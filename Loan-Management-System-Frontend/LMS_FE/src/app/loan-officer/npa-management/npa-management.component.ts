// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-npa-management',
//   standalone: true,
//   imports: [],
//   templateUrl: './npa-management.component.html',
//   styleUrl: './npa-management.component.scss'
// })
// export class NpaManagementComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatDialogModule, MatDialog } from '@angular/material/dialog';
// import { LoanOfficerService } from '../services/loan-officer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanResponseDTO } from '../models/loan-officer.model';
// import { NpaMarkDialogComponent } from '../npa-mark-dialog/npa-mark-dialog.component';

// @Component({
//   selector: 'app-npa-management',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatCardModule,
//     MatButtonModule,
//     MatDialogModule
//   ],
//   templateUrl: './npa-management.component.html',
//   styleUrls: ['./npa-management.component.scss']
// })
// export class NpaManagementComponent implements OnInit {
//   loans: LoanResponseDTO[] = [];
//   errorMessage: string | null = null;
//   displayedColumns: string[] = ['loanId', 'customerId', 'amount', 'status', 'actions'];

//   constructor(
//     private loanOfficerService: LoanOfficerService,
//     private authService: AuthService,
//     private dialog: MatDialog
//   ) { }

//   ngOnInit(): void {
//     const loanOfficerId = this.authService.getLoanOfficerId();
//     if (loanOfficerId) {
//       this.loadLoans(loanOfficerId);
//     }
//   }

//   loadLoans(loanOfficerId: number): void {
//     this.loanOfficerService.getAssignedLoans(loanOfficerId).subscribe({
//       next: loans => this.loans = loans.filter(loan => loan.statusName === 'APPROVED'),
//       error: err => this.errorMessage = 'Failed to load loans.'
//     });
//   }

//   markAsNPA(loan: LoanResponseDTO): void {
//     const dialogRef = this.dialog.open(NpaMarkDialogComponent, {
//       width: '400px',
//       data: { loan }
//     });

//     dialogRef.afterClosed().subscribe(result => {
//       if (result) {
//         this.loanOfficerService.markAsNPA(loan.loanId, { approve: true }).subscribe({
//           next: () => this.loadLoans(this.authService.getLoanOfficerId()!),
//           error: err => this.errorMessage = 'Failed to mark as NPA.'
//         });
//       }
//     });
//   }
// }

// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatDialogModule, MatDialog } from '@angular/material/dialog';
// import { LoanOfficerService } from '../services/loan-officer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanResponseDTO } from '../models/loan-officer.model';
// import { NpaMarkDialogComponent } from '../npa-mark-dialog/npa-mark-dialog.component';
// import { DashboardCardComponent } from '../../shared/components/dashboard-card/dashboard-card.component';

// @Component({
//   selector: 'app-npa-management',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatCardModule,
//     MatButtonModule,
//     MatDialogModule,
//     DashboardCardComponent
//   ],
//   templateUrl: './npa-management.component.html',
//   styleUrls: ['./npa-management.component.scss']
// })
// export class NpaManagementComponent implements OnInit {
//   npaLoans: LoanResponseDTO[] = [];
//   errorMessage: string | null = null;
//   showTable = false;
//   displayedColumns: string[] = ['loanId', 'customerId', 'amount', 'statusName', 'actions'];

//   constructor(
//     private loanOfficerService: LoanOfficerService,
//     private authService: AuthService,
//     private dialog: MatDialog
//   ) { }

//   ngOnInit(): void {
//     const loanOfficerId = this.authService.getLoanOfficerId();
//     console.log('NPA - LoanOfficerId:', loanOfficerId);
//     if (loanOfficerId) {
//       this.loadNpaLoans(loanOfficerId);
//     } else {
//       this.errorMessage = 'Loan Officer ID not found.';
//       console.error('NPA - No loanOfficerId');
//     }
//   }

