// import { Component, OnInit } from '@angular/core';
// import { LoanOfficerService } from '../../../core/services/loan-officer.service';
// import { LoanOfficerResponse } from '../../models/loan-officer.model';
// import { MatTableDataSource } from '@angular/material/table';
// import { MatTableModule } from '@angular/material/table';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-loan-officer-list',
//   standalone: true,
//   imports: [CommonModule, MatTableModule],
//   template: `
//     <div class="container">
//       <h2>Loan Officers</h2>
//       <div *ngIf="errorMessage" class="error-message">{{ errorMessage }}</div>
//       <table mat-table [dataSource]="dataSource">
//         <ng-container matColumnDef="id">
//           <th mat-header-cell *matHeaderCellDef>ID</th>
//           <td mat-cell *matCellDef="let officer">{{ officer.id }}</td>
//         </ng-container>
//         <ng-container matColumnDef="username">
//           <th mat-header-cell *matHeaderCellDef>Username</th>
//           <td mat-cell *matCellDef="let officer">{{ officer.username }}</td>
//         </ng-container>
//         <ng-container matColumnDef="email">
//           <th mat-header-cell *matHeaderCellDef>Email</th>
//           <td mat-cell *matCellDef="let officer">{{ officer.email }}</td>
//         </ng-container>
//         <ng-container matColumnDef="customerCount">
//           <th mat-header-cell *matHeaderCellDef>Customers</th>
//           <td mat-cell *matCellDef="let officer">{{ officer.customerIds.length }}</td>
//         </ng-container>
//         <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
//         <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
//       </table>
//     </div>
//   `,
//   styles: [
//     `
//       .container {
//         padding: 16px;
//       }
//       table {
//         width: 100%;
//       }
//       .error-message {
//         color: red;
//         margin-bottom: 16px;
//       }
//     `
//   ]
// })
// export class LoanOfficerListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'username', 'email', 'customerCount'];
//   dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
//   errorMessage: string | null = null;

//   constructor(private loanOfficerService: LoanOfficerService) { }

//   ngOnInit(): void {
//     this.loanOfficerService.getAllLoanOfficers().subscribe({
//       next: officers => {
//         this.dataSource.data = officers;
//       },
//       error: err => {
//         console.error('Fetch officers failed:', err);
//         this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load loan officers.';
//       }
//     });
//   }
// }








// import { Component, OnInit, ViewChild } from '@angular/core';
// import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
// import { MatTableDataSource } from '@angular/material/table';
// import { LoanOfficerService } from '../../../core/services/loan-officer.service';
// import { LoanOfficerResponse } from '../../models/loan-officer.model';
// import { MatTableModule } from '@angular/material/table';
// import { MatButtonModule } from '@angular/material/button';
// import { MatIconModule } from '@angular/material/icon';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-loan-officer-list',
//   standalone: true,
//   imports: [
//     CommonModule,
//     MatTableModule,
//     MatButtonModule,
//     MatIconModule,
//     MatPaginatorModule
//   ],
//   templateUrl: './loan-officer-list.component.html',
//   styleUrls: ['./loan-officer-list.component.scss']
// })
// export class LoanOfficerListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'username', 'email', 'customerCount'];
//   dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
//   errorMessage: string | null = null;

//   @ViewChild(MatPaginator) paginator!: MatPaginator;

//   constructor(private loanOfficerService: LoanOfficerService) { }

//   ngOnInit(): void {
//     this.loanOfficerService.getAllLoanOfficers().subscribe({
//       next: officers => {
//         this.dataSource.data = officers;
//         this.dataSource.paginator = this.paginator;
//       },
//       error: err => {
//         console.error('Fetch officers failed:', err);
//         this.errorMessage = err.status === 0 ? 'Server unreachable. Check CORS settings.' : 'Failed to load loan officers.';
//       }
//     });
//   }
// }

// import { Component, OnInit } from '@angular/core';
// import { MatTableDataSource } from '@angular/material/table';
// import { LoanOfficerService } from '../services/loan-officer.service';
// import jsPDF from 'jspdf';
// import autoTable from 'jspdf-autotable';

// @Component({
//   selector: 'app-loan-officer-list',
//   templateUrl: './loan-officer-list.component.html',
//   styleUrls: ['./loan-officer-list.component.css']
// })
// export class LoanOfficerListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'username', 'email', 'customerCount', 'report'];
//   dataSource = new MatTableDataSource<any>();

//   constructor(private loanOfficerService: LoanOfficerService) {}

//   ngOnInit(): void {
//     this.loadLoanOfficers();
//   }

//   loadLoanOfficers(): void {
//     this.loanOfficerService.getAllLoanOfficers().subscribe({
//       next: (officers) => this.dataSource.data = officers,
//       error: (err) => console.error('Error loading loan officers', err)
//     });
//   }

//   downloadReport(loanOfficerId: number): void {
//     this.loanOfficerService.getReport(loanOfficerId).subscribe({
//       next: (report) => {
//         const doc = new jsPDF();
//         doc.setFontSize(16);
//         doc.text('Loan Officer Report', 14, 20);

//         const rows = Object.entries(report).map(([key, value]) => [this.formatKey(key), value.toString()]);

//         autoTable(doc, {
//           startY: 30,
//           head: [['Metric', 'Value']],
//           body: rows,
//         });

//         doc.save(`loan_officer_report_${loanOfficerId}.pdf`);
//       },
//       error: (err) => console.error('Error generating report', err)
//     });
//   }

//   private formatKey(key: string): string {
//     return key
//       .replace(/([A-Z])/g, ' $1')
//       .replace(/^./, str => str.toUpperCase());
//   }
// }




// import { Component, OnInit, ViewChild } from '@angular/core';
// import { MatPaginator } from '@angular/material/paginator';
// import { MatTableDataSource } from '@angular/material/table';
// import { HttpClient } from '@angular/common/http';

// import { LoanOfficerResponse } from '../../models/loan-officer.model';

// @Component({
//   selector: 'app-loan-officer-list',
//   standalone: true,
//   imports: [
//     // your Angular material imports
//   ],
//   templateUrl: './loan-officer-list.component.html',
//   styleUrls: ['./loan-officer-list.component.scss']
// })
// export class LoanOfficerListComponent implements OnInit {
//   displayedColumns: string[] = ['id', 'username', 'email', 'customerCount', 'report'];
//   dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
//   errorMessage: string | null = null;

//   @ViewChild(MatPaginator) paginator!: MatPaginator;

//   constructor(private http: HttpClient) {}

//   ngOnInit(): void {
//     this.http.get<LoanOfficerResponse[]>('/api/loan-officers/all').subscribe({
//       next: officers => {
//         this.dataSource.data = officers;
//         this.dataSource.paginator = this.paginator;
//       },
//       error: err => {
//         console.error('Fetch officers failed:', err);
//         this.errorMessage = err.status === 0
//           ? 'Server unreachable. Check CORS settings.'
//           : 'Failed to load loan officers.';
//       }
//     });
//   }

//   downloadReport(loanOfficerId: number, username: string): void {
//     this.http.get(`/api/reports/loan-officer/${loanOfficerId}`, { responseType: 'json' }).subscribe({
//       next: report => {
//         const docDefinition = this.buildPdfDefinition(report, username);
//         const pdfDocGenerator = (window as any).pdfMake.createPdf(docDefinition);
//         pdfDocGenerator.download(`${username}-loan-officer-report.pdf`);
//       },
//       error: err => {
//         console.error('Download failed:', err);
//         alert('Failed to generate report.');
//       }
//     });
//   }

//   buildPdfDefinition(report: any, username: string): any {
//     return {
//       content: [
//         { text: `${username} - Loan Officer Report`, style: 'header' },
//         { text: '\n' },
//         {
//           table: {
//             widths: ['*', '*'],
//             body: [
//               ['Loans Offered', report.loansOffered],
//               ['Rejected', report.rejected],
//               ['In Process', report.inProcess],
//               ['Amount Disbursed', `₹ ${report.amountDisbursed}`],
//               ['Customers Entertained', report.customersEntertained],
//               ['NPAs', report.npas],
//               ['Red Flagged Customers', report.redFlaggedCustomers]
//             ]
//           }
//         }
//       ],
//       styles: {
//         header: {
//           fontSize: 18,
//           bold: true,
//           alignment: 'center'
//         }
//       }
//     };
//   }
// }



// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatPaginatorModule } from '@angular/material/paginator';
// import { AdminService } from '../../services/admin.service';
// import { LoanOfficerDTO } from '../../models/admin.model';

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
//   loanOfficers: LoanOfficerDTO[] = [];
//   displayedColumns: string[] = ['id', 'username', 'email', 'firstName', 'lastName', 'actions'];
//   errorMessage: string | null = null;

//   constructor(private adminService: AdminService) { }

//   ngOnInit(): void {
//     this.loadLoanOfficers();
//   }

//   loadLoanOfficers(): void {
//     this.adminService.getLoanOfficers().subscribe({
//       next: officers => {
//         this.loanOfficers = officers;
//         if (officers.length === 0) {
//           this.errorMessage = 'No loan officers found.';
//         }
//       },
//       error: err => {
//         this.errorMessage = `Failed to load loan officers: ${err.message}`;
//       }
//     });
//   }

//   deleteLoanOfficer(id: number): void {
//     if (confirm('Are you sure you want to delete this loan officer?')) {
//       this.adminService.deleteLoanOfficer(id).subscribe({
//         next: () => {
//           this.loanOfficers = this.loanOfficers.filter(officer => officer.id !== id);
//           this.errorMessage = null;
//         },
//         error: err => {
//           this.errorMessage = `Failed to delete loan officer: ${err.message}`;
//         }
//       });
//     }
//   }
// }


// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatPaginatorModule } from '@angular/material/paginator';
// //import { AdminService } from '../../services/admin.service';
// // import { LoanOfficerDTO } from '../../models/admin.model';
// import { HttpErrorResponse } from '@angular/common/http';

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
//   loanOfficers: LoanOfficerDTO[] = [];
//   displayedColumns: string[] = ['id', 'username', 'email', 'firstName', 'lastName', 'actions'];
//   errorMessage: string | null = null;

//   constructor(private adminService: AdminService) { }

//   ngOnInit(): void {
//     this.loadLoanOfficers();
//   }

//   loadLoanOfficers(): void {
//     this.adminService.getLoanOfficers().subscribe({
//       next: officers => {
//         this.loanOfficers = officers;
//         if (officers.length === 0) {
//           this.errorMessage = 'No loan officers found.';
//         }
//       },
//       error: (err: HttpErrorResponse) => {
//         this.errorMessage = `Failed to load loan officers: ${err.message}`;
//       }
//     });
//   }

//   deleteLoanOfficer(id: number): void {
//     if (confirm('Are you sure you want to delete this loan officer?')) {
//       this.adminService.deleteLoanOfficer(id).subscribe({
//         next: () => {
//           this.loanOfficers = this.loanOfficers.filter(officer => officer.id !== id);
//           this.errorMessage = null;
//         },
//         error: (err: HttpErrorResponse) => {
//           this.errorMessage = `Failed to delete loan officer: ${err.message}`;
//         }
//       });
//     }
//   }
// }



// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatPaginatorModule } from '@angular/material/paginator';
// import { AdminService } from '../../services/admin.service';
// import { LoanOfficerDTO } from '../../models/admin.model';

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
//   loanOfficers: LoanOfficerDTO[] = [];
//   displayedColumns: string[] = ['id', 'username', 'email', 'firstName', 'lastName', 'actions'];
//   errorMessage: string | null = null;

//   constructor(private adminService: AdminService) {}

//   ngOnInit(): void {
//     this.loadLoanOfficers();
//   }

//   loadLoanOfficers(): void {
//     this.adminService.getLoanOfficers().subscribe({
//       next: officers => {
//         this.loanOfficers = officers;
//         if (officers.length === 0) {
//           this.errorMessage = 'No loan officers found.';
//         }
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to load loan officers: ${err.message}`;
//       }
//     });
//   }

//   deleteLoanOfficer(id: number): void {
//     if (confirm('Are you sure you want to delete this loan officer?')) {
//       this.adminService.deleteLoanOfficer(id).subscribe({
//         next: () => {
//           this.loanOfficers = this.loanOfficers.filter(officer => officer.id !== id);
//           this.errorMessage = null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to delete loan officer: ${err.message}`;
//         }
//       });
//     }
//   }
// }





// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatPaginatorModule } from '@angular/material/paginator';
// import { AdminService } from '../../services/admin.service';
// import { LoanOfficerDTO } from '../../models/admin.model';

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
//   loanOfficers: LoanOfficerDTO[] = [];
//   displayedColumns: string[] = ['id', 'username', 'email', 'firstName', 'lastName', 'actions'];
//   errorMessage: string | null = null;

//   constructor(private adminService: AdminService) {}

//   ngOnInit(): void {
//     this.loadLoanOfficers();
//   }

//   loadLoanOfficers(): void {
//     this.adminService.getLoanOfficers().subscribe({
//       next: (officers: LoanOfficerDTO[]) => {
//         this.loanOfficers = officers;
//         if (officers.length === 0) {
//           this.errorMessage = 'No loan officers found.';
//         }
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to load loan officers: ${err.message}`;
//       }
//     });
//   }

//   deleteLoanOfficer(id: number): void {
//     if (confirm('Are you sure you want to delete this loan officer?')) {
//       this.adminService.deleteLoanOfficer(id).subscribe({
//         next: () => {
//           this.loanOfficers = this.loanOfficers.filter(officer => officer.id !== id);
//           this.errorMessage = null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to delete loan officer: ${err.message}`;
//         }
//       });
//     }
//   }
// }



// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MatTableModule } from '@angular/material/table';
// import { MatCardModule } from '@angular/material/card';
// import { MatButtonModule } from '@angular/material/button';
// import { MatPaginatorModule } from '@angular/material/paginator';
// import { MatTableDataSource } from '@angular/material/table';
// import { AdminService } from '../../services/admin.service';
// import { LoanOfficerDTO } from '../../models/admin.model';

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
//   loanOfficers: LoanOfficerDTO[] = [];
//   dataSource = new MatTableDataSource<LoanOfficerDTO>([]);
//   displayedColumns: string[] = ['id', 'username', 'email', 'firstName', 'lastName', 'actions'];
//   errorMessage: string | null = null;

//   constructor(private adminService: AdminService) { }

//   ngOnInit(): void {
//     this.loadLoanOfficers();
//   }

//   loadLoanOfficers(): void {
//     this.adminService.getLoanOfficers().subscribe({
//       next: (officers: LoanOfficerDTO[]) => {
//         this.loanOfficers = officers;
//         this.dataSource.data = officers;
//         if (officers.length === 0) {
//           this.errorMessage = 'No loan officers found.';
//         }
//       },
//       error: (err: Error) => {
//         this.errorMessage = `Failed to load loan officers: ${err.message}`;
//       }
//     });
//   }

//   deleteLoanOfficer(id: number): void {
//     if (confirm('Are you sure you want to delete this loan officer?')) {
//       this.adminService.deleteLoanOfficer(id).subscribe({
//         next: () => {
//           this.loanOfficers = this.loanOfficers.filter(officer => officer.id !== id);
//           this.dataSource.data = this.loanOfficers;
//           this.errorMessage = null;
//         },
//         error: (err: Error) => {
//           this.errorMessage = `Failed to delete loan officer: ${err.message}`;
//         }
//       });
//     }
//   }

//   downloadReport(officerId: number, username: string): void {
//     this.adminService.downloadOfficerReport(officerId).subscribe({
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




import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginatorModule } from '@angular/material/paginator';
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
export class LoanOfficerListComponent implements OnInit {
  loanOfficers: LoanOfficerResponse[] = [];
  dataSource = new MatTableDataSource<LoanOfficerResponse>([]);
  displayedColumns: string[] = ['id', 'username', 'email', 'customerCount', 'report', 'actions'];
  errorMessage: string | null = null;

  constructor(private loanOfficerService: LoanOfficerService) { }

  ngOnInit(): void {
    this.loadLoanOfficers();
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