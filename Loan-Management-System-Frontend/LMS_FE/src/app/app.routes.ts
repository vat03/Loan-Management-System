import { Routes } from '@angular/router';
import { AuthGuard } from '././core/auth/auth.guard';

export const routes: Routes = [
    {
        path: 'admin',
        loadChildren: () => import('./admin/admin-routes').then(m => m.ADMIN_ROUTES),
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_ADMIN'] }
      },
      {
        path: 'login',
        loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent)
      },
      { path: '', redirectTo: '/login', pathMatch: 'full' },
      { path: '**', redirectTo: '/login' }
];
