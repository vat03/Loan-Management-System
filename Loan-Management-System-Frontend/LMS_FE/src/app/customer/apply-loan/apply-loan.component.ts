// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-apply-loan',
//   standalone: true,
//   imports: [],
//   templateUrl: './apply-loan.component.html',
//   styleUrl: './apply-loan.component.scss'
// })
// export class ApplyLoanComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { LoanScheme, Document } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatSelectModule } from '@angular/material/select';
// import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

// @Component({
//   selector: 'app-apply-loan',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatTableModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatInputModule,
//     MatSelectModule,
//     MatProgressSpinnerModule
//   ],
//   templateUrl: './apply-loan.component.html',
//   styleUrls: ['./apply-loan.component.scss']
// })
// export class ApplyLoanComponent implements OnInit {
//   customerId = 1; // TODO: Replace with AuthService.getCurrentUserId()
//   loanSchemes: LoanScheme[] = [];
//   applyForm: FormGroup;
//   documentForms: FormGroup[] = [];
//   selectedScheme: LoanScheme | null = null;
//   uploadedDocuments: Document[] = [];
//   error: string | null = null;
//   loading = false;
//   displayedColumns: string[] = ['schemeName', 'interestRate', 'tenureMonths', 'action'];

//   constructor(private fb: FormBuilder, private customerService: CustomerService) {
//     this.applyForm = this.fb.group({
//       loanSchemeId: ['', Validators.required],
//       amount: ['', [Validators.required, Validators.min(1000)]]
//     });
//   }

//   ngOnInit(): void {
//     this.customerService.getLoanSchemes().subscribe({
//       next: (schemes) => this.loanSchemes = schemes.filter(s => !s.isDeleted),
//       error: (err) => this.error = err.message
//     });
//   }

//   selectScheme(scheme: LoanScheme): void {
//     this.selectedScheme = scheme;
//     this.applyForm.patchValue({ loanSchemeId: scheme.id });
//     this.documentForms = scheme.requiredDocumentTypeNames.map(name =>
//       this.fb.group({
//         documentName: [`${name} Document`, Validators.required],
//         documentTypeId: [name === 'ID Proof' ? 1 : 2, Validators.required], // Hardcoded IDs
//         documentFile: [null, Validators.required]
//       })
//     );
//   }

//   onFileChange(event: Event, index: number): void {
//     const input = event.target as HTMLInputElement;
//     if (input.files && input.files.length) {
//       this.documentForms[index].patchValue({ documentFile: input.files[0] });
//     }
//   }

//   apply(): void {
//     if (this.applyForm.invalid || this.documentForms.some(f => f.invalid)) {
//       return;
//     }

//     this.loading = true;
//     const loan = {
//       amount: this.applyForm.value.amount,
//       loanSchemeId: this.applyForm.value.loanSchemeId,
//       customerId: this.customerId
//     };

//     this.customerService.applyForLoan(loan).subscribe({
//       next: (newLoan) => {
//         this.uploadDocuments(newLoan.loanId);
//       },
//       error: (err) => {
//         this.error = err.message;
//         this.loading = false;
//       }
//     });
//   }

//   uploadDocuments(loanId: number): void {
//     let uploadedCount = 0;
//     this.documentForms.forEach(form => {
//       const formData = new FormData();
//       formData.append('documentName', form.value.documentName);
//       formData.append('documentFile', form.value.documentFile);
//       formData.append('customerId', this.customerId.toString());
//       formData.append('documentTypeId', form.value.documentTypeId.toString());
//       formData.append('loanId', loanId.toString());

//       this.customerService.uploadDocument(formData).subscribe({
//         next: (doc) => {
//           this.uploadedDocuments.push(doc);
//           uploadedCount++;
//           if (uploadedCount === this.documentForms.length) {
//             this.loading = false;
//             this.error = null;
//             alert('Loan application submitted successfully!');
//             this.applyForm.reset();
//             this.documentForms = [];
//             this.selectedScheme = null;
//           }
//         },
//         error: (err) => {
//           this.error = err.message;
//           this.loading = false;
//         }
//       });
//     });
//   }
// }


import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../core/auth/auth.service';
import { LoanScheme, Document } from '../models/customer.model';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router } from '@angular/router';

