// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-dashboard-card',
//   standalone: true,
//   imports: [],
//   templateUrl: './dashboard-card.component.html',
//   styleUrl: './dashboard-card.component.scss'
// })
// export class DashboardCardComponent {

// }


// import { Component, Input, Output, EventEmitter } from '@angular/core';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'dashboard-card',
//   standalone: true,
//   imports: [CommonModule, MatCardModule, MatButtonModule],
//   templateUrl: './dashboard-card.component.html',
//   styleUrls: ['./dashboard-card.component.scss']
// })
// export class DashboardCardComponent {
//   @Input() title: string = '';
//   @Input() count: number = 0;
//   @Output() add = new EventEmitter<void>();
//   @Output() view = new EventEmitter<void>();
// }


import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard-card', // Changed from 'dashboard-card'
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule],
  templateUrl: './dashboard-card.component.html',
  styleUrls: ['./dashboard-card.component.scss']
})
export class DashboardCardComponent {
  @Input() title: string = '';
  @Input() count: number = 0;
  @Output() add = new EventEmitter<void>();
  @Output() view = new EventEmitter<void>();
}