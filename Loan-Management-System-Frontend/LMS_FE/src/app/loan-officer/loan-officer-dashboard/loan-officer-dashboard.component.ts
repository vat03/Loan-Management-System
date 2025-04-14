// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-loan-officer-dashboard',
//   standalone: true,
//   imports: [],
//   templateUrl: './loan-officer-dashboard.component.html',
//   styleUrl: './loan-officer-dashboard.component.scss'
// })
// export class LoanOfficerDashboardComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { LoanOfficerService } from '../services/loan-officer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanResponseDTO } from '../models/loan-officer.model';
// import { Router } from '@angular/router';

// @Component({
//   selector: 'app-loan-officer-dashboard',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatCardModule,
//     MatButtonModule
//   ],
//   templateUrl: './loan-officer-dashboard.component.html',
//   styleUrls: ['./loan-officer-dashboard.component.scss']
// })
// export class LoanOfficerDashboardComponent implements OnInit {
//   loans: LoanResponseDTO[] = [];
//   errorMessage: string | null = null;
//   displayedColumns: string[] = ['loanId', 'customerId', 'amount', 'status', 'actions'];

//   constructor(
//     private loanOfficerService: LoanOfficerService,
//     private authService: AuthService,
//     private router: Router
//   ) { }

//   ngOnInit(): void {
//     const loanOfficerId = this.authService.getLoanOfficerId();
//     if (loanOfficerId) {
//       this.loadLoans(loanOfficerId);
//     } else {
//       this.errorMessage = 'Loan Officer ID not found.';
//     }
//   }

//   loadLoans(loanOfficerId: number): void {
//     this.loanOfficerService.getAssignedLoans(loanOfficerId).subscribe({
//       next: loans => this.loans = loans,
//       error: err => this.errorMessage = 'Failed to load loans.'
//     });
//   }

//   viewDocuments(loanId: number): void {
//     this.router.navigate([`/loan-officer/documents/${loanId}`]);
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
// import { Router } from '@angular/router';

// @Component({
//   selector: 'app-loan-officer-dashboard',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatCardModule,
//     MatButtonModule
//   ],
//   templateUrl: './loan-officer-dashboard.component.html',
//   styleUrls: ['./loan-officer-dashboard.component.scss']
// })
// export class LoanOfficerDashboardComponent implements OnInit {
//   loans: LoanResponseDTO[] = [];
//   errorMessage: string | null = null;
//   displayedColumns: string[] = ['loanId', 'customerId', 'amount', 'status', 'actions'];

//   constructor(
//     private loanOfficerService: LoanOfficerService,
//     private authService: AuthService,
//     private router: Router
//   ) { }

//   ngOnInit(): void {
//     const loanOfficerId = this.authService.getLoanOfficerId();
//     console.log('Dashboard - LoanOfficerId:', loanOfficerId);
//     if (loanOfficerId) {
//       this.loadLoans(loanOfficerId);
//     } else {
//       this.errorMessage = 'Loan Officer ID not found.';
//       console.error('Dashboard - No loanOfficerId');
//     }
//   }

//   loadLoans(loanOfficerId: number): void {
//     this.loanOfficerService.getAssignedLoans(loanOfficerId).subscribe({
//       next: loans => {
//         console.log('Dashboard - Loans:', loans);
//         this.loans = loans;
//       },
//       error: err => {
//         console.error('Dashboard - Load loans error:', err);
//         this.errorMessage = 'Failed to load loans.';
//       }
//     });
//   }

//   viewDocuments(loanId: number): void {
//     this.router.navigate([`/loan-officer/documents/${loanId}`]);
//   }
// }









import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { LoanOfficerService } from '../services/loan-officer.service';
import { AuthService } from '../../core/auth/auth.service';
import { LoanResponseDTO, DocumentResponseDTO } from '../models/loan-officer.model';
import { Router } from '@angular/router';
import { DashboardCardComponent } from '../../shared/components/dashboard-card/dashboard-card.component';

@Component({
  selector: 'app-loan-officer-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatButtonModule,
    DashboardCardComponent
  ],
  templateUrl: './loan-officer-dashboard.component.html',
  styleUrls: ['./loan-officer-dashboard.component.scss']
})
export class LoanOfficerDashboardComponent implements OnInit {
  assignedLoans: LoanResponseDTO[] = [];
  npaLoans: LoanResponseDTO[] = [];
  pendingDocuments: DocumentResponseDTO[] = [];
  approvedLoans: LoanResponseDTO[] = [];
  errorMessage: string | null = null;
  showAssignedTable = false;
  showNpaTable = false;
  showPendingTable = false;
  showApprovedTable = false;
  displayedColumns: string[] = ['loanId', 'customerId', 'amount', 'statusName', 'actions'];
  docColumns: string[] = ['documentId', 'loanId', 'documentName', 'status', 'actions'];

  constructor(
    private loanOfficerService: LoanOfficerService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const loanOfficerId = this.authService.getLoanOfficerId();
    console.log('Dashboard - LoanOfficerId:', loanOfficerId);
    if (loanOfficerId) {
      this.loadAllData(loanOfficerId);
    } else {
      this.errorMessage = 'Loan Officer ID not found.';
      console.error('Dashboard - No loanOfficerId');
    }
  }

  loadAllData(loanOfficerId: number): void {
    this.loanOfficerService.getAssignedLoans(loanOfficerId).subscribe({
      next: loans => {
        console.log('Dashboard - Assigned Loans:', loans);
        this.assignedLoans = loans;
        if (loans.length === 0) {
          this.errorMessage = 'No assigned loans found.';
        }
      },
      error: err => {
        console.error('Dashboard - Load assigned loans error:', err);
        this.errorMessage = 'Failed to load assigned loans.';
      }
    });

    this.loanOfficerService.getNpaLoans(loanOfficerId).subscribe({
      next: loans => {
        console.log('Dashboard - NPA Loans:', loans);
        this.npaLoans = loans;
      },
      error: err => {
        console.error('Dashboard - Load NPA loans error:', err);
        this.errorMessage = 'Failed to load NPA loans.';
      }
    });

    this.loanOfficerService.getPendingDocuments(loanOfficerId).subscribe({
      next: docs => {
        console.log('Dashboard - Pending Documents:', docs);
        this.pendingDocuments = docs;
      },
      error: err => {
        console.error('Dashboard - Load pending documents error:', err);
        this.errorMessage = 'Failed to load pending documents.';
      }
    });

    this.loanOfficerService.getApprovedLoans(loanOfficerId).subscribe({
      next: loans => {
        console.log('Dashboard - Approved Loans:', loans);
        this.approvedLoans = loans;
      },
      error: err => {
        console.error('Dashboard - Load approved loans error:', err);
        this.errorMessage = 'Failed to load approved loans.';
      }
    });
  }

  viewDocuments(loanId: number): void {
    this.router.navigate([`/loan-officer/documents/${loanId}`]);
  }

  toggleAssignedTable(): void {
    this.showAssignedTable = !this.showAssignedTable;
  }

  toggleNpaTable(): void {
    this.showNpaTable = !this.showNpaTable;
  }

  togglePendingTable(): void {
    this.showPendingTable = !this.showPendingTable;
  }

  toggleApprovedTable(): void {
    this.showApprovedTable = !this.showApprovedTable;
  }
}