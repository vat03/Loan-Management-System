// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-loan-scheme-list',
//   standalone: true,
//   imports: [],
//   templateUrl: './loan-scheme-list.component.html',
//   styleUrl: './loan-scheme-list.component.scss'
// })
// export class LoanSchemeListComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { MatDialog } from '@angular/material/dialog';
// import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
// import { AuthService } from '../../../core/auth/auth.service';
// import { LoanSchemeResponse, LoanSchemeSoftDelete } from '../../models/loan-scheme.model';
// import { LoanSchemeFormComponent } from '../loan-scheme-form/loan-scheme-form.component';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatIconModule } from '@angular/material/icon';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-loan-scheme-list',
//   standalone: true,
//   imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule],
//   templateUrl: './loan-scheme-list.component.html',
//   styleUrls: ['./loan-scheme-list.component.scss']
// })
// export class LoanSchemeListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'schemeName', 'interestRate', 'tenureMonths', 'actions'];
//   dataSource: LoanSchemeResponse[] = [];

//   constructor(
//     private loanSchemeService: LoanSchemeService,
//     private dialog: MatDialog,
//     private authService: AuthService
//   ) { }

//   ngOnInit(): void {
//     this.loadSchemes();
//   }

//   loadSchemes(): void {
//     this.loanSchemeService.getAllLoanSchemes().subscribe({
//       next: schemes => (this.dataSource = schemes),
//       error: err => console.error('Fetch schemes failed', err)
//     });
//   }

//   editScheme(scheme: LoanSchemeResponse): void {
//     this.dialog
//       .open(LoanSchemeFormComponent, {
//         width: '400px',
//         data: { adminId: this.authService.getUserId(), scheme }
//       })
//       .afterClosed()
//       .subscribe(result => {
//         if (result) this.loadSchemes();
//       });
//   }

//   deleteScheme(id: number): void {
//     if (confirm('Are you sure you want to delete this scheme?')) {
//       const request: LoanSchemeSoftDelete = { adminId: this.authService.getUserId()! };
//       this.loanSchemeService.softDeleteLoanScheme(id, request).subscribe({
//         next: () => this.loadSchemes(),
//         error: err => console.error('Delete scheme failed', err)
//       });
//     }
//   }
// }




import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
import { AuthService } from '../../../core/auth/auth.service';
import { LoanSchemeResponse, LoanSchemeSoftDelete } from '../../models/loan-scheme.model';
import { LoanSchemeFormComponent } from '../loan-scheme-form/loan-scheme-form.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loan-scheme-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule],
  template: `
    <div class="container">
      <h2>Loan Schemes</h2>
      <div *ngIf="errorMessage" class="error-message">{{ errorMessage }}</div>
      <table mat-table [dataSource]="dataSource">
        <ng-container matColumnDef="id">
          <th mat-header-cell *matHeaderCellDef>ID</th>
          <td mat-cell *matCellDef="let scheme">{{ scheme.id }}</td>
        </ng-container>
        <ng-container matColumnDef="schemeName">
          <th mat-header-cell *matHeaderCellDef>Name</th>
          <td mat-cell *matCellDef="let scheme">{{ scheme.schemeName }}</td>
        </ng-container>
        <ng-container matColumnDef="interestRate">
          <th mat-header-cell *matHeaderCellDef>Rate (%)</th>
          <td mat-cell *matCellDef="let scheme">{{ scheme.interestRate }}</td>
        </ng-container>
        <ng-container matColumnDef="tenureMonths">
          <th mat-header-cell *matHeaderCellDef>Tenure</th>
          <td mat-cell *matCellDef="let scheme">{{ scheme.tenureMonths }}</td>
        </ng-container>
        <ng-container matColumnDef="actions">
          <th mat-header-cell *matHeaderCellDef>Actions</th>
          <td mat-cell *matCellDef="let scheme">
            <button mat-icon-button (click)="editScheme(scheme)">
              <mat-icon>edit</mat-icon>
            </button>
            <button mat-icon-button (click)="deleteScheme(scheme.id)">
              <mat-icon>delete</mat-icon>
            </button>
          </td>
        </ng-container>
        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
        <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
      </table>
    </div>
  `,
  styles: [
    `
      .container {
        padding: 16px;
      }
      table {
        width: 100%;
      }
      .error-message {
        color: red;
        margin-bottom: 16px;
      }
    `
  ]
})
export class LoanSchemeListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'schemeName', 'interestRate', 'tenureMonths', 'actions'];
  dataSource = new MatTableDataSource<LoanSchemeResponse>([]);
  errorMessage: string | null = null;

  constructor(
    private loanSchemeService: LoanSchemeService,
    private dialog: MatDialog,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadSchemes();
  }

  loadSchemes(): void {
    this.loanSchemeService.getAllLoanSchemes().subscribe({
      next: schemes => {
        this.dataSource.data = schemes.filter(s => !s.isDeleted);
      },
      error: err => {
        console.error('Fetch schemes failed:', err);
        this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load loan schemes.';
      }
    });
  }

  editScheme(scheme: LoanSchemeResponse): void {
    this.dialog
      .open(LoanSchemeFormComponent, {
        width: '400px',
        data: { adminId: this.authService.getUserId(), scheme }
      })
      .afterClosed()
      .subscribe(result => {
        if (result) this.loadSchemes();
      });
  }

  deleteScheme(id: number): void {
    if (confirm('Are you sure you want to delete this scheme?')) {
      const request: LoanSchemeSoftDelete = { adminId: this.authService.getUserId()! };
      this.loanSchemeService.softDeleteLoanScheme(id, request).subscribe({
        next: () => this.loadSchemes(),
        error: err => console.error('Delete scheme failed:', err)
      });
    }
  }
}




