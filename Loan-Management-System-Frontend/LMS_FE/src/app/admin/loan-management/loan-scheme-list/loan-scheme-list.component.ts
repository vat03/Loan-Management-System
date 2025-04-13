// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-loan-scheme-list',
//   standalone: false,
//   templateUrl: './loan-scheme-list.component.html',
//   styleUrl: './loan-scheme-list.component.scss'
// })
// export class LoanSchemeListComponent {

// }

// import { Component, OnInit } from '@angular/core';
// import { MatDialog } from '@angular/material/dialog';
// import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
// import { LoanSchemeResponse, LoanSchemeSoftDelete } from '../../models/loan-scheme.model';
// import { LoanSchemeFormComponent } from '../loan-scheme-form/loan-scheme-form.component';
// import { AuthService } from '../../../core/auth/auth.service';

// @Component({
//   selector: 'app-loan-scheme-list',
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


// import { Component, OnInit } from '@angular/core';
// import { MatDialog } from '@angular/material/dialog';
// import { LoanSchemeService } from '../../../core/services/loan-scheme.service';
// import { LoanSchemeResponse, LoanSchemeSoftDelete } from '../../models/loan-scheme.model';
// import { LoanSchemeFormComponent } from '../loan-scheme-form/loan-scheme-form.component';
// import { AuthService } from '../../../core/auth/auth.service';

// @Component({
//   selector: 'app-loan-scheme-list',
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
import { LoanSchemeResponse, LoanSchemeSoftDelete } from '../../models/loan-scheme.model';
import { LoanSchemeFormComponent } from '../loan-scheme-form/loan-scheme-form.component';
import { AuthService } from '../../../core/auth/auth.service';

@Component({
  selector: 'app-loan-scheme-list',
  templateUrl: './loan-scheme-list.component.html',
  styleUrls: ['./loan-scheme-list.component.scss']
})
export class LoanSchemeListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'schemeName', 'interestRate', 'tenureMonths', 'actions'];
  dataSource: LoanSchemeResponse[] = [];

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
      next: schemes => (this.dataSource = schemes),
      error: err => console.error('Fetch schemes failed', err)
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
        error: err => console.error('Delete scheme failed', err)
      });
    }
  }
}