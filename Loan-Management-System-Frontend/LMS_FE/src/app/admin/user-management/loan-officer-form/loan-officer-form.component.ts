import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { LoanOfficerService } from '../../../core/services/loan-officer.service';
import { LoanOfficerRequest } from '../../models/loan-officer.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loan-officer-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  template: `
    <h2 mat-dialog-title>Add Loan Officer</h2>
    <mat-dialog-content>
      <form [formGroup]="form" (ngSubmit)="onSubmit()">
        <mat-form-field appearance="fill">
          <mat-label>Username</mat-label>
          <input matInput formControlName="username" />
          <mat-error *ngIf="form.get('username')?.hasError('required')">
            Username is required
          </mat-error>
          <mat-error *ngIf="form.get('username')?.hasError('minlength')">
            Username must be at least 3 characters
          </mat-error>
          <mat-error *ngIf="form.get('username')?.hasError('maxlength')">
            Username cannot exceed 50 characters
          </mat-error>
        </mat-form-field>
        <mat-form-field appearance="fill">
          <mat-label>Email</mat-label>
          <input matInput formControlName="email" type="email" />
          <mat-error *ngIf="form.get('email')?.hasError('required')">
            Email is required
          </mat-error>
          <mat-error *ngIf="form.get('email')?.hasError('email')">
            Invalid email format
          </mat-error>
          <mat-error *ngIf="form.get('email')?.hasError('maxlength')">
            Email cannot exceed 100 characters
          </mat-error>
        </mat-form-field>
        <mat-form-field appearance="fill">
          <mat-label>Password</mat-label>
          <input matInput formControlName="password" type="password" />
          <mat-error *ngIf="form.get('password')?.hasError('required')">
            Password is required
          </mat-error>
          <mat-error *ngIf="form.get('password')?.hasError('minlength')">
            Password must be at least 8 characters
          </mat-error>
          <mat-error *ngIf="form.get('password')?.hasError('pattern')">
            Password must include uppercase, lowercase, number, and special character
          </mat-error>
        </mat-form-field>
        <div *ngIf="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions>
      <button mat-button (click)="onCancel()">Cancel</button>
      <button mat-raised-button color="primary" [disabled]="form.invalid" (click)="onSubmit()">
        Add
      </button>
    </mat-dialog-actions>
  `,
  styles: [
    `
      .error-message {
        color: red;
        margin-top: 8px;
      }
      mat-form-field {
        width: 100%;
        margin-bottom: 16px;
      }
    `
  ]
})
export class LoanOfficerFormComponent {
  form: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<LoanOfficerFormComponent>,
    private loanOfficerService: LoanOfficerService,
    @Inject(MAT_DIALOG_DATA) public data: { adminId: number }
  ) {
    this.form = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(100),
          Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/)
        ]
      ]
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.errorMessage = null;
      const request: LoanOfficerRequest = this.form.value;
      this.loanOfficerService.addLoanOfficer(this.data.adminId, request).subscribe({
        next: () => this.dialogRef.close(true),
        error: err => {
          console.error('Add officer failed:', err);
          this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to add loan officer.';
        }
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}