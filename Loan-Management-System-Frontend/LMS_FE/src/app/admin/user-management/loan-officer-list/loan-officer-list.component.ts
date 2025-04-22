// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatPaginatorModule } from '@angular/material/paginator';
// import { MatTableDataSource } from '@angular/material/table';
// import { LoanOfficerService } from '../../../core/services/loan-officer.service';
// import { LoanOfficerResponse } from '../../models/loan-officer.model';

// @Component({
//   selector: 'app-loan-officer-list',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatCardModule,
//     MatButtonModule,
//     MatPaginatorModule
//   ],
//   templateUrl: './loan-officer-list.component.html',
//   styleUrls: ['./loan-officer-list.component.scss']
// })
// export class LoanOfficerListComponent implements OnInit {
//   loanOfficers: LoanOfficerResponse[] = [];
//   dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
//   displayedColumns: string[] = ['id', 'username', 'email', 'customerCount', 'report', 'actions'];
//   errorMessage: string | null = null;

//   constructor(private loanOfficerService: LoanOfficerService) { }

//   ngOnInit(): void {
//     this.loadLoanOfficers();
//   }

//   loadLoanOfficers(): void {
//     this.errorMessage = null;
//     this.loanOfficerService.getAllLoanOfficers().subscribe({
//       next: (officers: LoanOfficerResponse[]) => {
//         this.loanOfficers = officers;
//         this.dataSource.data = officers;
//         if (officers.length === 0) {
//           this.errorMessage = 'No loan officers found.';
//         }
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to load loan officers: ${err.message}`;
//         this.dataSource.data = [];
//       }
//     });
//   }

//   deleteLoanOfficer(id: number): void {
//     if (confirm('Are you sure you want to delete this loan officer?')) {
//       this.loanOfficerService.deleteLoanOfficer(id).subscribe({
//         next: () => {
//           this.loanOfficers = this.loanOfficers.filter(officer => officer.id !== id);
//           this.dataSource.data = this.loanOfficers;
//           this.errorMessage = this.loanOfficers.length === 0 ? 'No loan officers found.' : null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to delete loan officer: ${err.message}`;
//         }
//       });
//     }
//   }

//   downloadReport(officerId: number, username: string): void {
//     this.loanOfficerService.downloadOfficerReport(officerId).subscribe({
//       next: (blob: Blob) => {
//         const url = window.URL.createObjectURL(blob);
//         const a = document.createElement('a');
//         a.href = url;
//         a.download = `loan_officer_report_${username}.pdf`;
//         a.click();
//         window.URL.revokeObjectURL(url);
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to download report: ${err.message}`;
//       }
//     });
//   }
// }






// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatPaginatorModule } from '@angular/material/paginator';
// import { MatTableDataSource } from '@angular/material/table';
// import { LoanOfficerService } from '../../../core/services/loan-officer.service';
// import { LoanOfficerResponse } from '../../models/loan-officer.model';

// @Component({
//   selector: 'app-loan-officer-list',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatCardModule,
//     MatButtonModule,
//     MatPaginatorModule
//   ],
//   templateUrl: './loan-officer-list.component.html',
//   styleUrls: ['./loan-officer-list.component.scss']
// })
// export class LoanOfficerListComponent implements OnInit {
//   loanOfficers: LoanOfficerResponse[] = [];
//   dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
//   displayedColumns: string[] = ['id', 'username', 'email', 'customerCount', 'report'];
//   errorMessage: string | null = null;

//   constructor(private loanOfficerService: LoanOfficerService) { }

//   ngOnInit(): void {
//     this.loadLoanOfficers();
//   }

//   loadLoanOfficers(): void {
//     this.errorMessage = null;
//     this.loanOfficerService.getAllLoanOfficers().subscribe({
//       next: (officers: LoanOfficerResponse[]) => {
//         this.loanOfficers = officers;
//         this.dataSource.data = officers;
//         if (officers.length === 0) {
//           this.errorMessage = 'No loan officers found.';
//         }
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to load loan officers: ${err.message}`;
//         this.dataSource.data = [];
//       }
//     });
//   }

//   deleteLoanOfficer(id: number): void {
//     if (confirm('Are you sure you want to delete this loan officer?')) {
//       this.loanOfficerService.deleteLoanOfficer(id).subscribe({
//         next: () => {
//           this.loanOfficers = this.loanOfficers.filter(officer => officer.id !== id);
//           this.dataSource.data = this.loanOfficers;
//           this.errorMessage = this.loanOfficers.length === 0 ? 'No loan officers found.' : null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to delete loan officer: ${err.message}`;
//         }
//       });
//     }
//   }

//   downloadReport(officerId: number, username: string): void {
//     this.errorMessage = null;
//     this.loanOfficerService.downloadOfficerReport(officerId).subscribe({
//       next: (blob: Blob) => {
//         const url = window.URL.createObjectURL(blob);
//         const a = document.createElement('a');
//         a.href = url;
//         a.download = `loan_officer_report_${username}.pdf`;
//         a.click();
//         window.URL.revokeObjectURL(url);
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to download report: ${err.message}`;
//       }
//     });
//   }
// }


import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { LoanOfficerService } from '../../../core/services/loan-officer.service';
import { LoanOfficerResponse } from '../../models/loan-officer.model';

@Component({
  selector: 'app-loan-officer-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatButtonModule,
    MatPaginatorModule
  ],
  templateUrl: './loan-officer-list.component.html',
  styleUrls: ['./loan-officer-list.component.scss']
})
export class LoanOfficerListComponent implements OnInit, AfterViewInit {
  loanOfficers: LoanOfficerResponse[] = [];
  dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
  displayedColumns: string[] = ['id', 'username', 'email', 'customerCount', 'report'];
  errorMessage: string | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private loanOfficerService: LoanOfficerService) { }

  ngOnInit(): void {
    this.loadLoanOfficers();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  loadLoanOfficers(): void {
    this.errorMessage = null;
    this.loanOfficerService.getAllLoanOfficers().subscribe({
      next: (officers: LoanOfficerResponse[]) => {
        this.loanOfficers = officers;
        this.dataSource.data = officers;
        if (officers.length === 0) {
          this.errorMessage = 'No loan officers found.';
        }
      },
      error: (err: Error) => {
        this.errorMessage = `Failed to load loan officers: ${err.message}`;
        this.dataSource.data = [];
      }
    });
  }

  deleteLoanOfficer(id: number): void {
    if (confirm('Are you sure you want to delete this loan officer?')) {
      this.loanOfficerService.deleteLoanOfficer(id).subscribe({
        next: () => {
          this.loanOfficers = this.loanOfficers.filter(officer => officer.id !== id);
          this.dataSource.data = this.loanOfficers;
          this.errorMessage = this.loanOfficers.length === 0 ? 'No loan officers found.' : null;
        },
        error: (err: Error) => {
          this.errorMessage = `Failed to delete loan officer: ${err.message}`;
        }
      });
    }
  }

  downloadReport(officerId: number, username: string): void {
    this.errorMessage = null;
    this.loanOfficerService.downloadOfficerReport(officerId).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `loan_officer_report_${username}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err: Error) => {
        this.errorMessage = `Failed to download report: ${err.message}`;
      }
    });
  }
}