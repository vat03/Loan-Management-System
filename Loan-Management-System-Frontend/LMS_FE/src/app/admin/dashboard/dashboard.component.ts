// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-dashboard',
//   standalone: true,
//   imports: [],
//   templateUrl: './dashboard.component.html',
//   styleUrl: './dashboard.component.scss'
// })
// export class DashboardComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { MatDialog } from '@angular/material/dialog';
// import { Router } from '@angular/router';
// import { LoanOfficerService } from '../../core/services/loan-officer.service';
// import { LoanSchemeService } from '../../core/services/loan-scheme.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanOfficerFormComponent } from '../user-management/loan-officer-form/loan-officer-form.component';
// import { LoanSchemeFormComponent } from '../loan-management/loan-scheme-form/loan-scheme-form.component';
// import { DashboardCardComponent } from '../../shared/components/dashboard-card/dashboard-card.component';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-dashboard',
//   standalone: true,
//   imports: [CommonModule, DashboardCardComponent],
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
//   ) { }

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
import { Router } from '@angular/router';
import { LoanOfficerService } from '../../core/services/loan-officer.service';
import { LoanSchemeService } from '../../core/services/loan-scheme.service';
import { AuthService } from '../../core/auth/auth.service';
import { LoanOfficerFormComponent } from '../user-management/loan-officer-form/loan-officer-form.component';
import { LoanSchemeFormComponent } from '../loan-management/loan-scheme-form/loan-scheme-form.component';
import { DashboardCardComponent } from '../../shared/components/dashboard-card/dashboard-card.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, DashboardCardComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  loanOfficerCount = 0;
  loanSchemeCount = 0;
  errorMessage: string | null = null;

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
    this.errorMessage = null;
    this.loanOfficerService.getAllLoanOfficers().subscribe({
      next: officers => {
        this.loanOfficerCount = officers.length;
      },
      error: err => {
        console.error('Fetch loan officers count failed:', err);
        this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load counts.';
      }
    });
    this.loanSchemeService.getAllLoanSchemes().subscribe({
      next: schemes => {
        this.loanSchemeCount = schemes.filter(s => !s.isDeleted).length;
      },
      error: err => {
        console.error('Fetch loan schemes count failed:', err);
        this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load counts.';
      }
    });
  }

  openLoanOfficerForm(): void {
    this.dialog.open(LoanOfficerFormComponent, {
      width: '400px',
      data: { adminId: this.authService.getUserId() }
    }).afterClosed().subscribe(result => {
      if (result) this.loadCounts();
    });
  }

  openLoanSchemeForm(): void {
    this.dialog.open(LoanSchemeFormComponent, {
      width: '400px',
      data: { adminId: this.authService.getUserId() }
    }).afterClosed().subscribe(result => {
      if (result) this.loadCounts();
    });
  }

  viewLoanOfficers(): void {
    this.router.navigate(['/admin/users']);
  }

  viewLoanSchemes(): void {
    this.router.navigate(['/admin/schemes']);
  }
}




// import { Component, OnInit } from '@angular/core';
// import { MatDialog } from '@angular/material/dialog';
// import { Router } from '@angular/router';
// import { LoanOfficerService } from '../../core/services/loan-officer.service';
// import { LoanSchemeService } from '../../core/services/loan-scheme.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { LoanOfficerFormComponent } from '../user-management/loan-officer-form/loan-officer-form.component';
// import { LoanSchemeFormComponent } from '../loan-management/loan-scheme-form/loan-scheme-form.component';
// import { DashboardCardComponent } from '../../shared/components/dashboard-card/dashboard-card.component';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-dashboard',
//   standalone: true,
//   imports: [CommonModule, DashboardCardComponent],
//   template: `
//     <div class="dashboard-container">
//       <app-dashboard-card
//         title="Loan Officers"
//         [count]="loanOfficerCount"
//         buttonText="Add Officer"
//         (buttonClick)="openLoanOfficerForm()"
//         (viewClick)="viewLoanOfficers()"
//       ></app-dashboard-card>
//       <app-dashboard-card
//         title="Loan Schemes"
//         [count]="loanSchemeCount"
//         buttonText="Add Scheme"
//         (buttonClick)="openLoanSchemeForm()"
//         (viewClick)="viewLoanSchemes()"
//       ></app-dashboard-card>
//     </div>
//   `,
//   styles: [
//     `
//       .dashboard-container {
//         display: flex;
//         gap: 16px;
//         padding: 16px;
//       }
//     `
//   ]
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
//   ) { }

//   ngOnInit(): void {
//     this.loadCounts();
//   }

//   loadCounts(): void {
//     this.loanOfficerService.getAllLoanOfficers().subscribe({
//       next: officers => {
//         console.log('Dashboard loan officers count:', officers.length);
//         this.loanOfficerCount = officers.length;
//       },
//       error: err => console.error('Fetch loan officers count failed:', err)
//     });
//     this.loanSchemeService.getAllLoanSchemes().subscribe({
//       next: schemes => {
//         const activeSchemes = schemes.filter(s => !s.isDeleted);
//         console.log('Dashboard loan schemes count:', activeSchemes.length);
//         this.loanSchemeCount = activeSchemes.length;
//       },
//       error: err => console.error('Fetch loan schemes count failed:', err)
//     });
//   }

//   openLoanOfficerForm(): void {
//     this.dialog
//       .open(LoanOfficerFormComponent, {
//         width: '400px',
//         data: { adminId: this.authService.getUserId() }
//       })
//       .afterClosed()
//       .subscribe(result => {
//         if (result) this.loadCounts();
//       });
//   }

//   openLoanSchemeForm(): void {
//     this.dialog
//       .open(LoanSchemeFormComponent, {
//         width: '400px',
//         data: { adminId: this.authService.getUserId() }
//       })
//       .afterClosed()
//       .subscribe(result => {
//         if (result) this.loadCounts();
//       });
//   }

//   viewLoanOfficers(): void {
//     this.router.navigate(['/admin/users']);
//   }

//   viewLoanSchemes(): void {
//     this.router.navigate(['/admin/schemes']);
//   }
// }