import { Routes } from '@angular/router';
import { AuthGuard } from '../core/auth/auth.guard';

export const LOAN_OFFICER_ROUTES: Routes = [
    {
        path: '',
        loadComponent: () => import('./loan-officer-dashboard/loan-officer-dashboard.component')
            .then(m => m.LoanOfficerDashboardComponent),
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_LOAN_OFFICER'] }
    },
    {
        path: 'documents',
        loadComponent: () => import('./document-list/document-list.component')
            .then(m => m.DocumentListComponent),
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_LOAN_OFFICER'] }
    },
    {
        path: 'documents/:loanId',
        loadComponent: () => import('./document-verification/document-verification.component')
            .then(m => m.DocumentVerificationComponent),
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_LOAN_OFFICER'] }
    },
    {
        path: 'npa',
        loadComponent: () => import('./npa-management/npa-management.component')
            .then(m => m.NpaManagementComponent),
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_LOAN_OFFICER'] }
    }
];