import { Component, OnInit, ViewChild } from '@angular/core';
import { ApiService } from '../../core/services/api.service';
import { MatDialog } from '@angular/material/dialog';
import { LoanSchemeFormComponent } from '../loan-scheme-form/loan-scheme-form.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-loan-scheme-list',
  standalone: false,
  templateUrl: './loan-scheme-list.component.html',
  styleUrl: './loan-scheme-list.component.css',
  animations: [
    trigger('rowAnimation', [
      state('void', style({ opacity: 0, transform: 'translateY(20px)' })),
      transition('void => *', animate('300ms ease-in'))
    ])
  ]
})
export class LoanSchemeListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'schemeName', 'interestRate', 'tenureMonths'];
  dataSource = new MatTableDataSource<any>();
  adminId = 1; // Hardcoded for now

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private api: ApiService, private dialog: MatDialog) { }

  ngOnInit() {
    this.loadSchemes();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  loadSchemes() {
    this.api.get(`loan-schemes/getByAdminId/admin/${this.adminId}`).subscribe({
      next: (data: any) => this.dataSource.data = data,
      error: () => {
        this.dataSource.data = [
          { id: 1, schemeName: 'Home Loan', interestRate: 5.5, tenureMonths: 120 },
          { id: 2, schemeName: 'Car Loan', interestRate: 6.0, tenureMonths: 60 }
        ];
      }
    });
  }

  addScheme() {
    const dialogRef = this.dialog.open(LoanSchemeFormComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadSchemes();
      }
    });
  }
}