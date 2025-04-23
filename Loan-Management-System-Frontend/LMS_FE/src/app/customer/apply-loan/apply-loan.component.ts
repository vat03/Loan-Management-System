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
//   successMessage: string | null = null; // Added for success feedback
//   customerId: number | null;
//   displayedDocumentColumns: string[] = ['documentType', 'fileName', 'viewDocument', 'actions'];

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
//       const file = this.documentForm.value.file;
//       const documentTypeId = this.documentForm.value.documentTypeId;
//       const docType = this.documentTypes.find(dt => dt.id === documentTypeId);
//       const documentName = docType ? docType.typeName : file.name;

//       formData.append('documentFile', file);
//       formData.append('documentName', documentName);
//       formData.append('documentTypeId', documentTypeId.toString());
//       formData.append('customerId', this.customerId!.toString());

//       this.customerService.uploadDocument(formData).subscribe({
//         next: (doc) => {
//           if (doc.documentId && doc.documentUrl && doc.documentTypeId) {
//             const currentDocs = this.uploadedDocuments.data;
//             this.uploadedDocuments.data = [...currentDocs, doc];
//             this.documentForm.reset();
//             const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
//             if (fileInput) fileInput.value = '';
//             this.errorMessage = null;
//           } else {
//             this.errorMessage = 'Invalid document response from server. Please try again.';
//           }
//         },
//         error: (err: Error) => {
//           if (err.message.includes('Server error: 400')) {
//             this.errorMessage = 'Upload failed: Please ensure a valid document type and file are selected.';
//           } else {
//             this.errorMessage = `Failed to upload document: ${err.message}`;
//           }
//         }
//       });
//     } else {
//       this.errorMessage = 'Please select a document type and file before uploading.';
//     }
//   }

//   removeDocument(documentId: number): void {
//     this.uploadedDocuments.data = this.uploadedDocuments.data.filter(doc => doc.documentId !== documentId);
//   }

//   applyLoan(): void {
//     if (this.applyForm.valid && this.documentTypes.length > 0 && this.uploadedDocuments.data.length > 0) {
//       // Clear previous messages
//       this.errorMessage = null;
//       this.successMessage = null;

//       const loanApplication = {
//         customerId: this.customerId,
//         loanSchemeId: this.applyForm.value.schemeId,
//         amount: this.applyForm.value.amount // Backend expects BigDecimal, API handles conversion
//       };

//       console.log('Submitting loan application:', loanApplication);

//       this.customerService.applyForLoan(loanApplication).subscribe({
//         next: () => {
//           console.log('Loan application successful, navigating to /customer/loans');
//           this.successMessage = 'Your application has been sent successfully to the loan officer.';
//           setTimeout(() => {
//             this.router.navigate(['/customer/view-loans']).then(success => {
//               console.log('Navigation to /customer/loans successful:', success);
//               if (!success) {
//                 console.error('Navigation failed, redirected to:', this.router.url);
//               }
//             });
//           }, 2000); // Delay to show success message
//         },
//         error: (err: Error) => {
//           console.error('Loan application error:', err);
//           this.errorMessage = 'There was an error sending the application to the loan officer, please apply again.';
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

// upar wala best chal rha hai

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
  uploadedDocumentIds: number[] = [];
  applyForm: FormGroup;
  documentForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  customerId: number | null;
  displayedDocumentColumns: string[] = ['documentType', 'fileName', 'viewDocument', 'actions'];

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
      const file = this.documentForm.value.file;
      const documentTypeId = this.documentForm.value.documentTypeId;
      const docType = this.documentTypes.find(dt => dt.id === documentTypeId);
      const documentName = docType ? docType.typeName : file.name;

      const formData = new FormData();
      formData.append('documentFile', file);
      formData.append('documentName', documentName);
      formData.append('documentTypeId', documentTypeId.toString());
      formData.append('customerId', this.customerId!.toString());

      this.customerService.uploadDocument(formData).subscribe({
        next: (doc) => {
          const currentDocs = this.uploadedDocuments.data;
          this.uploadedDocuments.data = [...currentDocs, doc];
          this.uploadedDocumentIds.push(doc.documentId);
          this.documentForm.reset();
          const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
          if (fileInput) fileInput.value = '';
          this.errorMessage = null;
        },
        error: (err: Error) => {
          this.errorMessage = `Failed to upload document: ${err.message}`;
        }
      });
    } else {
      this.errorMessage = 'Please select a document type and file before uploading.';
    }
  }

  removeDocument(documentId: number): void {
    this.uploadedDocuments.data = this.uploadedDocuments.data.filter(doc => doc.documentId !== documentId);
    this.uploadedDocumentIds = this.uploadedDocumentIds.filter(id => id !== documentId);
  }

  applyLoan(): void {
    if (this.applyForm.valid && this.documentTypes.length > 0 && this.uploadedDocuments.data.length > 0) {
      this.errorMessage = null;
      this.successMessage = null;

      const loanApplication = {
        customerId: this.customerId,
        loanSchemeId: this.applyForm.value.schemeId,
        amount: this.applyForm.value.amount
      };

      console.log('Submitting loan application:', loanApplication);

      this.customerService.applyForLoan(loanApplication).subscribe({
        next: (loan) => {
          console.log('Loan application successful, loan ID:', loan.loanId);
          this.customerService.updateDocumentLoanIds(this.uploadedDocumentIds, loan.loanId).subscribe({
            next: () => {
              console.log('Documents associated with loan ID:', loan.loanId);
              this.successMessage = 'Your application and documents have been sent successfully to the loan officer.';
              setTimeout(() => {
                this.router.navigate(['/customer/view-loans']).then(success => {
                  console.log('Navigation to /customer/loans successful:', success);
                  if (!success) {
                    console.error('Navigation failed, redirected to:', this.router.url);
                  }
                });
              }, 2000);
            },
            error: (err: Error) => {
              console.error('Document association error:', err);
              this.errorMessage = 'Loan applied successfully, but failed to associate documents. Please contact support.';
            }
          });
        },
        error: (err: Error) => {
          console.error('Loan application error:', err);
          this.errorMessage = 'There was an error sending the application to the loan officer, please apply again.';
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