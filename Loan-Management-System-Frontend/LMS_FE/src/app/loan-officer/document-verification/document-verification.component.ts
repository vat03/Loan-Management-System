// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-document-verification',
//   standalone: true,
//   imports: [],
//   templateUrl: './document-verification.component.html',
//   styleUrl: './document-verification.component.scss'
// })
// export class DocumentVerificationComponent {

// }


import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { LoanOfficerService } from '../services/loan-officer.service';
import { DocumentResponseDTO } from '../models/loan-officer.model';
import { DocumentVerificationDialogComponent } from '../document-verification-dialog/document-verification-dialog.component';

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
  documents: DocumentResponseDTO[] = [];
  loanId: number | null = null;
  errorMessage: string | null = null;
  displayedColumns: string[] = ['documentId', 'documentName', 'documentType', 'status', 'actions'];

  constructor(
    private route: ActivatedRoute,
    private loanOfficerService: LoanOfficerService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loanId = +this.route.snapshot.paramMap.get('loanId')!;
    if (this.loanId) {
      this.loadDocuments(this.loanId);
    }
  }

  loadDocuments(loanId: number): void {
    this.loanOfficerService.getDocumentsByLoanId(loanId).subscribe({
      next: documents => this.documents = documents,
      error: err => this.errorMessage = 'Failed to load documents.'
    });
  }

  verifyDocument(document: DocumentResponseDTO): void {
    const dialogRef = this.dialog.open(DocumentVerificationDialogComponent, {
      width: '400px',
      data: { document }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loanOfficerService.verifyDocument(document.documentId, result).subscribe({
          next: updated => {
            this.documents = this.documents.map(doc =>
              doc.documentId === document.documentId ? updated : doc
            );
          },
          error: err => this.errorMessage = 'Failed to verify document.'
        });
      }
    });
  }
}