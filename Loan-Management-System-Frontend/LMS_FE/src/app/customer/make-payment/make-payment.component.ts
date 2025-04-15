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


import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../core/auth/auth.service';
import { Loan, LoanPayment, Profile } from '../models/customer.model';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router } from '@angular/router';

declare var Razorpay: any;

@Component({
  selector: 'app-make-payment',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './make-payment.component.html',
  styleUrls: ['./make-payment.component.scss']
})
export class MakePaymentComponent implements OnInit {
  customerId: number | null;
  loans: Loan[] = [];
  payments: LoanPayment[] = [];
  profile: Profile | null = null;
  paymentForm: FormGroup;
  error: string | null = null;
  loading = false;
  displayedColumns: string[] = ['id', 'amount', 'dueDate', 'penaltyAmount', 'action'];

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private authService: AuthService,
    private router: Router
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

    this.customerService.getCustomerLoans(this.customerId).subscribe({
      next: (loans) => this.loans = loans,
      error: (err) => {
        this.error = err.message;
        this.router.navigate(['/login']);
      }
    });

    this.customerService.getProfile(this.customerId).subscribe({
      next: (profile) => this.profile = profile,
      error: (err) => {
        this.error = err.message;
        this.router.navigate(['/login']);
      }
    });
  }

  onLoanChange(): void {
    const loanId = this.paymentForm.value.loanId;
    if (loanId) {
      this.customerService.getLoanPayments(loanId, 'PENDING').subscribe({
        next: (payments) => this.payments = payments,
        error: (err) => {
          this.error = err.message;
          this.router.navigate(['/login']);
        }
      });
    } else {
      this.payments = [];
    }
  }

  makePayment(payment: LoanPayment): void {
    if (!this.customerId) {
      this.router.navigate(['/login']);
      return;
    }

    this.loading = true;
    this.customerService.initiatePayment(payment.id).subscribe({
      next: (orderId) => {
        const options = {
          key: 'rzp_test_your_key_id', // TODO: Replace with actual Razorpay test key
          amount: payment.amount * 100, // Razorpay expects amount in paise
          currency: 'INR',
          name: 'LendEase',
          description: `Payment for Loan Payment ID: ${payment.id}`,
          order_id: orderId,
          handler: (response: any) => {
            this.verifyPayment(response, payment.id);
          },
          prefill: {
            email: this.profile?.email || 'customer@example.com',
            contact: this.profile?.mobileNumber || '9999999999'
          },
          theme: {
            color: '#1976d2'
          }
        };
        const rzp = new Razorpay(options);
        rzp.open();
        this.loading = false;
      },
      error: (err) => {
        this.error = err.message;
        this.loading = false;
      }
    });
  }

  verifyPayment(response: any, loanPaymentId: number): void {
    const payment = {
      orderId: response.razorpay_order_id,
      paymentId: response.razorpay_payment_id,
      signature: response.razorpay_signature,
      loanPaymentId
    };
    this.customerService.completePayment(payment).subscribe({
      next: () => {
        alert('Payment successful!');
        this.onLoanChange(); // Refresh payments
      },
      error: (err) => {
        this.error = err.message;
        this.router.navigate(['/login']);
      }
    });
  }
}