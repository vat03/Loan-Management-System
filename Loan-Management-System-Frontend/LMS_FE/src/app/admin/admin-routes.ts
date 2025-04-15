// import { Routes } from '@angular/router';
// import { DashboardComponent } from './dashboard/dashboard.component';
// import { LoanOfficerListComponent } from './user-management/loan-officer-list/loan-officer-list.component';
// import { LoanSchemeListComponent } from './loan-management/loan-scheme-list/loan-scheme-list.component';

// export const ADMIN_ROUTES: Routes = [
//     { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
//     { path: 'dashboard', component: DashboardComponent },
//     { path: 'users', component: LoanOfficerListComponent },
//     { path: 'schemes', component: LoanSchemeListComponent }
// ];

import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoanOfficerListComponent } from './user-management/loan-officer-list/loan-officer-list.component';
import { LoanSchemeListComponent } from './loan-management/loan-scheme-list/loan-scheme-list.component';
import { ProfileComponent } from '../shared/components/profile/profile.component';

export const ADMIN_ROUTES: Routes = [
    { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'users', component: LoanOfficerListComponent },
    { path: 'schemes', component: LoanSchemeListComponent },
    { path: 'profile', component: ProfileComponent }
];