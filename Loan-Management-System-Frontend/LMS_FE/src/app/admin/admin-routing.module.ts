import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { LoanOfficerListComponent } from './loan-officer-list/loan-officer-list.component';
import { LoanSchemeListComponent } from './loan-scheme-list/loan-scheme-list.component';

const routes: Routes = [
  { path: 'dashboard', component: AdminDashboardComponent },
  { path: 'loan-officers', component: LoanOfficerListComponent },
  { path: 'loan-schemes', component: LoanSchemeListComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }