// import { NgModule } from '@angular/core';
// import { RouterModule, Routes } from '@angular/router';
// import { CustomerDashboardComponent } from './customer-dashboard/customer-dashboard.component';
// import { ApplyLoanComponent } from './apply-loan/apply-loan.component';
// import { ViewLoansComponent } from './view-loans/view-loans.component';
// import { MakePaymentComponent } from './make-payment/make-payment.component';
// import { PaymentDetailsComponent } from './payment-details/payment-details.component';

// const routes: Routes = [
//     { path: 'dashboard', component: CustomerDashboardComponent },
//     { path: 'apply-loan', component: ApplyLoanComponent },
//     { path: 'view-loans', component: ViewLoansComponent },
//     { path: 'make-payment', component: MakePaymentComponent },
//     { path: 'payment-details', component: PaymentDetailsComponent },
//     { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
// ];

// @NgModule({
//     imports: [RouterModule.forChild(routes)],
//     exports: [RouterModule]
// })
// export class CustomerRoutingModule { }


// import { NgModule } from '@angular/core';
// import { RouterModule, Routes } from '@angular/router';
// import { CustomerDashboardComponent } from './customer-dashboard/customer-dashboard.component';
// import { ApplyLoanComponent } from './apply-loan/apply-loan.component';
// import { ViewLoansComponent } from './view-loans/view-loans.component';
// import { MakePaymentComponent } from './make-payment/make-payment.component';
// import { PaymentDetailsComponent } from './payment-details/payment-details.component';

// const routes: Routes = [
//     { path: 'dashboard', component: CustomerDashboardComponent },
//     { path: 'apply-loan', component: ApplyLoanComponent },
//     { path: 'view-loans', component: ViewLoansComponent },
//     { path: 'make-payment', component: MakePaymentComponent },
//     { path: 'payment-details', component: PaymentDetailsComponent },
//     { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
// ];

// @NgModule({
//     imports: [RouterModule.forChild(routes)],
//     exports: [RouterModule]
// })
// export class CustomerRoutingModule { }



import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerDashboardComponent } from './customer-dashboard/customer-dashboard.component';
import { ApplyLoanComponent } from './apply-loan/apply-loan.component';
import { ViewLoansComponent } from './view-loans/view-loans.component';
import { MakePaymentComponent } from './make-payment/make-payment.component';
import { PaymentDetailsComponent } from './payment-details/payment-details.component';
import { AuthGuard } from '../core/auth/auth.guard';

const routes: Routes = [
    {
        path: 'dashboard',
        component: CustomerDashboardComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_CUSTOMER'] }
    },
    {
        path: 'apply-loan',
        component: ApplyLoanComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_CUSTOMER'] }
    },
    {
        path: 'view-loans',
        component: ViewLoansComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_CUSTOMER'] }
    },
    {
        path: 'make-payment',
        component: MakePaymentComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_CUSTOMER'] }
    },
    {
        path: 'payment-details',
        component: PaymentDetailsComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_CUSTOMER'] }
    },
    { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CustomerRoutingModule { }