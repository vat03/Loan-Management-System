import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { LoanOfficerService } from '../services/loan-officer.service';
import { DocumentResponseDTO } from '../models/loan-officer.model';
import { DocumentVerificationDialogComponent } from '../document-verification-dialog/document-verification-dialog.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-document-verification',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatButtonModule,
    MatDialogModule
  ],
  templateUrl: './document-verification.component.html',
  styleUrls: ['./document-verification.component.scss']
})
export class DocumentVerificationComponent implements OnInit {
  documents = new MatTableDataSource<DocumentResponseDTO>([]);
  errorMessage: string | null = null;
  displayedColumns: string[] = ['documentId', 'documentName', 'documentType', 'viewDocument', 'status', 'actions'];

  constructor(
    private loanOfficerService: LoanOfficerService,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    const loanId = this.route.snapshot.paramMap.get('loanId');
    if (loanId) {
      this.loadDocuments(+loanId);
    } else {
      this.errorMessage = 'Invalid loan ID.';
    }
  }

  loadDocuments(loanId: number): void {
    this.errorMessage = null;
    this.loanOfficerService.getDocumentsByLoanId(loanId).subscribe({
      next: (documents: DocumentResponseDTO[]) => {
        this.documents.data = documents;
        if (documents.length === 0) {
          this.errorMessage = 'No documents found for this loan.';
        }
      },
      error: (err: Error) => {
        console.error('Failed to load documents:', err);
        this.errorMessage = 'Failed to load documents. Please try again later.';
      }
    });
  }

  verifyDocument(document: DocumentResponseDTO): void {
    this.openVerificationDialog(document);
  }

  openVerificationDialog(document: DocumentResponseDTO): void {
    const dialogRef = this.dialog.open(DocumentVerificationDialogComponent, {
      data: { document }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && !result.error) {
        this.documents.data = this.documents.data.map(doc =>
          doc.documentId === result.documentId ? result : doc
        );
      } else if (result?.error) {
        this.errorMessage = result.error;
      }
    });
  }
}