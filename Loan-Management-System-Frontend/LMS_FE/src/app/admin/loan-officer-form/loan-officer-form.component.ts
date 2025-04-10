import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../core/services/api.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-loan-officer-form',
  standalone: false,
  templateUrl: './loan-officer-form.component.html',
  styleUrl: './loan-officer-form.component.css'
})
export class LoanOfficerFormComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private api: ApiService,
    public dialogRef: MatDialogRef<LoanOfficerFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.form = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.api.post('users/registerUser?roleName=LOAN_OFFICER', this.form.value)
        .subscribe({
          next: (response) => this.dialogRef.close(response),
          error: (err) => console.error('Error adding officer:', err)
        });
    }
  }

  onCancel() {
    this.dialogRef.close();
  }
}