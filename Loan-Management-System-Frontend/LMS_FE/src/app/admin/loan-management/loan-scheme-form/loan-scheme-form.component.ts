// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-loan-scheme-form',
//   standalone: false,
//   templateUrl: './loan-scheme-form.component.html',
//   styleUrl: './loan-scheme-form.component.scss'
// })
// export class LoanSchemeFormComponent {

// }


// import { Component, Inject } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
// import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
// import { LoanSchemeRequest, LoanSchemeResponse } from '../../models/loan-scheme.model';

// @Component({
//   selector: 'app-loan-scheme-form',
//   templateUrl: './loan-scheme-form.component.html',
//   styleUrls: ['./loan-scheme-form.component.scss']
// })
// export class LoanSchemeFormComponent {
//   form: FormGroup;
//   isEdit: boolean;

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
//         data.scheme?.requiredDocumentTypeNames || [],
//         [Validators.required, Validators.minLength(1)]
//       ]
//     });
//   }

//   onSubmit(): void {
//     if (this.form.valid) {
//       const request: LoanSchemeRequest = this.form.value;
//       const action = this.isEdit
//         ? this.loanSchemeService.updateLoanScheme(this.data.scheme!.id, this.data.adminId, request)
//         : this.loanSchemeService.createLoanScheme(this.data.adminId, request);
//       action.subscribe({
//         next: () => this.dialogRef.close(true),
//         error: err => console.error('Scheme action failed', err)
//       });
//     }
//   }

//   onCancel(): void {
//     this.dialogRef.close();
//   }
// }






import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
import { LoanSchemeRequest, LoanSchemeResponse } from '../../models/loan-scheme.model';

@Component({
  selector: 'app-loan-scheme-form',
  templateUrl: './loan-scheme-form.component.html',
  styleUrls: ['./loan-scheme-form.component.scss']
})
export class LoanSchemeFormComponent {
  form: FormGroup;
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<LoanSchemeFormComponent>,
    private loanSchemeService: LoanSchemeService,
    @Inject(MAT_DIALOG_DATA) public data: { adminId: number; scheme?: LoanSchemeResponse }
  ) {
    this.isEdit = !!data.scheme;
    this.form = this.fb.group({
      schemeName: [data.scheme?.schemeName || '', [Validators.required]],
      interestRate: [data.scheme?.interestRate || '', [Validators.required, Validators.min(0)]],
      tenureMonths: [data.scheme?.tenureMonths || '', [Validators.required, Validators.min(1)]],
      requiredDocumentTypeIds: [
        data.scheme?.requiredDocumentTypeNames || [],
        [Validators.required, Validators.minLength(1)]
      ]
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      const request: LoanSchemeRequest = this.form.value;
      const action = this.isEdit
        ? this.loanSchemeService.updateLoanScheme(this.data.scheme!.id, this.data.adminId, request)
        : this.loanSchemeService.createLoanScheme(this.data.adminId, request);
      action.subscribe({
        next: () => this.dialogRef.close(true),
        error: err => console.error('Scheme action failed', err)
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}