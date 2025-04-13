// import { NgModule } from '@angular/core';
// import { CommonModule } from '@angular/common';



// @NgModule({
//   declarations: [],
//   imports: [
//     CommonModule
//   ]
// })
// export class CoreModule { }

// import { NgModule, Optional, SkipSelf } from '@angular/core';

// import { AuthService } from './auth/auth.service';
// import { AuthGuard } from './auth/auth.guard';
// import { AuthInterceptor } from './auth/auth.interceptor';
// import { LoanOfficerService } from './services/loan-officer.service';
// import { LoanSchemeService } from './services/loan-scheme.service';
// import { UserService } from './services/user.service';
// import { HTTP_INTERCEPTORS, provideHttpClient } from '@angular/common/http';

// @NgModule({
//   providers: [
//     provideHttpClient(),
//     AuthService,
//     AuthGuard,
//     LoanOfficerService,
//     LoanSchemeService,
//     UserService,
//     {
//       provide: HTTP_INTERCEPTORS,
//       useClass: AuthInterceptor,
//       multi: true
//     }
//   ]
// })
// export class CoreModule {
//   constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
//     if (parentModule) {
//       throw new Error('CoreModule is already loaded.');
//     }
//   }
// }




import { NgModule, Optional, SkipSelf } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { AuthService } from './auth/auth.service';
import { AuthGuard } from './auth/auth.guard';
import { LoanOfficerService } from './services/loan-officer.service';
import { LoanSchemeService } from './services/loan-scheme.service';
import { UserService } from './services/user.service';
import { inject } from '@angular/core';

export function authInterceptor(req: import('@angular/common/http').HttpRequest<any>, next: import('@angular/common/http').HttpHandlerFn) {
  const authService = inject(AuthService);
  const token = authService.getToken();
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }
  return next(req);
}

@NgModule({
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),
    AuthService,
    AuthGuard,
    LoanOfficerService,
    LoanSchemeService,
    UserService
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded.');
    }
  }
}