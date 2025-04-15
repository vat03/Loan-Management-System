// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-payment-details',
//   standalone: true,
//   imports: [],
//   templateUrl: './payment-details.component.html',
//   styleUrl: './payment-details.component.scss'
// })
// export class PaymentDetailsComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { Loan, LoanPayment } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';

// @Component({
//   selector: 'app-payment-details',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatTableModule,
//     MatFormFieldModule,
//     MatSelectModule
//   ],
//   templateUrl: './payment-details.component.html',
//   styleUrls: ['./payment-details.component.scss']
// })
// export class PaymentDetailsComponent implements OnInit {
//   customerId = 1; // TODO: Replace with AuthService.getCurrentUserId()
//   loans: Loan[] = [];
//   payments: LoanPayment[] = [];
//   paymentForm: FormGroup;
//   error: string | null = null;
//   displayedColumns: string[] = ['id', 'amount', 'dueDate', 'status', 'penaltyAmount'];

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
//       this.customerService.getLoanPayments(loanId).subscribe({
//         next: (payments) => this.payments = payments,
//         error: (err) => this.error = err.message
//       });
//     } else {
//       this.payments = [];
//     }
//   }
// }



// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { Loan, LoanPayment } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { Router } from '@angular/router';

// @Component({
//   selector: 'app-payment-details',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatTableModule,
//     MatFormFieldModule,
//     MatSelectModule
//   ],
//   templateUrl: './payment-details.component.html',
//   styleUrls: ['./payment-details.component.scss']
// })
// export class PaymentDetailsComponent implements OnInit {
//   customerId: number | null;
//   loans: Loan[] = [];
//   payments: LoanPayment[] = [];
//   paymentForm: FormGroup;
//   error: string | null = null;
//   displayedColumns: string[] = ['id', 'amount', 'dueDate', 'status', 'penaltyAmount'];

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
//       this.error = 'Please log in to view payment details.';
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
//   }

//   onLoanChange(): void {
//     const loanId = this.paymentForm.value.loanId;
//     if (loanId) {
//       this.customerService.getLoanPayments(loanId).subscribe({
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
// }



import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../core/auth/auth.service';
import { Loan, LoanPayment } from '../models/customer.model';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { HeaderComponent } from '../../shared/components/header/header.component';

@Component({
  selector: 'app-payment-details',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatTableModule,
    MatFormFieldModule,
    MatSelectModule,
    HeaderComponent
  ],
  templateUrl: './payment-details.component.html',
  styleUrls: ['./payment-details.component.scss']
})
export class PaymentDetailsComponent implements OnInit {
  customerId: number | null;
  loans: Loan[] = [];
  payments: LoanPayment[] = [];
  paymentForm: FormGroup;
  error: string | null = null;
  displayedColumns: string[] = ['id', 'amount', 'dueDate', 'status', 'penaltyAmount'];

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
      this.error = 'Please log in to view payment details.';
      this.router.navigate(['/login']);
      return;
    }

    this.customerService.getCustomerLoans(this.customerId).subscribe({
      next: (loans) => this.loans = loans,
      error: (err) => {
        this.error = `Failed to load loans: ${err.message}`;
      }
    });
  }

  onLoanChange(): void {
    const loanId = this.paymentForm.value.loanId;
    if (loanId) {
      this.customerService.getLoanPayments(loanId).subscribe({
        next: (payments) => this.payments = payments,
        error: (err) => {
          this.error = `Failed to load payments: ${err.message}`;
        }
      });
    } else {
      this.payments = [];
    }
  }
}