// import { Component, OnInit } from '@angular/core';
// import { MatDialog } from '@angular/material/dialog';
// import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
// import { AuthService } from '../../../core/auth/auth.service';
// import { LoanSchemeResponse, LoanSchemeSoftDelete } from '../../models/loan-scheme.model';
// import { LoanSchemeFormComponent } from '../loan-scheme-form/loan-scheme-form.component';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { MatTableDataSource } from '@angular/material/table';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatIconModule } from '@angular/material/icon';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-loan-scheme-list',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatButtonModule,
//     MatIconModule,
//     MatFormFieldModule,
//     MatInputModule,
//     ReactiveFormsModule
//   ],
//   template: `
//     <div class="container">
//       <h2>Loan Schemes</h2>
//       <form [formGroup]="form" (ngSubmit)="addLoanScheme()">
//         <mat-form-field appearance="fill">
//           <mat-label>Scheme Name</mat-label>
//           <input matInput formControlName="schemeName" required />
//           <mat-error *ngIf="form.get('schemeName')?.hasError('required')">Name is required</mat-error>
//         </mat-form-field>
//         <mat-form-field appearance="fill">
//           <mat-label>Interest Rate (%)</mat-label>
//           <input matInput formControlName="interestRate" required type="number" step="0.1" />
//           <mat-error *ngIf="form.get('interestRate')?.hasError('required')">Rate is required</mat-error>
//         </mat-form-field>
//         <mat-form-field appearance="fill">
//           <mat-label>Tenure (Months)</mat-label>
//           <input matInput formControlName="tenureMonths" required type="number" />
//           <mat-error *ngIf="form.get('tenureMonths')?.hasError('required')">Tenure is required</mat-error>
//         </mat-form-field>
//         <mat-form-field appearance="fill">
//           <mat-label>Document Type IDs</mat-label>
//           <input matInput formControlName="requiredDocumentTypeIds" placeholder="e.g., 1,2,3" />
//         </mat-form-field>
//         <button mat-raised-button color="primary" type="submit" [disabled]="form.invalid">Add</button>
//         <div *ngIf="errorMessage" class="error-message">{{ errorMessage }}</div>
//       </form>

//       <table mat-table [dataSource]="dataSource">
//         <ng-container matColumnDef="id">
//           <th mat-header-cell *matHeaderCellDef>ID</th>
//           <td mat-cell *matCellDef="let scheme">{{ scheme.id }}</td>
//         </ng-container>
//         <ng-container matColumnDef="schemeName">
//           <th mat-header-cell *matHeaderCellDef>Name</th>
//           <td mat-cell *matCellDef="let scheme">{{ scheme.schemeName }}</td>
//         </ng-container>
//         <ng-container matColumnDef="interestRate">
//           <th mat-header-cell *matHeaderCellDef>Rate (%)</th>
//           <td mat-cell *matCellDef="let scheme">{{ scheme.interestRate }}</td>
//         </ng-container>
//         <ng-container matColumnDef="tenureMonths">
//           <th mat-header-cell *matHeaderCellDef>Tenure</th>
//           <td mat-cell *matCellDef="let scheme">{{ scheme.tenureMonths }}</td>
//         </ng-container>
//         <ng-container matColumnDef="actions">
//           <th mat-header-cell *matHeaderCellDef>Actions</th>
//           <td mat-cell *matCellDef="let scheme">
//             <button mat-icon-button (click)="editScheme(scheme)">
//               <mat-icon>edit</mat-icon>
//             </button>
//             <button mat-icon-button (click)="deleteScheme(scheme.id)">
//               <mat-icon>delete</mat-icon>
//             </button>
//           </td>
//         </ng-container>
//         <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
//         <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
//       </table>
//     </div>
//   `,
//   styles: [
//     `
//       .container {
//         padding: 16px;
//       }
//       mat-form-field {
//         margin-right: 16px;
//         width: 200px;
//       }
//       button {
//         margin-top: 16px;
//       }
//       table {
//         width: 100%;
//         margin-top: 16px;
//       }
//       .error-message {
//         color: red;
//         margin-top: 8px;
//       }
//     `
//   ]
// })
// export class LoanSchemeListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'schemeName', 'interestRate', 'tenureMonths', 'actions'];
//   dataSource = new MatTableDataSource<LoanSchemeResponse>([]);
//   form: FormGroup;
//   errorMessage: string | null = null;

