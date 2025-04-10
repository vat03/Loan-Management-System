import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: false,
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {
  stats = { officers: 0, schemes: 0 };

  constructor(private api: ApiService) { }

  ngOnInit() {
    this.loadStats();
  }

  loadStats() {
    this.api.get<any>('admins/1/stats').subscribe({
      next: (data) => this.stats = data,
      error: () => {
        // Fallback to mock data
        this.stats = { officers: 5, schemes: 3 };
      }
    });
  }
}
