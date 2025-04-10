// import { NgModule } from '@angular/core';
// import { CommonModule } from '@angular/common';



// @NgModule({
//   declarations: [],
//   imports: [
//     CommonModule
//   ]
// })
// export class AdminModule { }


import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { SharedModule } from '../shared/shared.module';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { LoanOfficerListComponent } from './loan-officer-list/loan-officer-list.component';
import { LoanSchemeListComponent } from './loan-scheme-list/loan-scheme-list.component';
import { LoanOfficerFormComponent } from './loan-officer-form/loan-officer-form.component';
import { LoanSchemeFormComponent } from './loan-scheme-form/loan-scheme-form.component';
import { MatDialogModule } from '@angular/material/dialog'; 
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [
    AdminDashboardComponent,
    LoanOfficerListComponent,
    LoanSchemeListComponent,
    LoanOfficerFormComponent,
    LoanSchemeFormComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule,
    MatCardModule,
    MatTableModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    FormsModule,
    MatSelectModule,
    ReactiveFormsModule
  ]
})
export class AdminModule { }