import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { LoanOfficerService } from '../services/loan-officer.service';
import { AuthService } from '../../core/auth/auth.service';
import { LoanResponseDTO, DocumentResponseDTO, CustomerResponseDTO } from '../models/loan-officer.model';
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
  customers: CustomerResponseDTO[] = [];
  errorMessage: string | null = null;
  showAssignedTable = false;
  showNpaTable = false;
  showPendingTable = true;
  showApprovedTable = false;
  showCustomersTable = false;
  displayedColumns: string[] = ['loanId', 'customerId', 'amount', 'statusName', 'actions'];
  docColumns: string[] = ['documentId', 'loanId', 'documentName', 'documentUrl', 'status', 'actions'];
  customerColumns: string[] = ['id', 'username', 'email'];

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
          console.log('No assigned loans found for Loan Officer ID:', loanOfficerId);
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
        if (docs.length === 0) {
          console.log('No pending documents found for Loan Officer ID:', loanOfficerId);
        }
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

    this.loanOfficerService.getCustomersByLoanOfficerId(loanOfficerId).subscribe({
      next: customers => {
        console.log('Dashboard - Customers:', customers);
        this.customers = customers;
        this.showCustomersTable = customers.length > 0;
        if (customers.length === 0) {
          console.log('No customers found for Loan Officer ID:', loanOfficerId);
        }
      },
      error: err => {
        console.error('Dashboard - Load customers error:', err);
        this.errorMessage = 'Failed to load customers. Please try again later or contact support.';
      }
    });
  }

  approveDocument(documentId: number): void {
    this.loanOfficerService.verifyDocument(documentId, { status: 'APPROVED' }).subscribe({
      next: (doc) => {
        this.pendingDocuments = this.pendingDocuments.map(d =>
          d.documentId === documentId ? doc : d
        );
        this.errorMessage = null;
      },
      error: (err) => {
        this.errorMessage = `Failed to approve document: ${err.message}`;
      }
    });
  }

  rejectDocument(documentId: number): void {
    this.loanOfficerService.verifyDocument(documentId, { status: 'REJECTED' }).subscribe({
      next: (doc) => {
        this.pendingDocuments = this.pendingDocuments.map(d =>
          d.documentId === documentId ? doc : d
        );
        this.errorMessage = null;
      },
      error: (err) => {
        this.errorMessage = `Failed to reject document: ${err.message}`;
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

  toggleCustomersTable(): void {
    this.showCustomersTable = !this.showCustomersTable;
  }
}