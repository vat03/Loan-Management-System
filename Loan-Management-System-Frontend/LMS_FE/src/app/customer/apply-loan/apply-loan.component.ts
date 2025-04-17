// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanScheme, Document } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatSelectModule } from '@angular/material/select';
// import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
// import { Router } from '@angular/router';
// import { HeaderComponent } from '../../shared/components/header/header.component';

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
//     MatProgressSpinnerModule,
//     HeaderComponent
//   ],
//   templateUrl: './apply-loan.component.html',
//   styleUrls: ['./apply-loan.component.scss']
// })
// export class ApplyLoanComponent implements OnInit {
//   customerId: number | null;
//   loanSchemes: LoanScheme[] = [];
//   applyForm: FormGroup;
//   documentForms: FormGroup[] = [];
//   selectedScheme: LoanScheme | null = null;
//   uploadedDocuments: Document[] = [];
//   error: string | null = null;
//   loading = false;
//   isSubmitDisabled = true;
//   displayedColumns: string[] = ['schemeName', 'interestRate', 'tenureMonths', 'action'];

//   constructor(
//     private fb: FormBuilder,
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.applyForm = this.fb.group({
//       loanSchemeId: ['', Validators.required],
//       amount: ['', [Validators.required, Validators.min(1000)]]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.error = 'Please log in to apply for a loan.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getLoanSchemes().subscribe({
//       next: (schemes) => this.loanSchemes = schemes.filter(s => !s.isDeleted),
//       error: (err) => {
//         this.error = `Failed to load loan schemes: ${err.message}`;
//       }
//     });

//     this.applyForm.valueChanges.subscribe(() => this.updateSubmitDisabled());
//     this.applyForm.statusChanges.subscribe(() => this.updateSubmitDisabled());
//   }

//   updateSubmitDisabled(): void {
//     this.isSubmitDisabled = this.applyForm.invalid ||
//       this.documentForms.some(f => f.invalid) ||
//       this.loading;
//   }

//   selectScheme(scheme: LoanScheme): void {
//     this.selectedScheme = scheme;
//     this.applyForm.patchValue({ loanSchemeId: scheme.id });
//     this.documentForms = scheme.requiredDocumentTypeNames.map(name =>
//       this.fb.group({
//         documentName: [`${name} Document`, Validators.required],
//         documentTypeId: [name === 'ID Proof' ? 1 : 2, Validators.required],
//         documentFile: [null, Validators.required]
//       })
//     );
//     this.documentForms.forEach(form => {
//       form.valueChanges.subscribe(() => this.updateSubmitDisabled());
//       form.statusChanges.subscribe(() => this.updateSubmitDisabled());
//     });
//     this.updateSubmitDisabled();
//   }

//   onFileChange(event: Event, index: number): void {
//     const input = event.target as HTMLInputElement;
//     if (input.files && input.files.length) {
//       this.documentForms[index].patchValue({ documentFile: input.files[0] });
//     }
//   }

//   apply(): void {
//     if (this.isSubmitDisabled || !this.customerId) {
//       return;
//     }

//     this.loading = true;
//     this.updateSubmitDisabled();
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
//         this.error = `Failed to apply for loan: ${err.message}`;
//         this.loading = false;
//         this.updateSubmitDisabled();
//       }
//     });
//   }

//   uploadDocuments(loanId: number): void {
//     let uploadedCount = 0;
//     this.documentForms.forEach(form => {
//       const formData = new FormData();
//       formData.append('documentName', form.value.documentName);
//       formData.append('documentFile', form.value.documentFile);
//       formData.append('customerId', this.customerId!.toString());
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
//             this.updateSubmitDisabled();
//           }
//         },
//         error: (err) => {
//           this.error = `Failed to upload document: ${err.message}`;
//           this.loading = false;
//           this.updateSubmitDisabled();
//         }
//       });
//     });
//   }
// }


































// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanScheme, Document, DocumentType } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatSelectModule } from '@angular/material/select';
// import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
// import { Router } from '@angular/router';
// import { HeaderComponent } from '../../shared/components/header/header.component';

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
//     MatProgressSpinnerModule,
//     HeaderComponent
//   ],
//   templateUrl: './apply-loan.component.html',
//   styleUrls: ['./apply-loan.component.scss']
// })
// export class ApplyLoanComponent implements OnInit {
//   customerId: number | null;
//   loanSchemes: LoanScheme[] = [];
//   applyForm: FormGroup;
//   documentForms: FormGroup[] = [];
//   requiredDocumentTypes: DocumentType[] = [];
//   selectedScheme: LoanScheme | null = null;
//   uploadedDocuments: Document[] = [];
//   error: string | null = null;
//   success: string | null = null;
//   loading = false;
//   isSubmitDisabled = true;
//   displayedColumns: string[] = ['schemeName', 'interestRate', 'tenureMonths', 'action'];

//   constructor(
//     private fb: FormBuilder,
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.applyForm = this.fb.group({
//       loanSchemeId: ['', Validators.required],
//       amount: ['', [Validators.required, Validators.min(1000)]]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.error = 'Please log in to apply for a loan.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getLoanSchemes().subscribe({
//       next: (schemes) => {
//         this.loanSchemes = schemes.filter(s => !s.isDeleted);
//       },
//       error: (err) => {
//         this.error = `Failed to load loan schemes: ${err.message}`;
//       }
//     });

//     this.applyForm.valueChanges.subscribe(() => this.updateSubmitDisabled());
//     this.applyForm.statusChanges.subscribe(() => this.updateSubmitDisabled());
//   }

//   updateSubmitDisabled(): void {
//     this.isSubmitDisabled = this.applyForm.invalid ||
//       this.documentForms.some(f => f.invalid) ||
//       this.uploadedDocuments.length < this.requiredDocumentTypes.length ||
//       this.loading;
//   }

//   selectScheme(scheme: LoanScheme): void {
//     this.selectedScheme = scheme;
//     this.applyForm.patchValue({ loanSchemeId: scheme.id });
//     this.uploadedDocuments = [];
//     this.documentForms = [];

//     this.customerService.getRequiredDocumentTypes(scheme.id).subscribe({
//       next: (docTypes) => {
//         this.requiredDocumentTypes = docTypes;
//         this.documentForms = docTypes.map(docType =>
//           this.fb.group({
//             documentName: [`${docType.typeName} Document`, Validators.required],
//             documentTypeId: [docType.id, Validators.required],
//             documentFile: [null, Validators.required]
//           })
//         );
//         this.documentForms.forEach(form => {
//           form.valueChanges.subscribe(() => this.updateSubmitDisabled());
//           form.statusChanges.subscribe(() => this.updateSubmitDisabled());
//         });
//         this.updateSubmitDisabled();
//       },
//       error: (err) => {
//         this.error = `Failed to load required document types: ${err.message}`;
//       }
//     });
//   }

//   onFileChange(event: Event, index: number): void {
//     const input = event.target as HTMLInputElement;
//     if (input.files && input.files.length) {
//       const file = input.files[0];
//       if (file.type !== 'image/png') {
//         this.error = 'Only PNG files are allowed.';
//         this.documentForms[index].patchValue({ documentFile: null });
//         return;
//       }
//       this.documentForms[index].patchValue({ documentFile: file });
//     }
//   }

//   uploadDocument(index: number): void {
//     if (this.documentForms[index].invalid || !this.customerId) {
//       return;
//     }

//     this.loading = true;
//     const form = this.documentForms[index];
//     const formData = new FormData();
//     formData.append('documentName', form.value.documentName);
//     formData.append('documentFile', form.value.documentFile);
//     formData.append('customerId', this.customerId.toString());
//     formData.append('documentTypeId', form.value.documentTypeId.toString());
//     if (this.selectedScheme) {
//       formData.append('loanSchemeId', this.selectedScheme.id.toString());
//     }

//     this.customerService.uploadDocument(formData).subscribe({
//       next: (doc) => {
//         this.uploadedDocuments.push(doc);
//         this.success = `Document ${doc.documentName} uploaded successfully.`;
//         this.error = null;
//         this.loading = false;
//         this.updateSubmitDisabled();
//       },
//       error: (err) => {
//         this.error = `Failed to upload document: ${err.message}`;
//         this.loading = false;
//         this.updateSubmitDisabled();
//       }
//     });
//   }

//   apply(): void {
//     if (this.isSubmitDisabled || !this.customerId) {
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
//         this.loading = false;
//         this.success = 'Loan application submitted successfully!';
//         this.error = null;
//         this.applyForm.reset();
//         this.documentForms = [];
//         this.requiredDocumentTypes = [];
//         this.uploadedDocuments = [];
//         this.selectedScheme = null;
//         this.router.navigate(['/customer/view-loans']);
//       },
//       error: (err) => {
//         this.error = `Failed to apply for loan: ${err.message}`;
//         this.loading = false;
//         this.updateSubmitDisabled();
//       }
//     });
//   }
// }







// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { MatInputModule } from '@angular/material/input';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanScheme, DocumentType } from '../models/customer.model';
// import { Router } from '@angular/router';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

// @Component({
//   selector: 'app-apply-loan',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatCardModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatSelectModule,
//     MatInputModule,
//     ReactiveFormsModule
//   ],
//   templateUrl: './apply-loan.component.html',
//   styleUrls: ['./apply-loan.component.scss']
// })
// export class ApplyLoanComponent implements OnInit {
//   loanSchemes: LoanScheme[] = [];
//   documentTypes: DocumentType[] = [];
//   applyForm: FormGroup;
//   errorMessage: string | null = null;
//   customerId: number | null;

//   constructor(
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router,
//     private fb: FormBuilder
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.applyForm = this.fb.group({
//       schemeId: ['', Validators.required],
//       amount: ['', [Validators.required, Validators.min(1000)]]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.errorMessage = 'Please log in to apply for a loan.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getLoanSchemes().subscribe({
//       next: (schemes) => {
//         this.loanSchemes = schemes;
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to load loan schemes: ${err.message}`;
//       }
//     });
//   }

//   onSelectScheme(): void {
//     const schemeId = this.applyForm.get('schemeId')?.value;
//     if (schemeId) {
//       this.customerService.getRequiredDocumentTypes(schemeId).subscribe({
//         next: (docTypes) => {
//           this.documentTypes = docTypes;
//           this.errorMessage = null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to load required document types: ${err.message}`;
//           this.documentTypes = [];
//         }
//       });
//     }
//   }

//   applyLoan(): void {
//     if (this.applyForm.valid && this.documentTypes.length > 0) {
//       const loanApplication = {
//         customerId: this.customerId,
//         schemeId: this.applyForm.value.schemeId,
//         amount: this.applyForm.value.amount
//       };

//       this.customerService.applyForLoan(loanApplication).subscribe({
//         next: () => {
//           this.router.navigate(['/customer/loans']);
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to apply for loan: ${err.message}`;
//         }
//       });
//     } else {
//       this.errorMessage = 'Please select a valid scheme and ensure document types are loaded.';
//     }
//   }
// }








// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { MatInputModule } from '@angular/material/input';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanScheme, DocumentType } from '../models/customer.model';
// import { Router } from '@angular/router';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { MatRow, MatHeaderRow, MatCell, MatHeaderCell, MatTable } from '@angular/material/table';

// @Component({
//   selector: 'app-apply-loan',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatCardModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatSelectModule,
//     MatInputModule,
//     ReactiveFormsModule,
//     MatRow,
//     MatHeaderRow,
//     MatCell, 
//     MatHeaderCell,
//     MatTable
//   ],
//   templateUrl: './apply-loan.component.html',
//   styleUrls: ['./apply-loan.component.scss']
// })
// export class ApplyLoanComponent implements OnInit {
//   loanSchemes: LoanScheme[] = [];
//   documentTypes: DocumentType[] = [];
//   applyForm: FormGroup;
//   errorMessage: string | null = null;
//   customerId: number | null;

//   constructor(
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router,
//     private fb: FormBuilder
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.applyForm = this.fb.group({
//       schemeId: ['', Validators.required],
//       amount: ['', [Validators.required, Validators.min(1000)]]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.errorMessage = 'Please log in to apply for a loan.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getLoanSchemes().subscribe({
//       next: (schemes) => {
//         this.loanSchemes = schemes;
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to load loan schemes: ${err.message}`;
//       }
//     });
//   }

//   onSelectScheme(): void {
//     const schemeId = this.applyForm.get('schemeId')?.value;
//     if (schemeId) {
//       this.customerService.getRequiredDocumentTypes(schemeId).subscribe({
//         next: (docTypes) => {
//           this.documentTypes = docTypes;
//           this.errorMessage = null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to load required document types: ${err.message}`;
//           this.documentTypes = [];
//         }
//       });
//     }
//   }

//   applyLoan(): void {
//     if (this.applyForm.valid && this.documentTypes.length > 0) {
//       const loanApplication = {
//         customerId: this.customerId,
//         schemeId: this.applyForm.value.schemeId,
//         amount: this.applyForm.value.amount
//       };

//       this.customerService.applyForLoan(loanApplication).subscribe({
//         next: () => {
//           this.router.navigate(['/customer/loans']);
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to apply for loan: ${err.message}`;
//         }
//       });
//     } else {
//       this.errorMessage = 'Please select a valid scheme and ensure document types are loaded.';
//     }
//   }
// }







// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { MatInputModule } from '@angular/material/input';
// import { MatRow, MatRowDef, MatTable, MatTableModule } from '@angular/material/table';
// import { MatTableDataSource } from '@angular/material/table';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanScheme, DocumentType, Document } from '../models/customer.model';
// import { Router } from '@angular/router';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

// @Component({
//   selector: 'app-apply-loan',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatCardModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatSelectModule,
//     MatInputModule,
//     MatTableModule,
//     ReactiveFormsModule,
//     MatRow,
//     MatRowDef,
//     MatTable
//   ],
//   templateUrl: './apply-loan.component.html',
//   styleUrls: ['./apply-loan.component.scss']
// })
// export class ApplyLoanComponent implements OnInit {
//   loanSchemes: LoanScheme[] = [];
//   documentTypes: DocumentType[] = [];
//   uploadedDocuments: MatTableDataSource<Document> = new MatTableDataSource<Document>([]);
//   applyForm: FormGroup;
//   documentForm: FormGroup;
//   errorMessage: string | null = null;
//   customerId: number | null;
//   displayedDocumentColumns: string[] = ['documentType', 'fileName', 'actions'];

//   constructor(
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router,
//     private fb: FormBuilder
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.applyForm = this.fb.group({
//       schemeId: ['', Validators.required],
//       amount: ['', [Validators.required, Validators.min(1000)]]
//     });
//     this.documentForm = this.fb.group({
//       documentTypeId: ['', Validators.required],
//       file: [null, Validators.required]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.errorMessage = 'Please log in to apply for a loan.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getLoanSchemes().subscribe({
//       next: (schemes) => {
//         this.loanSchemes = schemes;
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to load loan schemes: ${err.message}`;
//       }
//     });
//   }

//   onSelectScheme(): void {
//     const schemeId = this.applyForm.get('schemeId')?.value;
//     if (schemeId) {
//       this.customerService.getRequiredDocumentTypes(schemeId).subscribe({
//         next: (docTypes) => {
//           this.documentTypes = docTypes;
//           this.errorMessage = null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to load required document types: ${err.message}`;
//           this.documentTypes = [];
//         }
//       });
//     }
//   }

//   onFileSelected(event: Event): void {
//     const input = event.target as HTMLInputElement;
//     if (input.files && input.files.length > 0) {
//       this.documentForm.patchValue({ file: input.files[0] });
//     }
//   }

//   // uploadDocument(): void {
//   //   if (this.documentForm.valid) {
//   //     const formData = new FormData();
//   //     formData.append('file', this.documentForm.value.file);
//   //     formData.append('documentTypeId', this.documentForm.value.documentTypeId);
//   //     formData.append('customerId', this.customerId!.toString());

//   //     this.customerService.uploadDocument(formData).subscribe({
//   //       next: (doc) => {
//   //         const currentDocs = this.uploadedDocuments.data;
//   //         this.uploadedDocuments.data = [...currentDocs, doc];
//   //         this.documentForm.reset();
//   //         this.errorMessage = null;
//   //       },
//   //       error: (err: Error) => {
//   //         this.errorMessage = `Failed to upload document: ${err.message}`;
//   //       }
//   //     });
//   //   }
//   // }

//   uploadDocument(): void {
//     if (this.documentForm.valid) {
//       const formData = new FormData();
//       formData.append('file', this.documentForm.value.file);
//       formData.append('documentTypeId', this.documentForm.value.documentTypeId);
//       formData.append('customerId', this.customerId!.toString());

//       this.customerService.uploadDocument(formData).subscribe({
//         next: (doc) => {
//           const currentDocs = this.uploadedDocuments.data;
//           this.uploadedDocuments.data = [...currentDocs, doc];
//           this.documentForm.reset();
//           const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
//           if (fileInput) fileInput.value = '';
//           this.errorMessage = null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to upload document: ${err.message}`;
//         }
//       });
//     }
//   }

//   removeDocument(documentId: number): void {
//     this.uploadedDocuments.data = this.uploadedDocuments.data.filter(doc => doc.documentId !== documentId);
//   }

//   applyLoan(): void {
//     if (this.applyForm.valid && this.documentTypes.length > 0 && this.uploadedDocuments.data.length > 0) {
//       const loanApplication = {
//         customerId: this.customerId,
//         schemeId: this.applyForm.value.schemeId,
//         amount: this.applyForm.value.amount,
//         documentIds: this.uploadedDocuments.data.map(doc => doc.documentId)
//       };

//       this.customerService.applyForLoan(loanApplication).subscribe({
//         next: () => {
//           this.router.navigate(['/customer/loans']);
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to apply for loan: ${err.message}`;
//         }
//       });
//     } else {
//       this.errorMessage = 'Please select a valid scheme, upload required documents, and ensure all fields are valid.';
//     }
//   }
// }




// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatSelectModule } from '@angular/material/select';
// import { MatInputModule } from '@angular/material/input';
// import { MatTableModule } from '@angular/material/table';
// import { MatTableDataSource } from '@angular/material/table';
// import { CustomerService } from '../services/customer.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanScheme, DocumentType, Document } from '../models/customer.model';
// import { Router } from '@angular/router';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

// @Component({
//   selector: 'app-apply-loan',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatCardModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatSelectModule,
//     MatInputModule,
//     MatTableModule,
//     ReactiveFormsModule
//   ],
//   templateUrl: './apply-loan.component.html',
//   styleUrls: ['./apply-loan.component.scss']
// })
// export class ApplyLoanComponent implements OnInit {
//   loanSchemes: LoanScheme[] = [];
//   documentTypes: DocumentType[] = [];
//   uploadedDocuments: MatTableDataSource<Document> = new MatTableDataSource<Document>([]);
//   applyForm: FormGroup;
//   documentForm: FormGroup;
//   errorMessage: string | null = null;
//   customerId: number | null;
//   displayedDocumentColumns: string[] = ['documentType', 'fileName', 'actions'];

//   constructor(
//     private customerService: CustomerService,
//     private authService: AuthService,
//     private router: Router,
//     private fb: FormBuilder
//   ) {
//     this.customerId = this.authService.getCustomerId();
//     this.applyForm = this.fb.group({
//       schemeId: ['', Validators.required],
//       amount: ['', [Validators.required, Validators.min(1000)]]
//     });
//     this.documentForm = this.fb.group({
//       documentTypeId: ['', Validators.required],
//       file: [null, Validators.required]
//     });
//   }

//   ngOnInit(): void {
//     if (!this.customerId) {
//       this.errorMessage = 'Please log in to apply for a loan.';
//       this.router.navigate(['/login']);
//       return;
//     }

//     this.customerService.getLoanSchemes().subscribe({
//       next: (schemes) => {
//         this.loanSchemes = schemes;
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to load loan schemes: ${err.message}`;
//       }
//     });
//   }

//   onSelectScheme(): void {
//     const schemeId = this.applyForm.get('schemeId')?.value;
//     if (schemeId) {
//       this.customerService.getRequiredDocumentTypes(schemeId).subscribe({
//         next: (docTypes) => {
//           this.documentTypes = docTypes;
//           this.errorMessage = null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to load required document types: ${err.message}`;
//           this.documentTypes = [];
//         }
//       });
//     }
//   }

//   onFileSelected(event: Event): void {
//     const input = event.target as HTMLInputElement;
//     if (input.files && input.files.length > 0) {
//       this.documentForm.patchValue({ file: input.files[0] });
//     }
//   }

//   uploadDocument(): void {
//     if (this.documentForm.valid) {
//       const formData = new FormData();
//       formData.append('file', this.documentForm.value.file);
//       formData.append('documentTypeId', this.documentForm.value.documentTypeId);
//       formData.append('customerId', this.customerId!.toString());

//       this.customerService.uploadDocument(formData).subscribe({
//         next: (doc) => {
//           const currentDocs = this.uploadedDocuments.data;
//           this.uploadedDocuments.data = [...currentDocs, doc];
//           this.documentForm.reset();
//           const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
//           if (fileInput) fileInput.value = '';
//           this.errorMessage = null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to upload document: ${err.message}`;
//         }
//       });
//     }
//   }

//   removeDocument(documentId: number): void {
//     this.uploadedDocuments.data = this.uploadedDocuments.data.filter(doc => doc.documentId !== documentId);
//   }

//   applyLoan(): void {
//     if (this.applyForm.valid && this.documentTypes.length > 0 && this.uploadedDocuments.data.length > 0) {
//       const loanApplication = {
//         customerId: this.customerId,
//         schemeId: this.applyForm.value.schemeId,
//         amount: this.applyForm.value.amount,
//         documentIds: this.uploadedDocuments.data.map(doc => doc.documentId)
//       };

//       this.customerService.applyForLoan(loanApplication).subscribe({
//         next: () => {
//           this.router.navigate(['/customer/loans']);
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to apply for loan: ${err.message}`;
//         }
//       });
//     } else {
//       this.errorMessage = 'Please select a valid scheme, upload required documents, and ensure all fields are valid.';
//     }
//   }

//   getDocumentTypeName(documentTypeId: number): string {
//     const docType = this.documentTypes.find(dt => dt.id === documentTypeId);
//     return docType ? docType.typeName : 'Unknown';
//   }
// }



import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatTableDataSource } from '@angular/material/table';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../core/auth/auth.service';
import { LoanScheme, DocumentType, Document } from '../models/customer.model';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-apply-loan',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatTableModule,
    ReactiveFormsModule
  ],
  templateUrl: './apply-loan.component.html',
  styleUrls: ['./apply-loan.component.scss']
})
export class ApplyLoanComponent implements OnInit {
  loanSchemes: LoanScheme[] = [];
  documentTypes: DocumentType[] = [];
  uploadedDocuments: MatTableDataSource<Document> = new MatTableDataSource<Document>([]);
  applyForm: FormGroup;
  documentForm: FormGroup;
  errorMessage: string | null = null;
  customerId: number | null;
  displayedDocumentColumns: string[] = ['documentType', 'fileName', 'actions'];

  constructor(
    private customerService: CustomerService,
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.customerId = this.authService.getCustomerId();
    this.applyForm = this.fb.group({
      schemeId: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1000)]]
    });
    this.documentForm = this.fb.group({
      documentTypeId: ['', Validators.required],
      file: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    if (!this.customerId) {
      this.errorMessage = 'Please log in to apply for a loan.';
      this.router.navigate(['/login']);
      return;
    }

    this.customerService.getLoanSchemes().subscribe({
      next: (schemes) => {
        this.loanSchemes = schemes;
      },
      error: (err: Error) => {
        this.errorMessage = `Failed to load loan schemes: ${err.message}`;
      }
    });
  }

  onSelectScheme(): void {
    const schemeId = this.applyForm.get('schemeId')?.value;
    if (schemeId) {
      this.customerService.getRequiredDocumentTypes(schemeId).subscribe({
        next: (docTypes) => {
          this.documentTypes = docTypes;
          this.errorMessage = null;
        },
        error: (err: Error) => {
          this.errorMessage = `Failed to load required document types: ${err.message}`;
          this.documentTypes = [];
        }
      });
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.documentForm.patchValue({ file: input.files[0] });
    }
  }

  uploadDocument(): void {
    if (this.documentForm.valid) {
      const formData = new FormData();
      const file = this.documentForm.value.file;
      const documentTypeId = this.documentForm.value.documentTypeId;
      const docType = this.documentTypes.find(dt => dt.id === documentTypeId);
      const documentName = docType ? docType.typeName : file.name; // Use typeName or file name

      formData.append('documentFile', file);
      formData.append('documentName', documentName);
      formData.append('documentTypeId', documentTypeId.toString());
      formData.append('customerId', this.customerId!.toString());

      this.customerService.uploadDocument(formData).subscribe({
        next: (doc) => {
          const currentDocs = this.uploadedDocuments.data;
          this.uploadedDocuments.data = [...currentDocs, doc];
          this.documentForm.reset();
          const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
          if (fileInput) fileInput.value = '';
          this.errorMessage = null;
        },
        error: (err: Error) => {
          // Enhanced error handling for backend validation
          if (err.message.includes('Server error: 400')) {
            this.errorMessage = 'Upload failed: Please ensure a valid document type and file are selected.';
          } else {
            this.errorMessage = `Failed to upload document: ${err.message}`;
          }
        }
      });
    } else {
      this.errorMessage = 'Please select a document type and file before uploading.';
    }
  }

  removeDocument(documentId: number): void {
    this.uploadedDocuments.data = this.uploadedDocuments.data.filter(doc => doc.documentId !== documentId);
  }

  applyLoan(): void {
    if (this.applyForm.valid && this.documentTypes.length > 0 && this.uploadedDocuments.data.length > 0) {
      const loanApplication = {
        customerId: this.customerId,
        schemeId: this.applyForm.value.schemeId,
        amount: this.applyForm.value.amount,
        documentIds: this.uploadedDocuments.data.map(doc => doc.documentId)
      };

      this.customerService.applyForLoan(loanApplication).subscribe({
        next: () => {
          this.router.navigate(['/customer/loans']);
        },
        error: (err: Error) => {
          this.errorMessage = `Failed to apply for loan: ${err.message}`;
        }
      });
    } else {
      this.errorMessage = 'Please select a valid scheme, upload required documents, and ensure all fields are valid.';
    }
  }

  getDocumentTypeName(documentTypeId: number): string {
    const docType = this.documentTypes.find(dt => dt.id === documentTypeId);
    return docType ? docType.typeName : 'Unknown';
  }
}