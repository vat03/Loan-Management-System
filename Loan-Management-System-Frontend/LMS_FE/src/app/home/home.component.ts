// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-home',
//   standalone: true,
//   imports: [],
//   templateUrl: './home.component.html',
//   styleUrl: './home.component.scss'
// })
// export class HomeComponent {

// }


import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    RouterModule
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  features = [
    { title: 'Easy Loan Management', description: 'Manage loans with a user-friendly dashboard.', icon: 'dashboard' },
    { title: 'Secure Platform', description: 'Your data is protected with top-notch security.', icon: 'security' },
    { title: 'Quick Approvals', description: 'Get loans approved faster with our streamlined process.', icon: 'speed' },
    { title: '24/7 Support', description: 'Our team is here to help anytime.', icon: 'support' }
  ];

  loans = [
    { title: 'Personal Loan', description: 'Flexible terms for your needs.' },
    { title: 'Home Loan', description: 'Build your dream home.' },
    { title: 'Business Loan', description: 'Grow your business.' },
    { title: 'Education Loan', description: 'Invest in your future.' }
  ];
}