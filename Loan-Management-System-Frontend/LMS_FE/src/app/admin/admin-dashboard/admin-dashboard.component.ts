import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: false,
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {
  stats = { officers: 0, schemes: 0 }; // Mock data

  constructor(private api: ApiService) { }

  ngOnInit() {
    // Mock for now; replace with real API calls
    this.stats = { officers: 5, schemes: 3 };
  }
}
