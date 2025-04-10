import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../core/services/api.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-loan-scheme-form',
  standalone: false,
  templateUrl: './loan-scheme-form.component.html',
  styleUrl: './loan-scheme-form.component.css'
})
export class LoanSchemeFormComponent implements OnInit {
  form: FormGroup;
  documentTypes: { id: number, name: string }[] = [];
  adminId = 1; // Hardcoded for now; replace with auth later

  constructor(
    private fb: FormBuilder,
    private api: ApiService,
    public dialogRef: MatDialogRef<LoanSchemeFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.form = this.fb.group({
      schemeName: ['', Validators.required],
      interestRate: [0, [Validators.required, Validators.min(0)]],
      tenureMonths: [0, [Validators.required, Validators.min(1)]],
      requiredDocumentTypeIds: [[], Validators.required]
    });
  }

  ngOnInit() {
    this.loadDocumentTypes();
  }

  loadDocumentTypes() {
    this.api.get<any[]>('document-types').subscribe({
      next: (data) => this.documentTypes = data,
      error: () => {
        // Mock data if API fails
        this.documentTypes = [
          { id: 1, name: 'ID Proof' },
          { id: 2, name: 'Income Proof' },
          { id: 3, name: 'Address Proof' }
        ];
      }
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const payload = this.form.value;
      this.api.post(`loan-schemes/create?adminId=${this.adminId}`, payload)
        .subscribe({
          next: (response) => this.dialogRef.close(response),
          error: (err) => console.error('Error adding scheme:', err)
        });
    }
  }

  onCancel() {
    this.dialogRef.close();
  }
}