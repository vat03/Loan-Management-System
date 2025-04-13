// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-header',
//   standalone: false,
//   templateUrl: './header.component.html',
//   styleUrl: './header.component.scss'
// })
// export class HeaderComponent {

// }


// import { Component } from '@angular/core';
// import { AuthService } from '../../../core/auth/auth.service';
// import { Router } from '@angular/router';

// @Component({
//   selector: 'header-component',
//   templateUrl: './header.component.html',
//   styleUrls: ['./header.component.scss']
// })
// export class HeaderComponent {
//   role: string | null;

//   constructor(private authService: AuthService, private router: Router) {
//     this.role = this.authService.getRole();
//   }

//   navigateToProfile(): void {
//     const userId = this.authService.getUserId();
//     if (userId) {
//       this.router.navigate([`/admin/profile/${userId}`]);
//     }
//   }

//   logout(): void {
//     this.authService.logout();
//     this.router.navigate(['/login']);
//   }
// }








import { Component } from '@angular/core';
import { AuthService } from '../../../core/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'header-component',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  role: string | null;

  constructor(private authService: AuthService, private router: Router) {
    this.role = this.authService.getRole();
  }

  navigateToProfile(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.router.navigate([`/admin/profile/${userId}`]);
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}