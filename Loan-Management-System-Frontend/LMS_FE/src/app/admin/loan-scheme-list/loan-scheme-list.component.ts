import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-loan-scheme-list',
  standalone: false,
  templateUrl: './loan-scheme-list.component.html',
  styleUrl: './loan-scheme-list.component.css'
})
export class LoanSchemeListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'schemeName', 'interestRate'];
  dataSource: any[] = []; // Mock data

  constructor(private api: ApiService) { }

  ngOnInit() {
    // Mock for now; replace with api.get('loan-schemes/admin/1')
    this.dataSource = [
      { id: 1, schemeName: 'Home Loan', interestRate: 5.5 },
      { id: 2, schemeName: 'Car Loan', interestRate: 6.0 }
    ];
  }
}