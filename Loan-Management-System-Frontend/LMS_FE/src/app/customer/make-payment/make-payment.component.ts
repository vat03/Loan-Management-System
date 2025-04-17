// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-make-payment',
//   standalone: true,
//   imports: [],
//   templateUrl: './make-payment.component.html',
//   styleUrl: './make-payment.component.scss'
// })
// export class MakePaymentComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { Loan, LoanPayment } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

// declare var Razorpay: any;

// @Component({
//   selector: 'app-make-payment',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatTableModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatSelectModule,
//     MatProgressSpinnerModule
//   ],
//   templateUrl: './make-payment.component.html',
//   styleUrls: ['./make-payment.component.scss']
// })
// export class MakePaymentComponent implements OnInit {
//   customerId = 1; // TODO: Replace with AuthService.getCurrentUserId()
//   loans: Loan[] = [];
//   payments: LoanPayment[] = [];
//   paymentForm: FormGroup;
//   error: string | null = null;
//   loading = false;
//   displayedColumns: string[] = ['id', 'amount', 'dueDate', 'penaltyAmount', 'action'];

//   constructor(private fb: FormBuilder, private customerService: CustomerService) {
//     this.paymentForm = this.fb.group({
//       loanId: ['', Validators.required]
//     });
//   }

//   ngOnInit(): void {
//     this.customerService.getCustomerLoans(this.customerId).subscribe({
//       next: (loans) => this.loans = loans,
//       error: (err) => this.error = err.message
//     });
//   }

//   onLoanChange(): void {
//     const loanId = this.paymentForm.value.loanId;
//     if (loanId) {
//       this.customerService.getLoanPayments(loanId, 'PENDING').subscribe({
//         next: (payments) => this.payments = payments,
//         error: (err) => this.error = err.message
//       });
//     } else {
//       this.payments = [];
//     }
//   }

//   makePayment(payment: LoanPayment): void {
//     this.loading = true;
//     this.customerService.initiatePayment(payment.id).subscribe({
//       next: (orderId) => {
//         const options = {
//           key: 'rzp_test_your_key_id', // TODO: Replace with actual Razorpay test key
//           amount: payment.amount * 100, // Razorpay expects amount in paise
//           currency: 'INR',
//           name: 'LendEase',
//           description: `Payment for Loan Payment ID: ${payment.id}`,
//           order_id: orderId,
//           handler: (response: any) => {
//             this.verifyPayment(response, payment.id);
//           },
//           prefill: {
//             email: 'customer@example.com', // TODO: Use profile.email
//             contact: '9999999999' // TODO: Use profile.mobileNumber
//           },
//           theme: {
//             color: '#1976d2'
//           }
//         };
//         const rzp = new Razorpay(options);
//         rzp.open();
//         this.loading = false;
//       },
//       error: (err) => {
//         this.error = err.message;
//         this.loading = false;
//       }
//     });
//   }

//   verifyPayment(response: any, loanPaymentId: number): void {
//     const payment = {
//       orderId: response.razorpay_order_id,
//       paymentId: response.razorpay_payment_id,
//       signature: response.razorpay_signature,
//       loanPaymentId
//     };
//     this.customerService.completePayment(payment).subscribe({
//       next: () => {
//         alert('Payment successful!');
//         this.onLoanChange(); // Refresh payments
//       },
//       error: (err) => this.error = err.message
//     });
//   }
// }


// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { Loan, LoanPayment, Profile } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
// import { Router } from '@angular/router';

// declare var Razorpay: any;

// @Component({
//   selector: 'app-make-payment',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatTableModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatSelectModule,
//     MatProgressSpinnerModule
//   ],
//   templateUrl: './make-payment.component.html',
//   styleUrls: ['./make-payment.component.scss']
// })
// export class MakePaymentComponent implements OnInit {
//   customerId: number | null;
//   loans: Loan[] = [];
//   payments: LoanPayment[] = [];
//   profile: Profile | null = null;
//   paymentForm: FormGroup;
//   error: string | null = null;
//   loading = false;
//   displayedColumns: string[] = ['id', 'amount', 'dueDate', 'penaltyAmount', 'action'];