//   loadNpaLoans(loanOfficerId: number): void {
//     this.loanOfficerService.getNpaLoans(loanOfficerId).subscribe({
//       next: loans => {
//         console.log('NPA - Loans:', loans);
//         this.npaLoans = loans;
//         if (loans.length === 0) {
//           this.errorMessage = 'No NPA loans found.';
//         }
//       },
//       error: err => {
//         console.error('NPA - Load loans error:', err);
//         this.errorMessage = 'Failed to load NPA loans.';
//       }
//     });
//   }

//   markAsNPA(loan: LoanResponseDTO): void {
//     const dialogRef = this.dialog.open(NpaMarkDialogComponent, {
//       width: '400px',
//       data: { loan }
//     });

//     dialogRef.afterClosed().subscribe(result => {
//       if (result) {
//         this.loanOfficerService.markAsNPA(loan.loanId, { approve: true }).subscribe({
//           next: () => {
//             this.loadNpaLoans(this.authService.getLoanOfficerId()!);
//             this.errorMessage = null;
//           },
//           error: err => {
//             console.error('NPA - Mark as NPA error:', err.status, err.message);
//             this.errorMessage = `Failed to mark as NPA: ${err.statusText || 'Unknown error'}`;
//           }
//         });
//       }
//     });
//   }

//   toggleTable(): void {
//     this.showTable = !this.showTable;
//   }
// }



// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatDialogModule, MatDialog } from '@angular/material/dialog';
// import { Router } from '@angular/router';
// import { LoanOfficerService } from '../services/loan-officer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanResponseDTO } from '../models/loan-officer.model';
// import { NpaMarkDialogComponent } from '../npa-mark-dialog/npa-mark-dialog.component';
// import { DashboardCardComponent } from '../../shared/components/dashboard-card/dashboard-card.component';

// @Component({
//   selector: 'app-npa-management',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatCardModule,
//     MatButtonModule,
//     MatDialogModule,
//     DashboardCardComponent
//   ],
//   templateUrl: './npa-management.component.html',
//   styleUrls: ['./npa-management.component.scss']
// })
// export class NpaManagementComponent implements OnInit {
//   npaLoans: LoanResponseDTO[] = [];
//   errorMessage: string | null = null;
//   showTable = false;
//   displayedColumns: string[] = ['loanId', 'customerId', 'amount', 'statusName', 'actions'];

//   constructor(
//     private loanOfficerService: LoanOfficerService,
//     private authService: AuthService,
//     private dialog: MatDialog,
//     private router: Router
//   ) { }

//   ngOnInit(): void {
//     const loanOfficerId = this.authService.getLoanOfficerId();
//     console.log('NPA - LoanOfficerId:', loanOfficerId);
//     if (loanOfficerId) {
//       this.loadNpaLoans(loanOfficerId);
//     } else {
//       this.errorMessage = 'Loan Officer ID not found.';
//       console.error('NPA - No loanOfficerId');
//     }
//   }

//   loadNpaLoans(loanOfficerId: number): void {
//     this.loanOfficerService.getNpaLoans(loanOfficerId).subscribe({
//       next: loans => {
//         console.log('NPA - Loans:', loans);
//         this.npaLoans = loans;
//         if (loans.length === 0) {
//           this.errorMessage = 'No NPA loans found.';
//         } else {
//           this.errorMessage = null;
//         }
//       },
//       error: err => {
//         console.error('NPA - Load loans error:', err);
//         this.errorMessage = 'Failed to load NPA loans.';
//       }
//     });
//   }

//   markAsNPA(loan: LoanResponseDTO): void {
//     const dialogRef = this.dialog.open(NpaMarkDialogComponent, {
//       width: '400px',
//       data: { loan }
//     });

//     dialogRef.afterClosed().subscribe(result => {
//       if (result) {
//         this.loanOfficerService.markAsNPA(loan.loanId, { approve: true }).subscribe({
//           next: () => {
//             this.loadNpaLoans(this.authService.getLoanOfficerId()!);
//             this.errorMessage = 'Loan marked as NPA successfully.';
//           },
//           error: err => {
//             console.error('NPA - Mark as NPA error:', err.status, err.message);
//             this.errorMessage = `Failed to mark as NPA: ${err.statusText || 'Unknown error'}`;
//           }
//         });
//       }
//     });
//   }

