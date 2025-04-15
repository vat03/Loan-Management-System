// import { Routes } from '@angular/router';
// import { AuthGuard } from './core/auth/auth.guard';
// import { HomeComponent } from './home/home.component';
// import { RegisterComponent } from './auth/register/register.component';

// export const routes: Routes = [
//   { path: '', component: HomeComponent },
//   {
//     path: 'admin',
//     loadChildren: () => import('./admin/admin-routes').then(m => m.ADMIN_ROUTES),
//     canActivate: [AuthGuard],
//     data: { roles: ['ROLE_ADMIN'] }
//   },
//   {
//     path: 'login',
//     loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent)
//   },
//   { path: 'register', component: RegisterComponent },
//   { path: '**', redirectTo: '' }
// ];

// import { Routes } from '@angular/router';
// import { AuthGuard } from './core/auth/auth.guard';
// import { HomeComponent } from './home/home.component';
// import { RegisterComponent } from './auth/register/register.component';

// export const routes: Routes = [
//   { path: '', component: HomeComponent },
//   {
//     path: 'admin',
//     loadChildren: () => import('./admin/admin-routes').then(m => m.ADMIN_ROUTES),
//     canActivate: [AuthGuard],
//     data: { roles: ['ROLE_ADMIN'] }
//   },
//   {
//     path: 'loan-officer',
//     loadChildren: () => import('./loan-officer/loan-officer-routes').then(m => m.LOAN_OFFICER_ROUTES),
//     canActivate: [AuthGuard],
//     data: { roles: ['ROLE_LOAN_OFFICER'] }
//   },
//   {
//     path: 'login',
//     loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent)
//   },
//   { path: 'register', component: RegisterComponent },
//   { path: '**', redirectTo: '' }
// ];


import { Routes } from '@angular/router';
import { AuthGuard } from './core/auth/auth.guard';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './auth/register/register.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin-routes').then(m => m.ADMIN_ROUTES),
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_ADMIN'] }
  },
  {
    path: 'loan-officer',
    loadChildren: () => import('./loan-officer/loan-officer-routes').then(m => m.LOAN_OFFICER_ROUTES),
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_LOAN_OFFICER'] }
  },
  {
    path: 'customer',
    loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_CUSTOMER'] }
  },
  {
    path: 'login',
    loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent)
  },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: '' }
];