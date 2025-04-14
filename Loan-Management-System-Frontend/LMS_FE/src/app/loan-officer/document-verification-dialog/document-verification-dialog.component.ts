// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-document-verification-dialog',
//   standalone: true,
//   imports: [],
//   templateUrl: './document-verification-dialog.component.html',
//   styleUrl: './document-verification-dialog.component.scss'
// })
// export class DocumentVerificationDialogComponent {

// }


import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DocumentResponseDTO, DocumentVerificationDTO } from '../models/loan-officer.model';

@Component({
  selector: 'app-document-verification-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule
  ],
  templateUrl: './document-verification-dialog.component.html',
  styleUrls: ['./document-verification-dialog.component.scss']
})
export class DocumentVerificationDialogComponent {
  verifyForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<DocumentVerificationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { document: DocumentResponseDTO }
  ) {
    this.verifyForm = this.fb.group({
      status: ['', Validators.required],
      rejectionReason: ['']
    });
  }

  submit(): void {
    if (this.verifyForm.valid) {
      const request: DocumentVerificationDTO = {
        status: this.verifyForm.value.status,
        rejectionReason: this.verifyForm.value.status === 'REJECTED' ? this.verifyForm.value.rejectionReason : undefined
      };
      this.dialogRef.close(request);
    }
  }
}