@Component({
  selector: 'app-apply-loan',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './apply-loan.component.html',
  styleUrls: ['./apply-loan.component.scss']
})
export class ApplyLoanComponent implements OnInit {
  customerId: number | null;
  loanSchemes: LoanScheme[] = [];
  applyForm: FormGroup;
  documentForms: FormGroup[] = [];
  selectedScheme: LoanScheme | null = null;
  uploadedDocuments: Document[] = [];
  error: string | null = null;
  loading = false;
  isSubmitDisabled = true;
  displayedColumns: string[] = ['schemeName', 'interestRate', 'tenureMonths', 'action'];

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private authService: AuthService,
    private router: Router
  ) {
    this.customerId = this.authService.getCustomerId();
    this.applyForm = this.fb.group({
      loanSchemeId: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1000)]]
    });
  }

  ngOnInit(): void {
    if (!this.customerId) {
      this.error = 'Please log in to apply for a loan.';
      this.router.navigate(['/login']);
      return;
    }

    this.customerService.getLoanSchemes().subscribe({
      next: (schemes) => this.loanSchemes = schemes.filter(s => !s.isDeleted),
      error: (err) => {
        this.error = err.message;
        this.router.navigate(['/login']);
      }
    });

    // Watch form changes to update submit button state
    this.applyForm.valueChanges.subscribe(() => this.updateSubmitDisabled());
    this.applyForm.statusChanges.subscribe(() => this.updateSubmitDisabled());
  }

  updateSubmitDisabled(): void {
    this.isSubmitDisabled = this.applyForm.invalid ||
      this.documentForms.some(f => f.invalid) ||
      this.loading;
  }

  selectScheme(scheme: LoanScheme): void {
    this.selectedScheme = scheme;
    this.applyForm.patchValue({ loanSchemeId: scheme.id });
    this.documentForms = scheme.requiredDocumentTypeNames.map(name =>
      this.fb.group({
        documentName: [`${name} Document`, Validators.required],
        documentTypeId: [name === 'ID Proof' ? 1 : 2, Validators.required], // Hardcoded IDs
        documentFile: [null, Validators.required]
      })
    );
    // Subscribe to document form changes
    this.documentForms.forEach(form => {
      form.valueChanges.subscribe(() => this.updateSubmitDisabled());
      form.statusChanges.subscribe(() => this.updateSubmitDisabled());
    });
    this.updateSubmitDisabled();
  }

  onFileChange(event: Event, index: number): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.documentForms[index].patchValue({ documentFile: input.files[0] });
    }
  }

  apply(): void {
    if (this.isSubmitDisabled || !this.customerId) {
      return;
    }

    this.loading = true;
    this.updateSubmitDisabled();
    const loan = {
      amount: this.applyForm.value.amount,
      loanSchemeId: this.applyForm.value.loanSchemeId,
      customerId: this.customerId
    };

    this.customerService.applyForLoan(loan).subscribe({
      next: (newLoan) => {
        this.uploadDocuments(newLoan.loanId);
      },
      error: (err) => {
        this.error = err.message;
        this.loading = false;
        this.updateSubmitDisabled();
      }
    });
  }

  uploadDocuments(loanId: number): void {
    let uploadedCount = 0;
    this.documentForms.forEach(form => {
      const formData = new FormData();
      formData.append('documentName', form.value.documentName);
      formData.append('documentFile', form.value.documentFile);
      formData.append('customerId', this.customerId!.toString());
      formData.append('documentTypeId', form.value.documentTypeId.toString());
      formData.append('loanId', loanId.toString());

      this.customerService.uploadDocument(formData).subscribe({
        next: (doc) => {
          this.uploadedDocuments.push(doc);
          uploadedCount++;
          if (uploadedCount === this.documentForms.length) {
            this.loading = false;
            this.error = null;
            alert('Loan application submitted successfully!');
            this.applyForm.reset();
            this.documentForms = [];
            this.selectedScheme = null;
            this.updateSubmitDisabled();
          }
        },
        error: (err) => {
          this.error = err.message;
          this.loading = false;
          this.updateSubmitDisabled();
        }
      });
    });
  }
}