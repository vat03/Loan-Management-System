// import { Component, OnInit, ViewChild } from '@angular/core';
// import { MatDialog } from '@angular/material/dialog';
// import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
// import { MatTableDataSource } from '@angular/material/table';
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
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatButtonModule,
//     MatIconModule,
//     MatPaginatorModule
//   ],
//   templateUrl: './loan-scheme-list.component.html',
//   styleUrls: ['./loan-scheme-list.component.scss']
// })
// export class LoanSchemeListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'schemeName', 'interestRate', 'tenureMonths', 'actions'];
//   dataSource = new MatTableDataSource<LoanSchemeResponse>([]);
//   errorMessage: string | null = null;

//   @ViewChild(MatPaginator) paginator!: MatPaginator;

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
//       next: schemes => {
//         this.dataSource.data = schemes.filter(s => !s.isDeleted);
//         this.dataSource.paginator = this.paginator;
//       },
//       error: err => {
//         console.error('Fetch schemes failed:', err);
//         this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load loan schemes.';
//       }
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
//         error: err => console.error('Delete scheme failed:', err)
//       });
//     }
//   }
// }

// import { Component, OnInit, ViewChild } from '@angular/core';
// import { MatDialog } from '@angular/material/dialog';
// import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
// import { MatTableDataSource } from '@angular/material/table';
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
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatButtonModule,
//     MatIconModule,
//     MatPaginatorModule
//   ],
//   templateUrl: './loan-scheme-list.component.html',
//   styleUrls: ['./loan-scheme-list.component.scss']
// })
// export class LoanSchemeListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'schemeName', 'interestRate', 'tenureMonths', 'actions', 'deleted'];
//   dataSource = new MatTableDataSource<LoanSchemeResponse>([]);
//   errorMessage: string | null = null;

//   @ViewChild(MatPaginator) paginator!: MatPaginator;

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
//       next: schemes => {
//         this.dataSource.data = schemes;
//         this.dataSource.paginator = this.paginator;
//       },
//       error: err => {
//         console.error('Fetch schemes failed:', err);
//         this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load loan schemes.';
//       }
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
//         error: err => console.error('Delete scheme failed:', err)
//       });
//     }
//   }
// }


import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
import { AuthService } from '../../../core/auth/auth.service';
import { LoanSchemeResponse, LoanSchemeSoftDelete } from '../../models/loan-scheme.model';
import { LoanSchemeFormComponent } from '../loan-scheme-form/loan-scheme-form.component';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loan-scheme-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule
  ],
  templateUrl: './loan-scheme-list.component.html',
  styleUrls: ['./loan-scheme-list.component.scss']
})
export class LoanSchemeListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'schemeName', 'interestRate', 'tenureMonths', 'actions', 'deleted'];
  dataSource = new MatTableDataSource<LoanSchemeResponse>([]);
  errorMessage: string | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

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
        console.log('Fetched schemes:', schemes); // Log to verify isDeleted
        this.dataSource = new MatTableDataSource<LoanSchemeResponse>(schemes);
        this.dataSource.paginator = this.paginator;
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
        next: () => {
          console.log(`Deleted scheme ID ${id}`);
          this.loadSchemes();
        },
        error: err => console.error('Delete scheme failed:', err)
      });
    }
  }
}