//   constructor(
//     private loanSchemeService: LoanSchemeService,
//     private dialog: MatDialog,
//     private authService: AuthService,
//     private fb: FormBuilder
//   ) {
//     this.form = this.fb.group({
//       schemeName: ['', Validators.required],
//       interestRate: ['', Validators.required],
//       tenureMonths: ['', Validators.required],
//       requiredDocumentTypeIds: ['']
//     });
//   }

//   ngOnInit(): void {
//     this.loadSchemes();
//   }

//   loadSchemes(): void {
//     this.loanSchemeService.getAllLoanSchemes().subscribe({
//       next: schemes => {
//         console.log('Loan schemes assigned to table:', schemes);
//         this.dataSource.data = schemes.filter(s => !s.isDeleted);
//       },
//       error: err => {
//         console.error('Fetch schemes failed:', err);
//         this.errorMessage = 'Failed to load loan schemes.';
//       }
//     });
//   }

//   addLoanScheme(): void {
//     if (this.form.valid) {
//       this.errorMessage = null;
//       const adminId = this.authService.getUserId();
//       if (!adminId) {
//         this.errorMessage = 'Admin ID not found. Please log in again.';
//         return;
//       }
//       const scheme = {
//         schemeName: this.form.value.schemeName,
//         interestRate: +this.form.value.interestRate,
//         tenureMonths: +this.form.value.tenureMonths,
//         requiredDocumentTypeIds: this.form.value.requiredDocumentTypeIds
//           ? this.form.value.requiredDocumentTypeIds.split(',').map((id: string) => +id)
//           : []
//       };
//       this.loanSchemeService.createLoanScheme(adminId, scheme).subscribe({
//         next: () => {
//           this.form.reset();
//           this.loadSchemes();
//         },
//         error: err => {
//           console.error('Add scheme failed:', err);
//           this.errorMessage = err.status === 401 ? 'Unauthorized. Please log in again.' : 'Failed to add loan scheme.';
//         }
//       });
//     }
//   }

//   editScheme(scheme: LoanSchemeResponse): void {
//     this.dialog
//       .open(LoanSchemeFormComponent, {
//         width: '400px',
//         data: { adminId: this.authService.getUserId(), scheme }
//       })
//       .afterClosed()
//       .subscribe(result => {
//         if (result) {
//           const adminId = this.authService.getUserId();
//           if (!adminId) {
//             this.errorMessage = 'Admin ID not found. Please log in again.';
//             return;
//           }
//           const updateRequest = {
//             interestRate: +result.interestRate,
//             tenureMonths: +result.tenureMonths,
//             requiredDocumentTypeIds: result.requiredDocumentTypeIds
//               ? result.requiredDocumentTypeIds.split(',').map((id: string) => +id)
//               : []
//           };
//           this.loanSchemeService.updateLoanScheme(scheme.id, adminId, updateRequest).subscribe({
//             next: () => this.loadSchemes(),
//             error: err => {
//               console.error('Update scheme failed:', err);
//               this.errorMessage = 'Failed to update loan scheme.';
//             }
//           });
//         }
//       });
//   }

//   deleteScheme(id: number): void {
//     if (confirm('Are you sure you want to delete this scheme?')) {
//       const adminId = this.authService.getUserId();
//       if (!adminId) {
//         this.errorMessage = 'Admin ID not found. Please log in again.';
//         return;
//       }
//       const request: LoanSchemeSoftDelete = { adminId };
//       this.loanSchemeService.softDeleteLoanScheme(id, request).subscribe({
//         next: () => this.loadSchemes(),
//         error: err => {
//           console.error('Delete scheme failed:', err);
//           this.errorMessage = 'Failed to delete loan scheme.';
//         }
//       });
//     }
//   }
// }