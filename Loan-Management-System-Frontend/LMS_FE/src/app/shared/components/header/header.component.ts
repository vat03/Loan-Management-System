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




import { Component } from '@angular/core';
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
export class HeaderComponent {
  role: string | null;

  constructor(private authService: AuthService, private router: Router) {
    this.role = this.authService.getRole();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  isAdmin(): boolean {
    return this.role === 'ROLE_ADMIN';
  }

  navigateToProfile(): void {
    this.router.navigate(['/admin/profile']);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}