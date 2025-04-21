import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from './auth.service';

export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    const isLoggedIn = authService.isLoggedIn();
    const userRole = authService.getRole();
    const requiredRoles = route.data['roles'] as string[];

    console.log('AuthGuard - Route:', route.url, 'LoggedIn:', isLoggedIn, 'UserRole:', userRole, 'Required:', requiredRoles);

    if (!isLoggedIn) {
        console.log('AuthGuard - Not logged in, redirecting to /login');
        router.navigate(['/login']);
        return false;
    }

    if (requiredRoles && userRole && requiredRoles.includes(userRole)) {
        console.log('AuthGuard - Access granted');
        return true;
    }

    console.log('AuthGuard - Role mismatch, redirecting to /login');
    router.navigate(['/login']);
    return false;
};