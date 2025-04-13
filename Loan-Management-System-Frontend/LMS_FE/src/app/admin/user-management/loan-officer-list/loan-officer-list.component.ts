// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-loan-officer-list',
//   standalone: true,
//   imports: [],
//   templateUrl: './loan-officer-list.component.html',
//   styleUrl: './loan-officer-list.component.scss'
// })
// export class LoanOfficerListComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { LoanOfficerService } from '../../../core/services/loan-officer.service';
// import { LoanOfficerResponse } from '../../models/loan-officer.model';
// import { MatTableModule } from '@angular/material/table';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-loan-officer-list',
//   standalone: true,
//   imports: [CommonModule, MatTableModule],
//   templateUrl: './loan-officer-list.component.html',
//   styleUrls: ['./loan-officer-list.component.scss']
// })
// export class LoanOfficerListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'username', 'email', 'customerCount'];
//   dataSource: LoanOfficerResponse[] = [];

//   constructor(private loanOfficerService: LoanOfficerService) { }

//   ngOnInit(): void {
//     this.loanOfficerService.getAllLoanOfficers().subscribe({
//       next: officers => (this.dataSource = officers),
//       error: err => console.error('Fetch officers failed', err)
//     });
//   }
// }




import { Component, OnInit } from '@angular/core';
import { LoanOfficerService } from '../../../core/services/loan-officer.service';
import { LoanOfficerResponse } from '../../models/loan-officer.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loan-officer-list',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  template: `
    <div class="container">
      <h2>Loan Officers</h2>
      <div *ngIf="errorMessage" class="error-message">{{ errorMessage }}</div>
      <table mat-table [dataSource]="dataSource">
        <ng-container matColumnDef="id">
          <th mat-header-cell *matHeaderCellDef>ID</th>
          <td mat-cell *matCellDef="let officer">{{ officer.id }}</td>
        </ng-container>
        <ng-container matColumnDef="username">
          <th mat-header-cell *matHeaderCellDef>Username</th>
          <td mat-cell *matCellDef="let officer">{{ officer.username }}</td>
        </ng-container>
        <ng-container matColumnDef="email">
          <th mat-header-cell *matHeaderCellDef>Email</th>
          <td mat-cell *matCellDef="let officer">{{ officer.email }}</td>
        </ng-container>
        <ng-container matColumnDef="customerCount">
          <th mat-header-cell *matHeaderCellDef>Customers</th>
          <td mat-cell *matCellDef="let officer">{{ officer.customerIds.length }}</td>
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
export class LoanOfficerListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'username', 'email', 'customerCount'];
  dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
  errorMessage: string | null = null;

  constructor(private loanOfficerService: LoanOfficerService) { }

  ngOnInit(): void {
    this.loanOfficerService.getAllLoanOfficers().subscribe({
      next: officers => {
        this.dataSource.data = officers;
      },
      error: err => {
        console.error('Fetch officers failed:', err);
        this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load loan officers.';
      }
    });
  }
}




// import { Component, OnInit } from '@angular/core';
// import { LoanOfficerService } from '../../../core/services/loan-officer.service';
// import { AuthService } from '../../../core/auth/auth.service';
// import { LoanOfficerResponse } from '../../models/loan-officer.model';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { MatTableDataSource, MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-loan-officer-list',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatButtonModule,
//     MatFormFieldModule,
//     MatInputModule,
//     ReactiveFormsModule
//   ],
//   template: `
//     <div class="container">
//       <h2>Loan Officers</h2>
//       <form [formGroup]="form" (ngSubmit)="addLoanOfficer()">
//         <mat-form-field appearance="fill">
//           <mat-label>Username</mat-label>
//           <input matInput formControlName="username" required />
//           <mat-error *ngIf="form.get('username')?.hasError('required')">Username is required</mat-error>
//         </mat-form-field>
//         <mat-form-field appearance="fill">
//           <mat-label>Email</mat-label>
//           <input matInput formControlName="email" required type="email" />
//           <mat-error *ngIf="form.get('email')?.hasError('required')">Email is required</mat-error>
//           <mat-error *ngIf="form.get('email')?.hasError('email')">Invalid email</mat-error>
//         </mat-form-field>
//         <mat-form-field appearance="fill">
//           <mat-label>Password</mat-label>
//           <input matInput formControlName="password" required type="password" />
//           <mat-error *ngIf="form.get('password')?.hasError('required')">Password is required</mat-error>
//         </mat-form-field>
//         <button mat-raised-button color="primary" type="submit" [disabled]="form.invalid">Add</button>
//         <div *ngIf="errorMessage" class="error-message">{{ errorMessage }}</div>
//       </form>

//       <table mat-table [dataSource]="dataSource">
//         <ng-container matColumnDef="id">
//           <th mat-header-cell *matHeaderCellDef>ID</th>
//           <td mat-cell *matCellDef="let officer">{{ officer.id }}</td>
//         </ng-container>
//         <ng-container matColumnDef="username">
//           <th mat-header-cell *matHeaderCellDef>Username</th>
//           <td mat-cell *matCellDef="let officer">{{ officer.username }}</td>
//         </ng-container>
//         <ng-container matColumnDef="email">
//           <th mat-header-cell *matHeaderCellDef>Email</th>
//           <td mat-cell *matCellDef="let officer">{{ officer.email }}</td>
//         </ng-container>
//         <ng-container matColumnDef="customerCount">
//           <th mat-header-cell *matHeaderCellDef>Customers</th>
//           <td mat-cell *matCellDef="let officer">{{ officer.customerIds.length }}</td>
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
// export class LoanOfficerListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'username', 'email', 'customerCount'];
//   dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
//   form: FormGroup;
//   errorMessage: string | null = null;

//   constructor(
//     private loanOfficerService: LoanOfficerService,
//     private authService: AuthService,
//     private fb: FormBuilder
//   ) {
//     this.form = this.fb.group({
//       username: ['', Validators.required],
//       email: ['', [Validators.required, Validators.email]],
//       password: ['', Validators.required]
//     });
//   }

//   ngOnInit(): void {
//     this.loadOfficers();
//   }

//   loadOfficers(): void {
//     this.loanOfficerService.getAllLoanOfficers().subscribe({
//       next: officers => {
//         console.log('Loan officers assigned to table:', officers);
//         this.dataSource.data = officers;
//       },
//       error: err => {
//         console.error('Fetch officers failed:', err);
//         this.errorMessage = 'Failed to load loan officers.';
//       }
//     });
//   }

//   addLoanOfficer(): void {
//     if (this.form.valid) {
//       this.errorMessage = null;
//       const adminId = this.authService.getUserId();
//       if (!adminId) {
//         this.errorMessage = 'Admin ID not found. Please log in again.';
//         return;
//       }
//       this.loanOfficerService.addLoanOfficer(adminId, this.form.value).subscribe({
//         next: () => {
//           this.form.reset();
//           this.loadOfficers();
//         },
//         error: err => {
//           console.error('Add officer failed:', err);
//           this.errorMessage = err.status === 401 ? 'Unauthorized. Please log in again.' : 'Failed to add loan officer.';
//         }
//       });
//     }
//   }
// }