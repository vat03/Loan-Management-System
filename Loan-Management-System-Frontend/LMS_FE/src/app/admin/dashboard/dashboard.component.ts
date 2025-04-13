// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-dashboard',
//   standalone: false,
//   templateUrl: './dashboard.component.html',
//   styleUrl: './dashboard.component.scss'
// })
// export class DashboardComponent {

// }

// import { Component, OnInit } from '@angular/core';
// import { MatDialog } from '@angular/material/dialog';
// import { LoanOfficerService } from '../../core/services/loan-officer.service';
// import { LoanSchemeService } from '../../core/services/loan-scheme.service';
// import { LoanOfficerFormComponent } from '../user-management/loan-officer-form/loan-officer-form.component';
// import { LoanSchemeFormComponent } from '../loan-management/loan-scheme-form/loan-scheme-form.component';
// import { Router } from '@angular/router';
// import { AuthService } from '../../core/auth/auth.service';

// @Component({
//   selector: 'app-dashboard',
//   templateUrl: './dashboard.component.html',
//   styleUrls: ['./dashboard.component.scss']
// })
// export class DashboardComponent implements OnInit {
//   loanOfficerCount = 0;
//   loanSchemeCount = 0;

//   constructor(
//     private dialog: MatDialog,
//     private loanOfficerService: LoanOfficerService,
//     private loanSchemeService: LoanSchemeService,
//     private router: Router,
//     private authService: AuthService
//   ) {}

//   ngOnInit(): void {
//     this.loadCounts();
//   }

//   loadCounts(): void {
//     this.loanOfficerService.getAllLoanOfficers().subscribe(officers => {
//       this.loanOfficerCount = officers.length;
//     });
//     this.loanSchemeService.getAllLoanSchemes().subscribe(schemes => {
//       this.loanSchemeCount = schemes.filter(s => !s.isDeleted).length;
//     });
//   }

//   openLoanOfficerForm(): void {
//     this.dialog.open(LoanOfficerFormComponent, {
//       width: '400px',
//       data: { adminId: this.authService.getUserId() }
//     });
//   }

//   openLoanSchemeForm(): void {
//     this.dialog.open(LoanSchemeFormComponent, {
//       width: '400px',
//       data: { adminId: this.authService.getUserId() }
//     });
//   }

//   viewLoanOfficers(): void {
//     this.router.navigate(['/admin/users']);
//   }

//   viewLoanSchemes(): void {
//     this.router.navigate(['/admin/schemes']);
//   }
// }


import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LoanOfficerService } from '../../core/services/loan-officer.service';
import { LoanSchemeService } from '../../core/services/loan-scheme.service';
import { LoanOfficerFormComponent } from '../user-management/loan-officer-form/loan-officer-form.component';
import { LoanSchemeFormComponent } from '../loan-management/loan-scheme-form/loan-scheme-form.component';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  loanOfficerCount = 0;
  loanSchemeCount = 0;

  constructor(
    private dialog: MatDialog,
    private loanOfficerService: LoanOfficerService,
    private loanSchemeService: LoanSchemeService,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadCounts();
  }

  loadCounts(): void {
    this.loanOfficerService.getAllLoanOfficers().subscribe(officers => {
      this.loanOfficerCount = officers.length;
    });
    this.loanSchemeService.getAllLoanSchemes().subscribe(schemes => {
      this.loanSchemeCount = schemes.filter(s => !s.isDeleted).length;
    });
  }

  openLoanOfficerForm(): void {
    this.dialog.open(LoanOfficerFormComponent, {
      width: '400px',
      data: { adminId: this.authService.getUserId() }
    });
  }

  openLoanSchemeForm(): void {
    this.dialog.open(LoanSchemeFormComponent, {
      width: '400px',
      data: { adminId: this.authService.getUserId() }
    });
  }

  viewLoanOfficers(): void {
    this.router.navigate(['/admin/users']);
  }

  viewLoanSchemes(): void {
    this.router.navigate(['/admin/schemes']);
  }
}