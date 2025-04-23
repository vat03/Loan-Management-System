import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { DocumentResponseDTO } from '../models/loan-officer.model';
import { LoanOfficerService } from '../services/loan-officer.service';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-document-verification-dialog',
  standalone: true,
  imports: [
    CommonModule, // Added CommonModule for NgIf
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    ReactiveFormsModule
  ],
  templateUrl: './document-verification-dialog.component.html',
  styleUrls: ['./document-verification-dialog.component.scss']
})
export class DocumentVerificationDialogComponent {
  verifyForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<DocumentVerificationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { document: DocumentResponseDTO },
    private loanOfficerService: LoanOfficerService,
    private fb: FormBuilder
  ) {
    this.verifyForm = this.fb.group({
      status: ['', Validators.required]
    });
  }

  submit(): void {
    if (this.verifyForm.valid) {
      const status = this.verifyForm.value.status;
      this.loanOfficerService.verifyDocument(this.data.document.documentId, { status }).subscribe({
        next: (doc) => this.dialogRef.close(doc),
        error: (err) => this.dialogRef.close({ error: `Failed to verify: ${err.message}` })
      });
    }
  }

  cancel(): void {
    this.dialogRef.close();
  }
}