import { Component, OnInit, ViewChild } from '@angular/core';
import { ApiService } from '../../core/services/api.service';
import { MatDialog } from '@angular/material/dialog';
import { LoanOfficerFormComponent } from '../loan-officer-form/loan-officer-form.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-loan-officer-list',
  standalone: false,
  templateUrl: './loan-officer-list.component.html',
  styleUrl: './loan-officer-list.component.css',
  animations: [
    trigger('rowAnimation', [
      state('void', style({ opacity: 0, transform: 'translateY(20px)' })),
      transition('void => *', animate('300ms ease-in'))
    ])
  ]
})
export class LoanOfficerListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'username', 'email'];
  dataSource = new MatTableDataSource<any>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private api: ApiService, private dialog: MatDialog) { }

  ngOnInit() {
    this.loadOfficers();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  loadOfficers() {
    this.api.get('loan-officers/admin/1').subscribe({
      next: (data: any) => this.dataSource.data = data,
      error: () => {
        this.dataSource.data = [
          { id: 1, username: 'officer1', email: 'officer1@example.com' },
          { id: 2, username: 'officer2', email: 'officer2@example.com' }
        ];
      }
    });
  }

  addOfficer() {
    const dialogRef = this.dialog.open(LoanOfficerFormComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadOfficers();
      }
    });
  }
}