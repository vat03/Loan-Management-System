// import { Component, Inject } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
// import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
// import { LoanSchemeRequest, LoanSchemeResponse } from '../../models/loan-scheme.model';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatSelectModule } from '@angular/material/select';
// import { MatButtonModule } from '@angular/material/button';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-loan-scheme-form',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatDialogModule,
//     MatFormFieldModule,
//     MatInputModule,
//     MatSelectModule,
//     MatButtonModule
//   ],
//   template: `
//     <h2 mat-dialog-title>{{ isEdit ? 'Edit Loan Scheme' : 'Add Loan Scheme' }}</h2>
//     <mat-dialog-content>
//       <form [formGroup]="form" (ngSubmit)="onSubmit()">
//         <mat-form-field appearance="fill">
//           <mat-label>Scheme Name</mat-label>
//           <input matInput formControlName="schemeName" />
//           <mat-error *ngIf="form.get('schemeName')?.hasError('required')">
//             Scheme name is required
//           </mat-error>
//         </mat-form-field>
//         <mat-form-field appearance="fill">
//           <mat-label>Interest Rate (%)</mat-label>
//           <input matInput formControlName="interestRate" type="number" step="0.1" />
//           <mat-error *ngIf="form.get('interestRate')?.hasError('required')">
//             Interest rate is required
//           </mat-error>
//           <mat-error *ngIf="form.get('interestRate')?.hasError('min')">
//             Interest rate must be non-negative
//           </mat-error>
//         </mat-form-field>
//         <mat-form-field appearance="fill">
//           <mat-label>Tenure (Months)</mat-label>
//           <input matInput formControlName="tenureMonths" type="number" />
//           <mat-error *ngIf="form.get('tenureMonths')?.hasError('required')">
//             Tenure is required
//           </mat-error>
//           <mat-error *ngIf="form.get('tenureMonths')?.hasError('min')">
//             Tenure must be at least 1 month
//           </mat-error>
//         </mat-form-field>
//         <mat-form-field appearance="fill">
//           <mat-label>Document Type IDs (e.g., 1,2,3)</mat-label>
//           <input matInput formControlName="requiredDocumentTypeIds" />
//           <mat-error *ngIf="form.get('requiredDocumentTypeIds')?.hasError('required')">
//             At least one document type ID is required
//           </mat-error>
//         </mat-form-field>
//         <div *ngIf="errorMessage" class="error-message">
//           {{ errorMessage }}
//         </div>
//       </form>
//     </mat-dialog-content>
//     <mat-dialog-actions>
//       <button mat-button (click)="onCancel()">Cancel</button>
//       <button mat-raised-button color="primary" [disabled]="form.invalid" (click)="onSubmit()">
//         {{ isEdit ? 'Update' : 'Add' }}
//       </button>
//     </mat-dialog-actions>
//   `,
//   styles: [
//     `
//       .error-message {
//         color: red;
//         margin-top: 8px;
//       }
//       mat-form-field {
//         width: 100%;
//         margin-bottom: 16px;
//       }
//     `
//   ]
// })
// export class LoanSchemeFormComponent {
//   form: FormGroup;
//   isEdit: boolean;
//   errorMessage: string | null = null;

//   constructor(
//     private fb: FormBuilder,
//     private dialogRef: MatDialogRef<LoanSchemeFormComponent>,
//     private loanSchemeService: LoanSchemeService,
//     @Inject(MAT_DIALOG_DATA) public data: { adminId: number; scheme?: LoanSchemeResponse }
//   ) {
//     this.isEdit = !!data.scheme;
//     this.form = this.fb.group({
//       schemeName: [data.scheme?.schemeName || '', [Validators.required]],
//       interestRate: [data.scheme?.interestRate || '', [Validators.required, Validators.min(0)]],
//       tenureMonths: [data.scheme?.tenureMonths || '', [Validators.required, Validators.min(1)]],
//       requiredDocumentTypeIds: [
//         data.scheme?.requiredDocumentTypeNames?.join(',') || '',
//         [Validators.required]
//       ]
//     });
//   }

//   onSubmit(): void {
//     if (this.form.valid) {
//       this.errorMessage = null;
//       const formValue = this.form.value;
//       const request: LoanSchemeRequest = {
//         schemeName: formValue.schemeName,
//         interestRate: +formValue.interestRate,
//         tenureMonths: +formValue.tenureMonths,
//         requiredDocumentTypeIds: formValue.requiredDocumentTypeIds
//           ? formValue.requiredDocumentTypeIds.split(',').map((id: string) => +id.trim()).filter((id: number) => !isNaN(id))
//           : []
//       };
//       const action = this.isEdit
//         ? this.loanSchemeService.updateLoanScheme(this.data.scheme!.id, this.data.adminId, {
//           interestRate: request.interestRate,
//           tenureMonths: request.tenureMonths,
//           requiredDocumentTypeIds: request.requiredDocumentTypeIds
//         })
//         : this.loanSchemeService.createLoanScheme(this.data.adminId, request);
//       action.subscribe({
//         next: () => this.dialogRef.close(true),
//         error: err => {
//           console.error('Scheme action failed:', err);
//           this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to save loan scheme.';
//         }
//       });
//     }
//   }

