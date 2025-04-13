import { Component, OnInit } from '@angular/core';
import { LoanOfficerService } from '../../../core/services/loan-officer.service';
import { LoanOfficerResponse } from '../../models/loan-officer.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loan-officer-list',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  template: `
    <div class="container">
      <h2>Loan Officers</h2>
      <div *ngIf="errorMessage" class="error-message">{{ errorMessage }}</div>
      <table mat-table [dataSource]="dataSource">
        <ng-container matColumnDef="id">
          <th mat-header-cell *matHeaderCellDef>ID</th>
          <td mat-cell *matCellDef="let officer">{{ officer.id }}</td>
        </ng-container>
        <ng-container matColumnDef="username">
          <th mat-header-cell *matHeaderCellDef>Username</th>
          <td mat-cell *matCellDef="let officer">{{ officer.username }}</td>
        </ng-container>
        <ng-container matColumnDef="email">
          <th mat-header-cell *matHeaderCellDef>Email</th>
          <td mat-cell *matCellDef="let officer">{{ officer.email }}</td>
        </ng-container>
        <ng-container matColumnDef="customerCount">
          <th mat-header-cell *matHeaderCellDef>Customers</th>
          <td mat-cell *matCellDef="let officer">{{ officer.customerIds.length }}</td>
        </ng-container>
        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
        <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
      </table>
    </div>
  `,
  styles: [
    `
      .container {
        padding: 16px;
      }
      table {
        width: 100%;
      }
      .error-message {
        color: red;
        margin-bottom: 16px;
      }
    `
  ]
})
export class LoanOfficerListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'username', 'email', 'customerCount'];
  dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
  errorMessage: string | null = null;

  constructor(private loanOfficerService: LoanOfficerService) { }

  ngOnInit(): void {
    this.loanOfficerService.getAllLoanOfficers().subscribe({
      next: officers => {
        this.dataSource.data = officers;
      },
      error: err => {
        console.error('Fetch officers failed:', err);
        this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load loan officers.';
      }
    });
  }
}