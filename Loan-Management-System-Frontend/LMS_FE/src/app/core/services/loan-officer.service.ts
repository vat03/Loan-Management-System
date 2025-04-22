// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { environment } from '../../../environments/environment';
// import { LoanOfficerRequest, LoanOfficerResponse } from '../../admin/models/loan-officer.model';
// import { tap } from 'rxjs/operators';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoanOfficerService {
//   private apiUrl = `${environment.apiUrl}/api/loan-officers`;

//   constructor(private http: HttpClient) { }

//   // TODO: Backend endpoint POST http://localhost:8080/api/loan-officers/addLoanOfficer?adminId=<id>
//   // Body: { "email": "string", "username": "string", "password": "string" }
//   // Response: { "id": number, "username": "string", "email": "string", "adminId": number, "customerIds": number[] }
//   addLoanOfficer(adminId: number, request: LoanOfficerRequest): Observable<LoanOfficerResponse> {
//     const url = `${this.apiUrl}/addLoanOfficer?adminId=${adminId}`;
//     console.log('Adding loan officer:', request, 'to:', url);
//     return this.http.post<LoanOfficerResponse>(url, request).pipe(
//       tap(response => console.log('Loan officer added:', response)),
//       tap({ error: err => console.error('Add loan officer failed:', err) })
//     );
//   }

//   // TODO: Backend endpoint GET http://localhost:8080/api/loan-officers/getLoanOfficerById/{id}
//   // Response: { "id": number, "username": "string", "email": "string", "adminId": number, "customerIds": number[] }
//   getLoanOfficerById(id: number): Observable<LoanOfficerResponse> {
//     const url = `${this.apiUrl}/getLoanOfficerById/${id}`;
//     console.log('Fetching loan officer:', url);
//     return this.http.get<LoanOfficerResponse>(url).pipe(
//       tap(response => console.log('Loan officer response:', response)),
//       tap({ error: err => console.error('Fetch loan officer failed:', err) })
//     );
//   }

//   // TODO: Backend endpoint GET http://localhost:8080/api/loan-officers/getAllLoanOfficers
//   // Response: [{ "id": number, "username": "string", "email": "string", "adminId": number, "customerIds": number[] }, ...]
//   getAllLoanOfficers(): Observable<LoanOfficerResponse[]> {
//     const url = `${this.apiUrl}/getAllLoanOfficers`;
//     console.log('Fetching loan officers from:', url);
//     return this.http.get<LoanOfficerResponse[]>(url).pipe(
//       tap(data => console.log('Loan officers response:', data)),
//       tap({ error: err => console.error('Fetch loan officers failed:', err) })
//     );
//   }
// }










// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { Observable, of } from 'rxjs';
// import { environment } from '../../../environments/environment';
// import { LoanOfficerRequest, LoanOfficerResponse } from '../../admin/models/loan-officer.model';
// import { catchError, tap } from 'rxjs/operators';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoanOfficerService {
//   private apiUrl = `${environment.apiUrl}/api/loan-officers`;

//   constructor(private http: HttpClient) { }

//   addLoanOfficer(adminId: number, request: LoanOfficerRequest): Observable<LoanOfficerResponse> {
//     const url = `${this.apiUrl}/addLoanOfficer?adminId=${adminId}`;
//     console.log('Adding loan officer:', request, 'to:', url);
//     return this.http.post<LoanOfficerResponse>(url, request).pipe(
//       tap(response => console.log('Loan officer added:', response)),
//       tap({ error: err => console.error('Add loan officer failed:', err) })
//     );
//   }

//   getLoanOfficerById(id: number): Observable<LoanOfficerResponse> {
//     const url = `${this.apiUrl}/getLoanOfficerById/${id}`;
//     console.log('Fetching loan officer:', url);
//     return this.http.get<LoanOfficerResponse>(url).pipe(
//       tap(response => console.log('Loan officer response:', response)),
//       tap({ error: err => console.error('Fetch loan officer failed:', err) })
//     );
//   }

