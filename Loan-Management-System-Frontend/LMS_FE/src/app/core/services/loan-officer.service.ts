// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { Observable, of, throwError } from 'rxjs';
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
//       catchError((err: HttpErrorResponse) => {
//         console.error('Add loan officer failed:', err);
//         let errorMessage = 'Failed to add loan officer. Please try again.';
//         if (err.status === 404) {
//           errorMessage = 'Loan officer endpoint not found. Check server configuration or admin ID.';
//         } else if (err.status === 0) {
//           errorMessage = 'Server unreachable. Check CORS settings or server status.';
//         }
//         return throwError(() => new Error(errorMessage));
//       })
//     );
//   }

//   getLoanOfficerById(id: number): Observable<LoanOfficerResponse> {
//     const url = `${this.apiUrl}/getLoanOfficerById/${id}`;
//     console.log('Fetching loan officer:', url);
//     return this.http.get<LoanOfficerResponse>(url).pipe(
//       tap(response => console.log('Loan officer response:', response)),
//       catchError((err: HttpErrorResponse) => {
//         console.error('Fetch loan officer failed:', err);
//         return throwError(() => new Error('Failed to fetch loan officer.'));
//       })
//     );
//   }

//   getAllLoanOfficers(): Observable<LoanOfficerResponse[]> {
//     const url = `${this.apiUrl}/getAllLoanOfficers`;
//     console.log('Fetching loan officers from:', url);
//     return this.http.get<LoanOfficerResponse[]>(url).pipe(
//       tap(data => console.log('Loan officers response:', data)),
//       catchError((err: HttpErrorResponse) => {
//         console.error('Fetch loan officers failed:', err);
//         if (err.status === 404 || err.status === 500) {
//           console.log('No loan officers found, returning empty array');
//           return of([]); // Return empty array for 404 or 500
//         }
//         return throwError(() => new Error('Failed to fetch loan officers.'));
//       })
//     );
//   }

//   deleteLoanOfficer(id: number): Observable<void> {
//     const url = `${this.apiUrl}/${id}`;
//     console.log('Deleting loan officer:', url);
//     return this.http.delete<void>(url).pipe(
//       tap(() => console.log('Loan officer deleted:', id)),
//       catchError((err: HttpErrorResponse) => {
//         console.error('Delete loan officer failed:', err);
//         let errorMessage = 'Failed to delete loan officer. Please try again.';
//         if (err.status === 404) {
//           errorMessage = 'Loan officer not found.';
//         }
//         return throwError(() => new Error(errorMessage));
//       })
//     );
//   }

//   downloadOfficerReport(id: number): Observable<Blob> {
//     const url = `http://localhost:8080/api/reports/loan-officer/${id}`;
//     console.log('Downloading report for loan officer:', url);
//     return this.http.get(url, { responseType: 'blob' }).pipe(
//       tap(() => console.log('Report downloaded for loan officer:', id)),
//       catchError((err: HttpErrorResponse) => {
//         console.error('Download report failed:', err);
//         return throwError(() => new Error('Failed to download report.'));
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
  private reportApiUrl = `${environment.apiUrl}/api/reports`;

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
    const url = `${this.reportApiUrl}/loan-officer/${id}`;
    console.log('Downloading report for loan officer:', url);
    return this.http.get(url, { responseType: 'blob' }).pipe(
      tap((blob: Blob) => {
        if (blob.size === 0) {
          throw new Error('Received empty report file.');
        }
        if (blob.type !== 'application/pdf') {
          throw new Error(`Invalid file type: ${blob.type}. Expected application/pdf.`);
        }
        console.log('Report downloaded for loan officer:', id, 'Size:', blob.size);
      }),
      catchError((err: HttpErrorResponse | Error) => {
        console.error('Download report failed:', err);
        let errorMessage = 'Failed to download report. Please try again.';
        if (err instanceof HttpErrorResponse) {
          if (err.status === 404) {
            errorMessage = 'Loan officer or report data not found.';
          } else if (err.status === 400) {
            errorMessage = err.error?.message || 'Invalid report data.';
          }
        } else {
          errorMessage = err.message || errorMessage;
        }
        return throwError(() => new Error(errorMessage));
      })
    );
  }
}