//   constructor(
//     private fb: FormBuilder,
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.paymentForm = this.fb.group({
//       loanId: ['', Validators.required]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.error = 'Please log in to make a payment.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getCustomerLoans(this.customerId).subscribe({
//       next: (loans) => this.loans = loans,
//       error: (err) => {
//         this.error = err.message;
//         this.router.navigate(['/login']);
//       }
//     });

//     this.customerService.getProfile(this.customerId).subscribe({
//       next: (profile) => this.profile = profile,
//       error: (err) => {
//         this.error = err.message;
//         this.router.navigate(['/login']);
//       }
//     });
//   }

//   onLoanChange(): void {
//     const loanId = this.paymentForm.value.loanId;
//     if (loanId) {
//       this.customerService.getLoanPayments(loanId, 'PENDING').subscribe({
//         next: (payments) => this.payments = payments,
//         error: (err) => {
//           this.error = err.message;
//           this.router.navigate(['/login']);
//         }
//       });
//     } else {
//       this.payments = [];
//     }
//   }

//   makePayment(payment: LoanPayment): void {
//     if (!this.customerId) {
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.loading = true;
//     this.customerService.initiatePayment(payment.id).subscribe({
//       next: (orderId) => {
//         const options = {
//           key: 'rzp_test_your_key_id', // TODO: Replace with actual Razorpay test key
//           amount: payment.amount * 100, // Razorpay expects amount in paise
//           currency: 'INR',
//           name: 'LendEase',
//           description: `Payment for Loan Payment ID: ${payment.id}`,
//           order_id: orderId,
//           handler: (response: any) => {
//             this.verifyPayment(response, payment.id);
//           },
//           prefill: {
//             email: this.profile?.email || 'customer@example.com',
//             contact: this.profile?.mobileNumber || '9999999999'
//           },
//           theme: {
//             color: '#1976d2'
//           }
//         };
//         const rzp = new Razorpay(options);
//         rzp.open();
//         this.loading = false;
//       },
//       error: (err) => {
//         this.error = err.message;
//         this.loading = false;
//       }
//     });
//   }

//   verifyPayment(response: any, loanPaymentId: number): void {
//     const payment = {
//       orderId: response.razorpay_order_id,
//       paymentId: response.razorpay_payment_id,
//       signature: response.razorpay_signature,
//       loanPaymentId
//     };
//     this.customerService.completePayment(payment).subscribe({
//       next: () => {
//         alert('Payment successful!');
//         this.onLoanChange(); // Refresh payments
//       },
//       error: (err) => {
//         this.error = err.message;
//         this.router.navigate(['/login']);
//       }
//     });
//   }
// }





// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { Loan, LoanPayment, Profile } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
// import { Router } from '@angular/router';
// import { HeaderComponent } from '../../shared/components/header/header.component';

// declare var Razorpay: any;

// @Component({
//   selector: 'app-make-payment',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatTableModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatSelectModule,
//     MatProgressSpinnerModule,
//     HeaderComponent
//   ],
//   templateUrl: './make-payment.component.html',
//   styleUrls: ['./make-payment.component.scss']
// })
// export class MakePaymentComponent implements OnInit {
//   customerId: number | null;
//   loans: Loan[] = [];
//   payments: LoanPayment[] = [];
//   profile: Profile | null = null;
//   paymentForm: FormGroup;
//   error: string | null = null;
//   loading = false;
//   displayedColumns: string[] = ['id', 'amount', 'dueDate', 'penaltyAmount', 'action'];

//   constructor(
//     private fb: FormBuilder,
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.paymentForm = this.fb.group({
//       loanId: ['', Validators.required]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.error = 'Please log in to make a payment.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getCustomerLoans(this.customerId).subscribe({
//       next: (loans) => this.loans = loans,
//       error: (err) => {
//         this.error = `Failed to load loans: ${err.message}`;
//       }
//     });

//     this.customerService.getProfile(this.customerId).subscribe({
//       next: (profile) => this.profile = profile,
//       error: (err) => {
//         this.error = `Failed to load profile: ${err.message}`;
//       }
//     });
//   }

//   onLoanChange(): void {
//     const loanId = this.paymentForm.value.loanId;
//     if (loanId) {
//       this.customerService.getLoanPayments(loanId, 'PENDING').subscribe({
//         next: (payments) => this.payments = payments,
//         error: (err) => {
//           this.error = `Failed to load payments: ${err.message}`;
//         }
//       });
//     } else {
//       this.payments = [];
//     }
//   }

//   makePayment(payment: LoanPayment): void {
//     if (!this.customerId) {
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.loading = true;
//     this.customerService.initiatePayment(payment.id).subscribe({
//       next: (orderId) => {
//         const options = {
//           key: 'rzp_test_FFOcSmWzqyVdRz',
//           amount: payment.amount * 100,
//           currency: 'INR',
//           name: 'LendEase',
//           description: `Payment for Loan Payment ID: ${payment.id}`,
//           order_id: orderId,
//           handler: (response: any) => {
//             this.verifyPayment(response, payment.id);
//           },
//           prefill: {
//             email: this.profile?.email || 'customer@example.com',
//             contact: this.profile?.mobileNumber || '9999999999'
//           },
//           theme: {
//             color: '#1976d2'
//           }
//         };
//         const rzp = new Razorpay(options);
//         rzp.open();
//         this.loading = false;
//       },
//       error: (err) => {
//         this.error = `Failed to initiate payment: ${err.message}`;
//         this.loading = false;
//       }
//     });
//   }

//   verifyPayment(response: any, loanPaymentId: number): void {
//     const payment = {
//       orderId: response.razorpay_order_id,
//       paymentId: response.razorpay_payment_id,
//       signature: response.razorpay_signature,
//       loanPaymentId
//     };
//     this.customerService.completePayment(payment).subscribe({
//       next: () => {
//         alert('Payment successful!');
//         this.onLoanChange();
//       },
//       error: (err) => {
//         this.error = `Failed to verify payment: ${err.message}`;
//       }
//     });
//   }
// }








// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { Loan, LoanPayment, Profile } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
// import { Router } from '@angular/router';
// import { HeaderComponent } from '../../shared/components/header/header.component';

// declare var Razorpay: any;

// @Component({
//   selector: 'app-make-payment',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatTableModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatSelectModule,
//     MatProgressSpinnerModule,
//     HeaderComponent
//   ],
//   templateUrl: './make-payment.component.html',
//   styleUrls: ['./make-payment.component.scss']
// })
// export class MakePaymentComponent implements OnInit {
//   customerId: number | null;
//   loans: Loan[] = [];
//   payments: LoanPayment[] = [];
//   profile: Profile | null = null;
//   paymentForm: FormGroup;
//   error: string | null = null;
//   loading = false;
//   displayedColumns: string[] = ['id', 'amount', 'dueDate', 'penaltyAmount', 'action'];

//   constructor(
//     private fb: FormBuilder,
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.paymentForm = this.fb.group({
//       loanId: ['', Validators.required]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.error = 'Please log in to make a payment.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getCustomerLoans(this.customerId).subscribe({
//       next: (loans) => this.loans = loans,
//       error: (err) => {
//         this.error = `Failed to load loans: ${err.message}`;
//       }
//     });

//     this.customerService.getProfile(this.customerId).subscribe({
//       next: (profile) => this.profile = profile,
//       error: (err) => {
//         this.error = `Failed to load profile: ${err.message}`;
//       }
//     });
//   }

//   onLoanChange(): void {
//     const loanId = this.paymentForm.value.loanId;
//     if (loanId) {
//       this.customerService.getLoanPayments(loanId, 'PENDING').subscribe({
//         next: (payments) => this.payments = payments,
//         error: (err) => {
//           this.error = `Failed to load payments: ${err.message}`;
//         }
//       });
//     } else {
//       this.payments = [];
//     }
//   }

//   makePayment(payment: LoanPayment): void {
//     if (!this.customerId) {
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.loading = true;
//     console.log('Initiating payment for payment ID:', payment.id);
//     this.customerService.initiatePayment(payment.id).subscribe({
//       next: (orderId) => {
//         console.log('Received orderId:', orderId);
//         const options = {
//           key: 'rzp_test_FFOcSmWzqyVdRz', // TODO: Replace with your Razorpay test key
//           amount: payment.amount * 100,
//           currency: 'INR',
//           name: 'LendEase',
//           description: `Payment for Loan Payment ID: ${payment.id}`,
//           order_id: orderId,
//           handler: (response: any) => {
//             console.log('Razorpay response:', response);
//             this.verifyPayment(response, payment.id);
//           },
//           prefill: {
//             email: this.profile?.email || 'customer@example.com',
//             contact: this.profile?.mobileNumber || '9999999999'
//           },
//           theme: {
//             color: '#1976d2'
//           }
//         };
//         try {
//           const rzp = new Razorpay(options);
//           rzp.open();
//         } catch (e) {
//           console.error('Razorpay error:', e);
//           this.error = 'Failed to open payment gateway';
//         }
//         this.loading = false;
//       },
//       error: (err) => {
//         console.error('Initiate payment error:', err);
//         this.error = `Failed to initiate payment: ${err.message}`;
//         this.loading = false;
//       }
//     });
//   }

//   verifyPayment(response: any, loanPaymentId: number): void {
//     console.log('Verifying payment with response:', response);
//     const payment = {
//       orderId: response.razorpay_order_id,
//       paymentId: response.razorpay_payment_id,
//       signature: response.razorpay_signature,
//       loanPaymentId
//     };
//     this.customerService.completePayment(payment).subscribe({
//       next: () => {
//         alert('Payment successful!');
//         this.onLoanChange();
//       },
//       error: (err) => {
//         console.error('Verify payment error:', err);
//         this.error = `Failed to verify payment: ${err.message}`;
//       }
//     });
//   }
// }




// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { Loan, LoanPayment, Profile } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
// import { Router } from '@angular/router';
// import { HeaderComponent } from '../../shared/components/header/header.component';

// declare var Razorpay: any;

// @Component({
//   selector: 'app-make-payment',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatTableModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatSelectModule,
//     MatProgressSpinnerModule,
//     HeaderComponent
//   ],
//   templateUrl: './make-payment.component.html',
//   styleUrls: ['./make-payment.component.scss']
// })
// export class MakePaymentComponent implements OnInit {
//   customerId: number | null;
//   loans: Loan[] = [];
//   payments: LoanPayment[] = [];
//   profile: Profile | null = null;
//   paymentForm: FormGroup;
//   error: string | null = null;
//   loading = false;
//   displayedColumns: string[] = ['id', 'amount', 'dueDate', 'penaltyAmount', 'action'];

//   constructor(
//     private fb: FormBuilder,
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.paymentForm = this.fb.group({
//       loanId: ['', Validators.required]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.error = 'Please log in to make a payment.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getCustomerLoans(this.customerId).subscribe({
//       next: (loans) => this.loans = loans,
//       error: (err) => {
//         this.error = `Failed to load loans: ${err.message}`;
//       }
//     });

//     this.customerService.getProfile(this.customerId).subscribe({
//       next: (profile) => this.profile = profile,
//       error: (err) => {
//         this.error = `Failed to load profile: ${err.message}`;
//       }
//     });
//   }

//   onLoanChange(): void {
//     const loanId = this.paymentForm.value.loanId;
//     if (loanId) {
//       this.customerService.getLoanPayments(loanId, 'PENDING').subscribe({
//         next: (payments) => this.payments = payments,
//         error: (err) => {
//           this.error = `Failed to load payments: ${err.message}`;
//         }
//       });
//     } else {
//       this.payments = [];
//     }
//   }

//   makePayment(payment: LoanPayment): void {
//     if (!this.customerId) {
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.loading = true;
//     console.log('Fetching amount for payment ID:', payment.id);
//     this.customerService.getPaymentAmount(payment.id).subscribe({
//       next: (amountInRupees) => {
//         const amountInPaise = Math.round(parseFloat(amountInRupees) * 100);
//         console.log(`Amount fetched: ${amountInRupees} INR (${amountInPaise} paise)`);

//         this.customerService.initiatePayment(payment.id).subscribe({
//           next: (orderId) => {
//             console.log('Received orderId:', orderId);
//             const options = {
//               key: 'rzp_test_FFOcSmWzqyVdRz',
//               amount: amountInPaise,
//               currency: 'INR',
//               name: 'Loan Management System',
//               description: `Payment for LoanPaymentId ${payment.id}`,
//               order_id: orderId,
//               handler: (response: any) => {
//                 console.log('Razorpay response:', response);
//                 this.verifyPayment(response, payment.id);
//               },
//               prefill: {
//                 name: this.profile?.firstName || 'Test User',
//                 email: this.profile?.email || 'test@example.com',
//                 contact: this.profile?.mobileNumber || '9999999999'
//               },
//               theme: {
//                 color: '#3399cc'
//               }
//             };
//             try {
//               const rzp = new Razorpay(options);
//               rzp.on('payment.failed', (response: any) => {
//                 console.error('Payment failed:', response.error);
//                 this.error = `Payment failed: ${response.error.description}`;
//                 this.loading = false;
//               });
//               rzp.open();
//             } catch (e) {
//               console.error('Razorpay error:', e);
//               this.error = 'Failed to open payment gateway';
//               this.loading = false;
//             }
//           },
//           error: (err) => {
//             console.error('Initiate payment error:', err);
//             this.error = `Failed to initiate payment: ${err.message}`;
//             this.loading = false;
//           },
//           complete: () => {
//             this.loading = false;
//           }
//         });
//       },
//       error: (err) => {
//         console.error('Fetch amount error:', err);
//         this.error = `Failed to fetch payment amount: ${err.message}`;
//         this.loading = false;
//       }
//     });
//   }

//   verifyPayment(response: any, loanPaymentId: number): void {
//     console.log('Verifying payment with response:', response);
//     const payment = {
//       orderId: response.razorpay_order_id,
//       paymentId: response.razorpay_payment_id,
//       signature: response.razorpay_signature,
//       loanPaymentId
//     };
//     this.customerService.completePayment(payment).subscribe({
//       next: (result) => {
//         console.log('Payment completed:', result);
//         alert('Payment successful!');
//         this.onLoanChange();
//       },
//       error: (err) => {
//         console.error('Verify payment error:', err);
//         this.error = `Failed to verify payment: ${err.message}`;
//       }
//     });
//   }
// }





// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatSelectModule } from '@angular/material/select';
// import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { Loan, LoanPayment, Profile } from '../models/customer.model';
// import { ActivatedRoute, Router } from '@angular/router';
// import { FormsModule } from '@angular/forms';

// @Component({
//   selector: 'app-make-payment',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatCardModule,
//     MatTableModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatInputModule,
//     MatSelectModule,
//     MatProgressSpinnerModule,
//     FormsModule
//   ],
//   templateUrl: './make-payment.component.html',
//   styleUrls: ['./make-payment.component.scss']
// })
// export class MakePaymentComponent implements OnInit {
//   customerId: number | null;
//   loans: Loan[] = [];
//   selectedLoan: Loan | null = null;
//   payments: LoanPayment[] = [];
//   profile: Profile | null = null;
//   error: string | null = null;
//   loading = false;
//   displayedColumns: string[] = ['id', 'amount', 'dueDate', 'status', 'penaltyAmount', 'action'];

//   constructor(
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private route: ActivatedRoute,
//     private router: Router
//   ) {
//     this.customerId = this.authService.getCustomerId();
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.error = 'Please log in to make a payment.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getProfile(this.customerId).subscribe({
//       next: (profile: Profile) => this.profile = profile,
//       error: (err: Error) => {
//         this.error = `Failed to load profile: ${err.message}`;
//       }
//     });

//     this.customerService.getCustomerLoans(this.customerId).subscribe({
//       next: loans => {
//         this.loans = loans;
//         const loanId = this.route.snapshot.paramMap.get('loanId');
//         if (loanId) {
//           this.selectedLoan = loans.find(loan => loan.loanId === +loanId) || null;
//           if (this.selectedLoan) {
//             this.loadPayments(this.selectedLoan.loanId);
//           }
//         }
//       },
//       error: err => {
//         this.error = `Failed to load loans: ${err.message}`;
//       }
//     });
//   }

//   selectLoan(loan: Loan): void {
//     this.selectedLoan = loan;
//     this.loadPayments(loan.loanId);
//   }

//   loadPayments(loanId: number): void {
//     this.loading = true;
//     this.customerService.getLoanPayments(loanId, 'PENDING').subscribe({
//       next: payments => {
//         this.payments = payments;
//         this.loading = false;
//       },
//       error: err => {
//         this.error = `Failed to load payments: ${err.message}`;
//         this.loading = false;
//       }
//     });
//   }

//   makePayment(paymentId: number): void {
//     if (!this.customerId) return;
//     this.loading = true;

//     this.customerService.initiatePayment(paymentId).subscribe({
//       next: orderId => {
//         this.loading = false;
//         // Simulate Razorpay payment
//         const options = {
//           key: 'rzp_test_key',
//           amount: 0,
//           currency: 'INR',
//           name: 'LendEase',
//           description: 'Loan Payment',
//           order_id: orderId,
//           handler: (response: any) => {
//             this.completePayment(response, paymentId);
//           },
//           prefill: {
//             name: this.profile?.firstName + ' ' + this.profile?.lastName,
//             email: this.profile?.email,
//             contact: this.profile?.mobileNumber
//           }
//         };

//         this.customerService.getPaymentAmount(paymentId).subscribe({
//           next: amount => {
//             options.amount = parseFloat(amount) * 100;
//             const rzp = new (window as any).Razorpay(options);
//             rzp.open();
//           },
//           error: err => {
//             this.error = `Failed to fetch payment amount: ${err.message}`;
//             this.loading = false;
//           }
//         });
//       },
//       error: err => {
//         this.error = `Failed to initiate payment: ${err.message}`;
//         this.loading = false;
//       }
//     });
//   }

//   completePayment(response: any, paymentId: number): void {
//     const paymentData = {
//       razorpay_payment_id: response.razorpay_payment_id,
//       razorpay_order_id: response.razorpay_order_id,
//       razorpay_signature: response.razorpay_signature,
//       paymentId: paymentId
//     };

//     this.customerService.completePayment(paymentData).subscribe({
//       next: () => {
//         this.error = null;
//         if (this.selectedLoan) {
//           this.loadPayments(this.selectedLoan.loanId);
//         }
//       },
//       error: err => {
//         this.error = `Failed to complete payment: ${err.message}`;
//       }
//     });
//   }
// }




import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../core/auth/auth.service';
import { Loan, LoanPayment, Profile } from '../models/customer.model';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-make-payment',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    ReactiveFormsModule
  ],
  templateUrl: './make-payment.component.html',
  styleUrls: ['./make-payment.component.scss']
})
export class MakePaymentComponent implements OnInit {
  customerId: number | null;
  loans: Loan[] = [];
  selectedLoan: Loan | null = null;
  payments: LoanPayment[] = [];
  profile: Profile | null = null;
  error: string | null = null;
  loading = false;
  paymentForm: FormGroup;
  displayedColumns: string[] = ['id', 'amount', 'dueDate', 'status', 'penaltyAmount', 'action'];

  constructor(
    private customerService: CustomerService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.customerId = this.authService.getCustomerId();
    this.paymentForm = this.fb.group({
      loanId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if (!this.customerId) {
      this.error = 'Please log in to make a payment.';
      this.router.navigate(['/login']);
      return;
    }

    this.customerService.getProfile(this.customerId).subscribe({
      next: (profile: Profile) => this.profile = profile,
      error: (err: Error) => {
        this.error = `Failed to load profile: ${err.message}`;
      }
    });

    this.customerService.getCustomerLoans(this.customerId).subscribe({
      next: loans => {
        this.loans = loans;
        const loanId = this.route.snapshot.paramMap.get('loanId');
        if (loanId) {
          this.selectedLoan = loans.find(loan => loan.loanId === +loanId) || null;
          if (this.selectedLoan) {
            this.paymentForm.patchValue({ loanId: this.selectedLoan.loanId });
            this.loadPayments(this.selectedLoan.loanId);
          }
        }
      },
      error: err => {
        this.error = `Failed to load loans: ${err.message}`;
      }
    });
  }

  selectLoan(loanId: number): void {
    this.selectedLoan = this.loans.find(loan => loan.loanId === loanId) || null;
    if (this.selectedLoan) {
      this.loadPayments(this.selectedLoan.loanId);
    }
  }

  loadPayments(loanId: number): void {
    this.loading = true;
    this.customerService.getLoanPayments(loanId, 'PENDING').subscribe({
      next: payments => {
        this.payments = payments;
        this.loading = false;
      },
      error: err => {
        this.error = `Failed to load payments: ${err.message}`;
        this.loading = false;
      }
    });
  }

  makePayment(paymentId: number): void {
    if (!this.customerId) return;
    this.loading = true;

    this.customerService.initiatePayment(paymentId).subscribe({
      next: orderId => {
        this.loading = false;
        // Simulate Razorpay payment
        const options = {
          key: 'rzp_test_key',
          amount: 0,
          currency: 'INR',
          name: 'LendEase',
          description: 'Loan Payment',
          order_id: orderId,
          handler: (response: any) => {
            this.completePayment(response, paymentId);
          },
          prefill: {
            name: this.profile?.firstName + ' ' + this.profile?.lastName,
            email: this.profile?.email,
            contact: this.profile?.mobileNumber
          }
        };

        this.customerService.getPaymentAmount(paymentId).subscribe({
          next: amount => {
            options.amount = parseFloat(amount) * 100;
            const rzp = new (window as any).Razorpay(options);
            rzp.open();
          },
          error: err => {
            this.error = `Failed to fetch payment amount: ${err.message}`;
            this.loading = false;
          }
        });
      },
      error: err => {
        this.error = `Failed to initiate payment: ${err.message}`;
        this.loading = false;
      }
    });
  }

  completePayment(response: any, paymentId: number): void {
    const paymentData = {
      razorpay_payment_id: response.razorpay_payment_id,
      razorpay_order_id: response.razorpay_order_id,
      razorpay_signature: response.razorpay_signature,
      paymentId: paymentId
    };

    this.customerService.completePayment(paymentData).subscribe({
      next: () => {
        this.error = null;
        if (this.selectedLoan) {
          this.loadPayments(this.selectedLoan.loanId);
        }
      },
      error: err => {
        this.error = `Failed to complete payment: ${err.message}`;
      }
    });
  }
}