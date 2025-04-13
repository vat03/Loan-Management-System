// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-dashboard-card',
//   standalone: false,
//   templateUrl: './dashboard-card.component.html',
//   styleUrl: './dashboard-card.component.scss'
// })
// export class DashboardCardComponent {

// }

// import { Component, Input, Output, EventEmitter } from '@angular/core';

// @Component({
//   selector: 'dashboard-card',
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

@Component({
  selector: 'dashboard-card',
  templateUrl: './dashboard-card.component.html',
  styleUrls: ['./dashboard-card.component.scss']
})
export class DashboardCardComponent {
  @Input() title: string = '';
  @Input() count: number = 0;
  @Output() add = new EventEmitter<void>();
  @Output() view = new EventEmitter<void>();
}