//   viewDocuments(loanId: number): void {
//     this.router.navigate([`/loan-officer/documents/${loanId}`]);
//   }

//   toggleTable(): void {
//     this.showTable = !this.showTable;
//   }
// }


// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { LoanOfficerService } from '../services/loan-officer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanResponseDTO } from '../models/loan-officer.model';

// @Component({
//   selector: 'app-npa-management',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatCardModule,
//     MatButtonModule
//   ],
//   templateUrl: './npa-management.component.html',
//   styleUrls: ['./npa-management.component.scss']
// })
// export class NpaManagementComponent implements OnInit {
//   loans: LoanResponseDTO[] = [];
//   errorMessage: string | null = null;
//   displayedColumns: string[] = ['loanId', 'customerId', 'amount', 'statusName', 'actions'];

//   constructor(
//     private loanOfficerService: LoanOfficerService,
//     private authService: AuthService
//   ) { }

//   ngOnInit(): void {
//     const loanOfficerId = this.authService.getLoanOfficerId();
//     if (loanOfficerId) {
//       this.loadNpaLoans(loanOfficerId);
//     }
//   }

//   loadNpaLoans(loanOfficerId: number): void {
//     this.loanOfficerService.getNpaLoans(loanOfficerId).subscribe({
//       next: loans => {
//         this.loans = loans;
//         if (loans.length === 0) {
//           this.errorMessage = 'No NPA loans found.';
//         }
//       },
//       error: err => {
//         this.errorMessage = 'Failed to load NPA loans.';
//       }
//     });
//   }

//   markAsNPA(loan: LoanResponseDTO): void {
//     this.loanOfficerService.markAsNPA(loan.loanId, { approve: true }).subscribe({
//       next: updatedLoan => {
//         this.loans = this.loans.map(l => l.loanId === updatedLoan.loanId ? updatedLoan : l);
//         this.errorMessage = null;
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to mark loan as NPA: ${err.message}`;
//       }
//     });
//   }
// }





import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { LoanOfficerService } from '../services/loan-officer.service';
import { AuthService } from '../../core/auth/auth.service';
import { LoanResponseDTO } from '../models/loan-officer.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-npa-management',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './npa-management.component.html',
  styleUrls: ['./npa-management.component.scss']
})
export class NpaManagementComponent implements OnInit {
  loans: LoanResponseDTO[] = [];
  errorMessage: string | null = null;
  displayedColumns: string[] = ['loanId', 'customerId', 'amount', 'statusName', 'actions'];

  constructor(
    private loanOfficerService: LoanOfficerService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const loanOfficerId = this.authService.getLoanOfficerId();
    if (loanOfficerId) {
      this.loadNpaLoans(loanOfficerId);
    }
  }

  loadNpaLoans(loanOfficerId: number): void {
    this.loanOfficerService.getNpaLoans(loanOfficerId).subscribe({
      next: loans => {
        this.loans = loans;
        if (loans.length === 0) {
          this.errorMessage = 'No NPA loans found.';
        }
      },
      error: err => {
        this.errorMessage = 'Failed to load NPA loans.';
      }
    });
  }

  markAsNPA(loan: LoanResponseDTO): void {
    this.loanOfficerService.markAsNPA(loan.loanId, { approve: true }).subscribe({
      next: updatedLoan => {
        this.loans = this.loans.map(l => l.loanId === updatedLoan.loanId ? updatedLoan : l);
        this.errorMessage = null;
      },
      error: (err: Error) => {
        this.errorMessage = `Failed to mark loan as NPA: ${err.message}`;
      }
    });
  }

  viewDocuments(loanId: number): void {
    this.router.navigate([`/loan-officer/documents/${loanId}`]);
  }
}