//   getAllLoanOfficers(): Observable<LoanOfficerResponse[]> {
//     const url = `${this.apiUrl}/getAllLoanOfficers`;
//     console.log('Fetching loan officers from:', url);
//     return this.http.get<LoanOfficerResponse[]>(url).pipe(
//       tap(data => console.log('Loan officers response:', data)),
//       catchError((err: HttpErrorResponse) => {
//         console.error('Fetch loan officers failed:', err);
//         if (err.status === 404) {
//           console.log('No loan officers found, returning empty array');
//           return of([]); // Return empty array for 404
//         }
//         throw err; // Rethrow other errors
//       })
//     );
//   }
// }


import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoanOfficerRequest, LoanOfficerResponse } from '../../admin/models/loan-officer.model';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoanOfficerService {
  private apiUrl = `${environment.apiUrl}/api/loan-officers`;

  constructor(private http: HttpClient) { }

  addLoanOfficer(adminId: number, request: LoanOfficerRequest): Observable<LoanOfficerResponse> {
    const url = `${this.apiUrl}/addLoanOfficer?adminId=${adminId}`;
    console.log('Adding loan officer:', request, 'to:', url);
    return this.http.post<LoanOfficerResponse>(url, request).pipe(
      tap(response => console.log('Loan officer added:', response)),
      catchError((err: HttpErrorResponse) => {
        console.error('Add loan officer failed:', err);
        let errorMessage = 'Failed to add loan officer. Please try again.';
        if (err.status === 404) {
          errorMessage = 'Loan officer endpoint not found. Check server configuration or admin ID.';
        } else if (err.status === 0) {
          errorMessage = 'Server unreachable. Check CORS settings or server status.';
        }
        return throwError(() => new Error(errorMessage));
      })
    );
  }

  getLoanOfficerById(id: number): Observable<LoanOfficerResponse> {
    const url = `${this.apiUrl}/getLoanOfficerById/${id}`;
    console.log('Fetching loan officer:', url);
    return this.http.get<LoanOfficerResponse>(url).pipe(
      tap(response => console.log('Loan officer response:', response)),
      catchError((err: HttpErrorResponse) => {
        console.error('Fetch loan officer failed:', err);
        return throwError(() => new Error('Failed to fetch loan officer.'));
      })
    );
  }

  getAllLoanOfficers(): Observable<LoanOfficerResponse[]> {
    const url = `${this.apiUrl}/getAllLoanOfficers`;
    console.log('Fetching loan officers from:', url);
    return this.http.get<LoanOfficerResponse[]>(url).pipe(
      tap(data => console.log('Loan officers response:', data)),
      catchError((err: HttpErrorResponse) => {
        console.error('Fetch loan officers failed:', err);
        if (err.status === 404 || err.status === 500) {
          console.log('No loan officers found, returning empty array');
          return of([]); // Return empty array for 404 or 500
        }
        return throwError(() => new Error('Failed to fetch loan officers.'));
      })
    );
  }

  deleteLoanOfficer(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    console.log('Deleting loan officer:', url);
    return this.http.delete<void>(url).pipe(
      tap(() => console.log('Loan officer deleted:', id)),
      catchError((err: HttpErrorResponse) => {
        console.error('Delete loan officer failed:', err);
        let errorMessage = 'Failed to delete loan officer. Please try again.';
        if (err.status === 404) {
          errorMessage = 'Loan officer not found.';
        }
        return throwError(() => new Error(errorMessage));
      })
    );
  }

  downloadOfficerReport(id: number): Observable<Blob> {
    const url = `${this.apiUrl}/report/${id}`;
    console.log('Downloading report for loan officer:', url);
    return this.http.get(url, { responseType: 'blob' }).pipe(
      tap(() => console.log('Report downloaded for loan officer:', id)),
      catchError((err: HttpErrorResponse) => {
        console.error('Download report failed:', err);
        return throwError(() => new Error('Failed to download report.'));
      })
    );
  }
}