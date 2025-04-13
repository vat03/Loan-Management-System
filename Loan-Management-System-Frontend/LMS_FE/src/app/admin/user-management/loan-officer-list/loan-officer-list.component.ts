// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-loan-officer-list',
//   standalone: false,
//   templateUrl: './loan-officer-list.component.html',
//   styleUrl: './loan-officer-list.component.scss'
// })
// export class LoanOfficerListComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { LoanOfficerService } from '../../../core/services/loan-officer.service';
// import { LoanOfficerResponse } from '../../models/loan-officer.model';

// @Component({
//   selector: 'app-loan-officer-list',
//   templateUrl: './loan-officer-list.component.html',
//   styleUrls: ['./loan-officer-list.component.scss']
// })
// export class LoanOfficerListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'username', 'email', 'customerCount'];
//   dataSource: LoanOfficerResponse[] = [];

//   constructor(private loanOfficerService: LoanOfficerService) { }

//   ngOnInit(): void {
//     this.loanOfficerService.getAllLoanOfficers().subscribe({
//       next: officers => (this.dataSource = officers),
//       error: err => console.error('Fetch officers failed', err)
//     });
//   }
// }




import { Component, OnInit } from '@angular/core';
import { LoanOfficerService } from '../../../core/services/loan-officer.service';
import { LoanOfficerResponse } from '../../models/loan-officer.model';

@Component({
  selector: 'app-loan-officer-list',
  templateUrl: './loan-officer-list.component.html',
  styleUrls: ['./loan-officer-list.component.scss']
})
export class LoanOfficerListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'username', 'email', 'customerCount'];
  dataSource: LoanOfficerResponse[] = [];

  constructor(private loanOfficerService: LoanOfficerService) { }

  ngOnInit(): void {
    this.loanOfficerService.getAllLoanOfficers().subscribe({
      next: officers => (this.dataSource = officers),
      error: err => console.error('Fetch officers failed', err)
    });
  }
}