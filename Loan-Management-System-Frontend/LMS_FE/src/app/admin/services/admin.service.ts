// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class AdminService {

//   constructor() { }
// }


// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError } from 'rxjs/operators';
// import { LoanOfficerDTO } from '../models/admin.model';

// @Injectable({
//   providedIn: 'root'
// })
// export class AdminService {
//   private apiUrl = 'http://localhost:8080/api';

//   constructor(private http: HttpClient) {}

//   getLoanOfficers(): Observable<LoanOfficerDTO[]> {
//     return this.http.get<LoanOfficerDTO[]>(`${this.apiUrl}/admin/loan-officers`).pipe(
//       catchError(this.handleError)
//     );
//   }

//   deleteLoanOfficer(id: number): Observable<void> {
//     return this.http.delete<void>(`${this.apiUrl}/admin/loan-officers/${id}`).pipe(
//       catchError(this.handleError)
//     );
//   }

//   private handleError(error: HttpErrorResponse): Observable<never> {
//     let errorMessage = 'An error occurred';
//     if (error.error instanceof ErrorEvent) {
//       errorMessage = `Client error: ${error.error.message}`;
//     } else {
//       errorMessage = `Server error: ${error.status} - ${error.message}`;
//       if (error.error) {
//         errorMessage += `: ${error.error.message || error.error}`;
//       }
//     }
//     return throwError(() => new Error(errorMessage));
//   }
// }

import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { LoanOfficerDTO } from '../models/admin.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getLoanOfficers(): Observable<LoanOfficerDTO[]> {
    return this.http.get<LoanOfficerDTO[]>(`${this.apiUrl}/admin/loan-officers`).pipe(
      catchError(this.handleError)
    );
  }

  deleteLoanOfficer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/admin/loan-officers/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  downloadOfficerReport(officerId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/admin/loan-officers/${officerId}/report`, {
      responseType: 'blob'
    }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Client error: ${error.error.message}`;
    } else {
      errorMessage = `Server error: ${error.status} - ${error.message}`;
      if (error.error) {
        errorMessage += `: ${error.error.message || error.error}`;
      }
    }
    return throwError(() => new Error(errorMessage));
  }
}