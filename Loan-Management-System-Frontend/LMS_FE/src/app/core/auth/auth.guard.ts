import { Injectable } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { inject } from '@angular/core';

export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (!authService.isLoggedIn()) {
        router.navigate(['/login']);
        return false;
    }

    const requiredRoles = route.data['roles'] as string[];
    const userRole = authService.getRole();

    if (requiredRoles && userRole && requiredRoles.includes(userRole)) {
        return true;
    }

    router.navigate(['/login']);
    return false;
};