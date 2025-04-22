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

//   constructor(private authService: AuthService, private router: Router) { }

//   ngOnInit(): void {
//     this.role = this.authService.getRole();
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

//   isCustomer(): boolean {
//     return this.role === 'ROLE_CUSTOMER';
//   }

//   navigateToProfile(): void {
//     this.router.navigate(['/profile']);
//   }

//   logout(): void {
//     this.authService.logout();
//     this.role = null;
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
// import { ProfileResponseDTO } from '../../../admin/models/profile.model';

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
//   greeting: string = 'User';

//   constructor(private authService: AuthService, private router: Router) { }

//   ngOnInit(): void {
//     this.role = this.authService.getRole();
//     this.loadUserProfile();
//   }

//   private loadUserProfile(): void {
//     if (this.authService.isLoggedIn()) {
//       this.authService.getUserProfile().subscribe({
//         next: (profile: ProfileResponseDTO) => {
//           if (profile.firstName && profile.firstName.trim()) {
//             this.greeting = profile.firstName;
//           } else {
//             this.setRoleBasedGreeting();
//           }
//         },
//         error: (err) => {
//           console.error('Error loading profile:', err.message);
//           this.setRoleBasedGreeting();
//         }
//       });
//     } else {
//       this.greeting = 'User';
//     }
//   }

//   private setRoleBasedGreeting(): void {
//     if (this.role) {
//       this.greeting = this.role.replace('ROLE_', '').charAt(0) + this.role.replace('ROLE_', '').slice(1).toLowerCase();
//     } else {
//       this.greeting = 'User';
//     }
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

//   isCustomer(): boolean {
//     return this.role === 'ROLE_CUSTOMER';
//   }

//   navigateToProfile(): void {
//     this.router.navigate(['/profile']);
//   }

//   logout(): void {
//     this.authService.logout();
//     this.role = null;
//     this.greeting = 'User';
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
import { ProfileResponseDTO } from '../../../admin/models/profile.model';

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
  greeting: string = 'User';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.role = this.authService.getRole();
    this.loadUserProfile();
  }

  private loadUserProfile(): void {
    if (this.authService.isLoggedIn()) {
      this.authService.getUserProfile().subscribe({
        next: (profile: ProfileResponseDTO) => {
          if (profile.firstName && profile.firstName.trim()) {
            this.greeting = profile.firstName;
          } else {
            this.setRoleBasedGreeting();
          }
        },
        error: (err) => {
          console.error('Error loading profile:', err.message);
          this.setRoleBasedGreeting();
        }
      });
    } else {
      this.greeting = 'User';
    }
  }

  private setRoleBasedGreeting(): void {
    if (this.role) {
      this.greeting = this.role.replace('ROLE_', '').charAt(0) + this.role.replace('ROLE_', '').slice(1).toLowerCase();
    } else {
      this.greeting = 'User';
    }
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
    this.router.navigate(['/profile']);
  }

  navigateToHome(): void {
    this.router.navigate(['/']);
  }

  logout(): void {
    this.authService.logout();
    this.role = null;
    this.greeting = 'User';
    this.router.navigate(['/login']);
  }
}