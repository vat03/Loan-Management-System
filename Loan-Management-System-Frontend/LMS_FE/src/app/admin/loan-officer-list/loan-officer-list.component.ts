import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-loan-officer-list',
  standalone: false,
  templateUrl: './loan-officer-list.component.html',
  styleUrl: './loan-officer-list.component.css'
})
export class LoanOfficerListComponent implements OnInit{
  displayedColumns: string[] = ['id', 'username', 'email'];
  dataSource: any[] = []; // Mock data

  constructor(private api: ApiService) { }

  ngOnInit() {
    // Mock for now; replace with api.get('loan-officers/admin/1')
    this.dataSource = [
      { id: 1, username: 'officer1', email: 'officer1@example.com' },
      { id: 2, username: 'officer2', email: 'officer2@example.com' }
    ];
  }
}
