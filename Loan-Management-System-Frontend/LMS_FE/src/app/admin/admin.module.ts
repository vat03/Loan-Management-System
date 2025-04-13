// import { NgModule } from '@angular/core';
// import { CommonModule } from '@angular/common';

// import { AdminRoutingModule } from './admin-routing.module';
// import { DashboardComponent } from './dashboard/dashboard.component';
// import { LoanOfficerFormComponent } from './user-management/loan-officer-form/loan-officer-form.component';
// import { LoanOfficerListComponent } from './user-management/loan-officer-list/loan-officer-list.component';
// import { LoanSchemeFormComponent } from './loan-management/loan-scheme-form/loan-scheme-form.component';
// import { LoanSchemeListComponent } from './loan-management/loan-scheme-list/loan-scheme-list.component';


// @NgModule({
//   declarations: [
//     DashboardComponent,
//     LoanOfficerFormComponent,
//     LoanOfficerListComponent,
//     LoanSchemeFormComponent,
//     LoanSchemeListComponent
//   ],
//   imports: [
//     CommonModule,
//     AdminRoutingModule
//   ]
// })
// export class AdminModule { }




// import { NgModule } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { AdminRoutingModule } from './admin-routing.module';
// import { DashboardComponent } from './dashboard/dashboard.component';
// import { LoanOfficerFormComponent } from './user-management/loan-officer-form/loan-officer-form.component';
// import { LoanOfficerListComponent } from './user-management/loan-officer-list/loan-officer-list.component';
// import { LoanSchemeFormComponent } from './loan-management/loan-scheme-form/loan-scheme-form.component';
// import { LoanSchemeListComponent } from './loan-management/loan-scheme-list/loan-scheme-list.component';
// import { SharedModule } from '../shared/shared.module';
// import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { MatDialogModule } from '@angular/material/dialog';

// @NgModule({
//   declarations: [
//     DashboardComponent,
//     LoanOfficerFormComponent,
//     LoanOfficerListComponent,
//     LoanSchemeFormComponent,
//     LoanSchemeListComponent
//   ],
//   imports: [
//     CommonModule,
//     AdminRoutingModule,
//     SharedModule,
//     FormsModule,
//     ReactiveFormsModule,
//     MatDialogModule
//   ]
// })
// export class AdminModule { }










import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoanOfficerFormComponent } from './user-management/loan-officer-form/loan-officer-form.component';
import { LoanOfficerListComponent } from './user-management/loan-officer-list/loan-officer-list.component';
import { LoanSchemeFormComponent } from './loan-management/loan-scheme-form/loan-scheme-form.component';
import { LoanSchemeListComponent } from './loan-management/loan-scheme-list/loan-scheme-list.component';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    DashboardComponent,
    LoanOfficerFormComponent,
    LoanOfficerListComponent,
    LoanSchemeFormComponent,
    LoanSchemeListComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule
  ]
})
export class AdminModule { }