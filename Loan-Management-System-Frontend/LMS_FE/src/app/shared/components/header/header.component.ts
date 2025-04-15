// import { Component } from '@angular/core';
// import { AuthService } from '../../../core/auth/auth.service';
// import { Router, RouterModule } from '@angular/router';
// import { MatToolbarModule } from '@angular/material/toolbar';
// import { MatButtonModule } from '@angular/material/button';
// import { MatIconModule } from '@angular/material/icon';
// import { MatMenuModule } from '@angular/material/menu';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'header-component',
//   standalone: true,
//   imports: [
//     CommonModule,
//     RouterModule,
//     MatToolbarModule,
//     MatButtonModule,
//     MatIconModule,
//     MatMenuModule
//   ],
//   templateUrl: './header.component.html',
//   styleUrls: ['./header.component.scss']
// })
// export class HeaderComponent {
//   role: string | null;

//   constructor(private authService: AuthService, private router: Router) {
//     this.role = this.authService.getRole();
//   }

//   navigateToProfile(): void {
//     this.router.navigate(['/admin/profile']);
//   }

//   logout(): void {
//     this.authService.logout();
//     this.router.navigate(['/login']);
//   }
// }




// import { Component } from '@angular/core';
// import { AuthService } from '../../../core/auth/auth.service';
// import { Router, RouterModule } from '@angular/router';
// import { MatToolbarModule } from '@angular/material/toolbar';
// import { MatButtonModule } from '@angular/material/button';
// import { MatIconModule } from '@angular/material/icon';
// import { MatMenuModule } from '@angular/material/menu';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'header-component',
//   standalone: true,
//   imports: [
//     CommonModule,
//     RouterModule,
//     MatToolbarModule,
//     MatButtonModule,
//     MatIconModule,
//     MatMenuModule
//   ],
//   templateUrl: './header.component.html',
//   styleUrls: ['./header.component.scss']
// })
// export class HeaderComponent {
//   role: string | null;

//   constructor(private authService: AuthService, private router: Router) {
//     this.role = this.authService.getRole();
//   }

//   isLoggedIn(): boolean {
//     return this.authService.isLoggedIn();
//   }

//   isAdmin(): boolean {
//     return this.role === 'ROLE_ADMIN';
//   }

//   navigateToProfile(): void {
//     this.router.navigate(['/admin/profile']);
//   }

//   logout(): void {
//     this.authService.logout();
//     this.router.navigate(['/login']);
//   }
// }



// import { Component, OnInit } from '@angular/core';
// import { AuthService } from '../../../core/auth/auth.service';
// import { Router, RouterModule } from '@angular/router';
// import { MatToolbarModule } from '@angular/material/toolbar';
// import { MatButtonModule } from '@angular/material/button';
// import { MatIconModule } from '@angular/material/icon';
// import { MatMenuModule } from '@angular/material/menu';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'header-component',
//   standalone: true,
//   imports: [
//     CommonModule,
//     RouterModule,
//     MatToolbarModule,
//     MatButtonModule,
//     MatIconModule,
//     MatMenuModule
//   ],
//   templateUrl: './header.component.html',
//   styleUrls: ['./header.component.scss']
// })
// export class HeaderComponent implements OnInit {
//   role: string | null = null;

//   constructor(private authService: AuthService, private router: Router) {}

//   ngOnInit(): void {
//     this.role = this.authService.getRole();
//     // Optional: Subscribe to role changes if AuthService emits updates
//   }

//   isLoggedIn(): boolean {
//     return this.authService.isLoggedIn();
//   }

//   isAdmin(): boolean {
//     return this.role === 'ROLE_ADMIN';
//   }

//   isLoanOfficer(): boolean {
//     return this.role === 'ROLE_LOAN_OFFICER';
//   }

//   navigateToProfile(): void {
//     if (this.isAdmin()) {
//       this.router.navigate(['/admin/profile']);
//     } else if (this.isLoanOfficer()) {
//       this.router.navigate(['/loan-officer/profile']);
//     } else {
//       this.router.navigate(['/profile']);
//     }
//   }

//   logout(): void {
//     this.authService.logout();
//     this.role = null;
//     this.router.navigate(['/login']);
//   }
// }


import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../core/auth/auth.service';
import { Router, RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'header-component',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule
  ],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  role: string | null = null;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.role = this.authService.getRole();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  isAdmin(): boolean {
    return this.role === 'ROLE_ADMIN';
  }

  isLoanOfficer(): boolean {
    return this.role === 'ROLE_LOAN_OFFICER';
  }

  isCustomer(): boolean {
    return this.role === 'ROLE_CUSTOMER';
  }

  navigateToProfile(): void {
    if (this.isAdmin()) {
      this.router.navigate(['/admin/profile']);
    } else if (this.isLoanOfficer()) {
      this.router.navigate(['/loan-officer/profile']);
    } else if (this.isCustomer()) {
      this.router.navigate(['/customer/profile']);
    } else {
      this.router.navigate(['/profile']);
    }
  }

  logout(): void {
    this.authService.logout();
    this.role = null;
    this.router.navigate(['/login']);
  }
}