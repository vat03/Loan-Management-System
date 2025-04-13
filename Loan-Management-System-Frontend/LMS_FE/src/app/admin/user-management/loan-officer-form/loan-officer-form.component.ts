// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-loan-officer-form',
//   standalone: false,
//   templateUrl: './loan-officer-form.component.html',
//   styleUrl: './loan-officer-form.component.scss'
// })
// export class LoanOfficerFormComponent {

// }

// import { Component, Inject } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
// import { LoanOfficerService } from '../../../core/services/loan-officer.service';
// import { LoanOfficerRequest } from '../../models/loan-officer.model';

// @Component({
//   selector: 'app-loan-officer-form',
//   templateUrl: './loan-officer-form.component.html',
//   styleUrls: ['./loan-officer-form.component.scss']
// })
// export class LoanOfficerFormComponent {
//   form: FormGroup;

//   constructor(
//     private fb: FormBuilder,
//     private dialogRef: MatDialogRef<LoanOfficerFormComponent>,
//     private loanOfficerService: LoanOfficerService,
//     @Inject(MAT_DIALOG_DATA) public data: { adminId: number }
//   ) {
//     this.form = this.fb.group({
//       username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
//       email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
//       password: [
//         '',
//         [
//           Validators.required,
//           Validators.minLength(8),
//           Validators.maxLength(100),
//           Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/)
//         ]
//       ]
//     });
//   }

//   onSubmit(): void {
//     if (this.form.valid) {
//       const request: LoanOfficerRequest = this.form.value;
//       this.loanOfficerService.addLoanOfficer(this.data.adminId, request).subscribe({
//         next: () => this.dialogRef.close(true),
//         error: err => console.error('Add officer failed', err)
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
import { LoanOfficerService } from '../../../core/services/loan-officer.service';
import { LoanOfficerRequest } from '../../models/loan-officer.model';

@Component({
  selector: 'app-loan-officer-form',
  templateUrl: './loan-officer-form.component.html',
  styleUrls: ['./loan-officer-form.component.scss']
})
export class LoanOfficerFormComponent {
  form: FormGroup;

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
      const request: LoanOfficerRequest = this.form.value;
      this.loanOfficerService.addLoanOfficer(this.data.adminId, request).subscribe({
        next: () => this.dialogRef.close(true),
        error: err => console.error('Add officer failed', err)
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}