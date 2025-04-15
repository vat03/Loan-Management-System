// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-customer-dashboard',
//   standalone: true,
//   imports: [],
//   templateUrl: './customer-dashboard.component.html',
//   styleUrl: './customer-dashboard.component.scss'
// })
// export class CustomerDashboardComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { CustomerService } from '../services/customer.service';
// import { Profile } from '../models/customer.model';
// import { CommonModule } from '@angular/common';
// import { RouterModule } from '@angular/router';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';

// @Component({
//   selector: 'app-customer-dashboard',
//   standalone: true,
//   imports: [CommonModule, RouterModule, MatCardModule, MatButtonModule],
//   templateUrl: './customer-dashboard.component.html',
//   styleUrls: ['./customer-dashboard.component.scss']
// })
// export class CustomerDashboardComponent implements OnInit {
//   customerId = 1; // TODO: Replace with AuthService.getCurrentUserId()
//   profile: Profile | null = null;
//   error: string | null = null;

//   constructor(private customerService: CustomerService) { }

//   ngOnInit(): void {
//     this.customerService.getProfile(this.customerId).subscribe({
//       next: (profile) => this.profile = profile,
//       error: (err) => this.error = err.message
//     });
//   }
// }

import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../core/auth/auth.service';
import { Profile } from '../models/customer.model';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, MatCardModule, MatButtonModule],
  templateUrl: './customer-dashboard.component.html',
  styleUrls: ['./customer-dashboard.component.scss']
})
export class CustomerDashboardComponent implements OnInit {
  customerId: number | null;
  profile: Profile | null = null;
  error: string | null = null;

  constructor(
    private customerService: CustomerService,
    private authService: AuthService,
    private router: Router
  ) {
    this.customerId = this.authService.getCustomerId();
  }

  ngOnInit(): void {
    if (!this.customerId) {
      this.error = 'Please log in to view your dashboard.';
      this.router.navigate(['/login']);
      return;
    }

    this.customerService.getProfile(this.customerId).subscribe({
      next: (profile) => this.profile = profile,
      error: (err) => {
        this.error = err.message;
        this.router.navigate(['/login']);
      }
    });
  }
}