// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-view-loans',
//   standalone: true,
//   imports: [],
//   templateUrl: './view-loans.component.html',
//   styleUrl: './view-loans.component.scss'
// })
// export class ViewLoansComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { CustomerService } from '../services/customer.service';
// import { LoanScheme, Loan } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { RouterModule } from '@angular/router';

// @Component({
//     selector: 'app-view-loans',
//     standalone: true,
//     imports: [CommonModule, MatCardModule, MatTableModule, MatButtonModule, RouterModule],
//     templateUrl: './view-loans.component.html',
//     styleUrls: ['./view-loans.component.scss']
// })
// export class ViewLoansComponent implements OnInit {
//     customerId = 1; // TODO: Replace with AuthService.getCurrentUserId()
//     loanSchemes: LoanScheme[] = [];
//     myLoans: Loan[] = [];
//     error: string | null = null;
//     schemeColumns: string[] = ['schemeName', 'interestRate', 'tenureMonths', 'action'];
//     loanColumns: string[] = ['loanId', 'loanSchemeName', 'amount', 'statusName', 'applicationDate', 'dueDate'];

//     constructor(private customerService: CustomerService) { }

//     ngOnInit(): void {
//         this.customerService.getLoanSchemes().subscribe({
//             next: (schemes) => this.loanSchemes = schemes.filter(s => !s.isDeleted),
//             error: (err) => this.error = err.message
//         });

//         this.customerService.getCustomerLoans(this.customerId).subscribe({
//             next: (loans) => this.myLoans = loans,
//             error: (err) => this.error = err.message
//         });
//     }
// }


// import { Component, OnInit } from '@angular/core';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanScheme, Loan } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { RouterModule, Router } from '@angular/router';

// @Component({
//   selector: 'app-view-loans',
//   standalone: true,
//   imports: [CommonModule, MatCardModule, MatTableModule, MatButtonModule, RouterModule],
//   templateUrl: './view-loans.component.html',
//   styleUrls: ['./view-loans.component.scss']
// })
// export class ViewLoansComponent implements OnInit {
//   customerId: number | null;
//   loanSchemes: LoanScheme[] = [];
//   myLoans: Loan[] = [];
//   error: string | null = null;
//   schemeColumns: string[] = ['schemeName', 'interestRate', 'tenureMonths', 'action'];
//   loanColumns: string[] = ['loanId', 'loanSchemeName', 'amount', 'statusName', 'applicationDate', 'dueDate'];

//   constructor(
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.customerId = this.authService.getCustomerId();
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.error = 'Please log in to view loans.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getLoanSchemes().subscribe({
//       next: (schemes) => this.loanSchemes = schemes.filter(s => !s.isDeleted),
//       error: (err) => {
//         this.error = err.message;
//         this.router.navigate(['/login']);
//       }
//     });

//     this.customerService.getCustomerLoans(this.customerId).subscribe({
//       next: (loans) => this.myLoans = loans,
//       error: (err) => {
//         this.error = err.message;
//         this.router.navigate(['/login']);
//       }
//     });
//   }
// }



// import { Component, OnInit } from '@angular/core';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanScheme, Loan } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { RouterModule, Router } from '@angular/router';
// import { HeaderComponent } from '../../shared/components/header/header.component';

// @Component({
//   selector: 'app-view-loans',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatCardModule,
//     MatTableModule,
//     MatButtonModule,
//     RouterModule,
//     HeaderComponent
//   ],
//   templateUrl: './view-loans.component.html',
//   styleUrls: ['./view-loans.component.scss']
// })
// export class ViewLoansComponent implements OnInit {
//   customerId: number | null;
//   loanSchemes: LoanScheme[] = [];
//   myLoans: Loan[] = [];
//   error: string | null = null;
//   schemeColumns: string[] = ['schemeName', 'interestRate', 'tenureMonths', 'action'];
//   loanColumns: string[] = ['loanId', 'loanSchemeName', 'amount', 'statusName', 'applicationDate', 'dueDate'];

//   constructor(
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.customerId = this.authService.getCustomerId();
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.error = 'Please log in to view loans.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getLoanSchemes().subscribe({
//       next: (schemes) => this.loanSchemes = schemes.filter(s => !s.isDeleted),
//       error: (err) => {
//         this.error = `Failed to load loan schemes: ${err.message}`;
//       }
//     });

//     this.customerService.getCustomerLoans(this.customerId).subscribe({
//       next: (loans) => this.myLoans = loans,
//       error: (err) => {
//         this.error = `Failed to load your loans: ${err.message}`;
//       }
//     });
//   }
// }


import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../core/auth/auth.service';
import { Loan, LoanScheme } from '../models/customer.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-loans',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule
  ],
  templateUrl: './view-loans.component.html',
  styleUrls: ['./view-loans.component.scss']
})
export class ViewLoansComponent implements OnInit {
  loans: Loan[] = [];
  loanSchemes: LoanScheme[] = [];
  errorMessage: string | null = null;
  customerId: number | null;
  displayedColumns: string[] = ['loanId', 'schemeName', 'amount', 'status', 'actions'];

  constructor(
    private customerService: CustomerService,
    private authService: AuthService,
    private router: Router
  ) {
    this.customerId = this.authService.getCustomerId();
  }

  ngOnInit(): void {
    if (!this.customerId) {
      this.errorMessage = 'Please log in to view loans.';
      this.router.navigate(['/login']);
      return;
    }

    this.loadLoanSchemes();
    this.loadLoans();
  }

  loadLoanSchemes(): void {
    this.customerService.getLoanSchemes().subscribe({
      next: (schemes) => this.loanSchemes = schemes.filter(s => !s.isDeleted),
      error: (err: Error) => {
        this.errorMessage = `Failed to load loan schemes: ${err.message}`;
      }
    });
  }

  loadLoans(): void {
    if (this.customerId) {
      this.customerService.getCustomerLoans(this.customerId).subscribe({
        next: (loans) => {
          this.loans = loans;
          if (loans.length === 0) {
            this.errorMessage = 'No loans found.';
          }
        },
        error: (err: Error) => {
          this.errorMessage = `Failed to load loans: ${err.message}`;
        }
      });
    }
  }

  viewDetails(loanId: number): void {
    this.router.navigate([`/customer/loans/${loanId}`]);
  }
}