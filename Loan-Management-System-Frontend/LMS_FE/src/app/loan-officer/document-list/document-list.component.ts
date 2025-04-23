import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { LoanOfficerService } from '../services/loan-officer.service';
import { AuthService } from '../../core/auth/auth.service';
import { DocumentResponseDTO } from '../models/loan-officer.model';

@Component({
  selector: 'app-document-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatButtonModule],
  template: `
    <div class="document-container">
      <h2>Pending Documents - LendEase</h2>
      <div *ngIf="errorMessage" class="error-message">{{ errorMessage }}</div>
      <mat-card class="document-card">
        <mat-card-header>
          <mat-card-title>Documents for Verification</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <mat-table [dataSource]="documents">
            <ng-container matColumnDef="documentId">
              <mat-header-cell *matHeaderCellDef>Document ID</mat-header-cell>
              <mat-cell *matCellDef="let doc">{{ doc.documentId }}</mat-cell>
            </ng-container>
            <ng-container matColumnDef="loanId">
              <mat-header-cell *matHeaderCellDef>Loan ID</mat-header-cell>
              <mat-cell *matCellDef="let doc">{{ doc.loanId }}</mat-cell>
            </ng-container>
            <ng-container matColumnDef="name">
              <mat-header-cell *matHeaderCellDef>Name</mat-header-cell>
              <mat-cell *matCellDef="let doc">{{ doc.documentName }}</mat-cell>
            </ng-container>
            <ng-container matColumnDef="status">
              <mat-header-cell *matHeaderCellDef>Status</mat-header-cell>
              <mat-cell *matCellDef="let doc">{{ doc.status }}</mat-cell>
            </ng-container>
            <ng-container matColumnDef="actions">
              <mat-header-cell *matHeaderCellDef>Actions</mat-header-cell>
              <mat-cell *matCellDef="let doc">
                <button mat-raised-button color="primary" (click)="verifyDocument(doc.loanId)">
                  Verify
                </button>
              </mat-cell>
            </ng-container>
            <mat-header-row *matHeaderRowDef="displayedColumns"></mat-header-row>
            <mat-row *matRowDef="let row; columns: displayedColumns;"></mat-row>
          </mat-table>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [
    `
      .document-container {
        margin-top: 20px;
        padding: 20px;
        background-color: #f5f5f5;
        display: flex;
        flex-direction: column;
        align-items: center;
        min-height: 100vh;
      }
      h2 {
        margin-bottom: 24px;
        font-size: 28px;
        color: #333;
        text-align: center;
      }
      .document-card {
        width: 100%;
        max-width: 800px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      }
      mat-card-header {
        background-color: #1976d2;
        color: #fff;
        padding: 16px;
        border-radius: 8px 8px 0 0;
        display: flex;
        justify-content: center;
      }
      mat-card-content {
        padding: 24px;
      }
      mat-table {
        width: 100%;
      }
      .error-message {
        color: #d32f2f;
        margin-bottom: 16px;
        text-align: center;
        font-size: 16px;
      }
    `
  ]
})
export class DocumentListComponent implements OnInit {
  documents: DocumentResponseDTO[] = [];
  errorMessage: string | null = null;
  displayedColumns: string[] = ['documentId', 'loanId', 'name', 'status', 'actions'];

  constructor(
    private loanOfficerService: LoanOfficerService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const loanOfficerId = this.authService.getLoanOfficerId();
    if (loanOfficerId) {
      this.loadDocuments(loanOfficerId);
    } else {
      this.errorMessage = 'Loan Officer ID not found.';
    }
  }

  loadDocuments(loanOfficerId: number): void {
    this.loanOfficerService.getPendingDocuments(loanOfficerId).subscribe({
      next: docs => {
        this.documents = docs;
        if (docs.length === 0) {
          this.errorMessage = 'No pending documents found.';
        }
      },
      error: err => {
        console.error('Load documents error:', err);
        this.errorMessage = 'Failed to load documents.';
      }
    });
  }

  verifyDocument(loanId: number): void {
    this.router.navigate([`/loan-officer/documents/${loanId}`]);
  }
}
