import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
import { AuthService } from '../../../core/auth/auth.service';
import { LoanSchemeResponse, LoanSchemeSoftDelete } from '../../models/loan-scheme.model';
import { LoanSchemeFormComponent } from '../loan-scheme-form/loan-scheme-form.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loan-scheme-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule],
  template: `
    <div class="container">
      <h2>Loan Schemes</h2>
      <div *ngIf="errorMessage" class="error-message">{{ errorMessage }}</div>
      <table mat-table [dataSource]="dataSource">
        <ng-container matColumnDef="id">
          <th mat-header-cell *matHeaderCellDef>ID</th>
          <td mat-cell *matCellDef="let scheme">{{ scheme.id }}</td>
        </ng-container>
        <ng-container matColumnDef="schemeName">
          <th mat-header-cell *matHeaderCellDef>Name</th>
          <td mat-cell *matCellDef="let scheme">{{ scheme.schemeName }}</td>
        </ng-container>
        <ng-container matColumnDef="interestRate">
          <th mat-header-cell *matHeaderCellDef>Rate (%)</th>
          <td mat-cell *matCellDef="let scheme">{{ scheme.interestRate }}</td>
        </ng-container>
        <ng-container matColumnDef="tenureMonths">
          <th mat-header-cell *matHeaderCellDef>Tenure</th>
          <td mat-cell *matCellDef="let scheme">{{ scheme.tenureMonths }}</td>
        </ng-container>
        <ng-container matColumnDef="actions">
          <th mat-header-cell *matHeaderCellDef>Actions</th>
          <td mat-cell *matCellDef="let scheme">
            <button mat-icon-button (click)="editScheme(scheme)">
              <mat-icon>edit</mat-icon>
            </button>
            <button mat-icon-button (click)="deleteScheme(scheme.id)">
              <mat-icon>delete</mat-icon>
            </button>
          </td>
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
export class LoanSchemeListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'schemeName', 'interestRate', 'tenureMonths', 'actions'];
  dataSource = new MatTableDataSource<LoanSchemeResponse>([]);
  errorMessage: string | null = null;

  constructor(
    private loanSchemeService: LoanSchemeService,
    private dialog: MatDialog,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadSchemes();
  }

  loadSchemes(): void {
    this.loanSchemeService.getAllLoanSchemes().subscribe({
      next: schemes => {
        this.dataSource.data = schemes.filter(s => !s.isDeleted);
      },
      error: err => {
        console.error('Fetch schemes failed:', err);
        this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load loan schemes.';
      }
    });
  }

  editScheme(scheme: LoanSchemeResponse): void {
    this.dialog
      .open(LoanSchemeFormComponent, {
        width: '400px',
        data: { adminId: this.authService.getUserId(), scheme }
      })
      .afterClosed()
      .subscribe(result => {
        if (result) this.loadSchemes();
      });
  }

  deleteScheme(id: number): void {
    if (confirm('Are you sure you want to delete this scheme?')) {
      const request: LoanSchemeSoftDelete = { adminId: this.authService.getUserId()! };
      this.loanSchemeService.softDeleteLoanScheme(id, request).subscribe({
        next: () => this.loadSchemes(),
        error: err => console.error('Delete scheme failed:', err)
      });
    }
  }
}