//   onCancel(): void {
//     this.dialogRef.close();
//   }
// }







import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
import { LoanSchemeRequest, LoanSchemeResponse, LoanSchemeUpdate } from '../../models/loan-scheme.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loan-scheme-form',
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
    <h2 mat-dialog-title>{{ isEdit ? 'Edit Loan Scheme' : 'Add Loan Scheme' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="form" (ngSubmit)="onSubmit()">
        <mat-form-field appearance="fill">
          <mat-label>Scheme Name</mat-label>
          <input matInput formControlName="schemeName" [disabled]="isEdit" />
          <mat-error *ngIf="form.get('schemeName')?.hasError('required')">
            Scheme name is required
          </mat-error>
        </mat-form-field>
        <mat-form-field appearance="fill">
          <mat-label>Interest Rate (%)</mat-label>
          <input matInput formControlName="interestRate" type="number" step="0.1" />
          <mat-error *ngIf="form.get('interestRate')?.hasError('required')">
            Interest rate is required
          </mat-error>
          <mat-error *ngIf="form.get('interestRate')?.hasError('min')">
            Interest rate must be non-negative
          </mat-error>
        </mat-form-field>
        <mat-form-field appearance="fill">
          <mat-label>Tenure (Months)</mat-label>
          <input matInput formControlName="tenureMonths" type="number" />
          <mat-error *ngIf="form.get('tenureMonths')?.hasError('required')">
            Tenure is required
          </mat-error>
          <mat-error *ngIf="form.get('tenureMonths')?.hasError('min')">
            Tenure must be at least 1 month
          </mat-error>
        </mat-form-field>
        <mat-form-field appearance="fill">
          <mat-label>Document Type IDs (e.g., 1,2,3)</mat-label>
          <input matInput formControlName="requiredDocumentTypeIds" />
          <mat-error *ngIf="form.get('requiredDocumentTypeIds')?.hasError('required')">
            At least one document type ID is required
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
        {{ isEdit ? 'Update' : 'Add' }}
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
      input[disabled] {
        color: rgba(0, 0, 0, 0.5);
        background-color: #f5f5f5;
      }
    `
  ]
})
export class LoanSchemeFormComponent {
  form: FormGroup;
  isEdit: boolean;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<LoanSchemeFormComponent>,
    private loanSchemeService: LoanSchemeService,
    @Inject(MAT_DIALOG_DATA) public data: { adminId: number; scheme?: LoanSchemeResponse }
  ) {
    this.isEdit = !!data.scheme;
    this.form = this.fb.group({
      schemeName: [
        { value: data.scheme?.schemeName || '', disabled: this.isEdit },
        this.isEdit ? [] : [Validators.required]
      ],
      interestRate: [data.scheme?.interestRate || '', [Validators.required, Validators.min(0)]],
      tenureMonths: [data.scheme?.tenureMonths || '', [Validators.required, Validators.min(1)]],
      requiredDocumentTypeIds: [
        data.scheme?.requiredDocumentTypeNames?.join(',') || '',
        [Validators.required]
      ]
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.errorMessage = null;
      const formValue = this.form.getRawValue(); // Use getRawValue to include disabled fields
      if (this.isEdit) {
        const updateRequest: LoanSchemeUpdate = {
          interestRate: +formValue.interestRate,
          tenureMonths: +formValue.tenureMonths,
          requiredDocumentTypeIds: formValue.requiredDocumentTypeIds
            ? formValue.requiredDocumentTypeIds
              .split(',')
              .map((id: string) => +id.trim())
              .filter((id: number) => !isNaN(id))
            : []
        };
        this.loanSchemeService
          .updateLoanScheme(this.data.scheme!.id, this.data.adminId, updateRequest)
          .subscribe({
            next: () => this.dialogRef.close(true),
            error: err => {
              console.error('Update scheme failed:', err);
              this.errorMessage =
                err.status === 0
                  ? 'Server unreachable. Check CORS settings.'
                  : err.status === 400
                    ? 'Invalid data provided.'
                    : 'Failed to update loan scheme.';
            }
          });
      } else {
        const createRequest: LoanSchemeRequest = {
          schemeName: formValue.schemeName,
          interestRate: +formValue.interestRate,
          tenureMonths: +formValue.tenureMonths,
          requiredDocumentTypeIds: formValue.requiredDocumentTypeIds
            ? formValue.requiredDocumentTypeIds
              .split(',')
              .map((id: string) => +id.trim())
              .filter((id: number) => !isNaN(id))
            : []
        };
        this.loanSchemeService.createLoanScheme(this.data.adminId, createRequest).subscribe({
          next: () => this.dialogRef.close(true),
          error: err => {
            console.error('Create scheme failed:', err);
            this.errorMessage =
              err.status === 0
                ? 'Server unreachable. Check CORS settings.'
                : err.status === 400
                  ? 'Invalid data provided.'
                  : 'Failed to create loan scheme.';
          }
        });
      }
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}