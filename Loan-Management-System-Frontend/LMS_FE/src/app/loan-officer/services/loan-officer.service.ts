import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { LoanResponseDTO, DocumentResponseDTO, CustomerResponseDTO } from '../models/loan-officer.model';

@Injectable({
  providedIn: 'root'
})
export class LoanOfficerService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getAssignedLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
    return this.http.get<LoanResponseDTO[]>(`${this.apiUrl}/loans/getByLoanOfficerId/loan-officer/${loanOfficerId}`).pipe(
      catchError(this.handleError)
    );
  }

  getNpaLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
    return this.http.get<LoanResponseDTO[]>(`${this.apiUrl}/loans/getByLoanOfficerId/loan-officer/${loanOfficerId}?isNpa=true`).pipe(
      catchError(this.handleError)
    );
  }

  getPendingDocuments(loanOfficerId: number): Observable<DocumentResponseDTO[]> {
    return this.http.get<DocumentResponseDTO[]>(`${this.apiUrl}/documents/getByLoanOfficerId/loan-officer/${loanOfficerId}?status=PENDING_VERIFICATION`).pipe(
      tap(docs => console.log('Fetched pending documents:', docs)),
      catchError(this.handleError)
    );
  }

  getDocumentsByLoanId(loanId: number): Observable<DocumentResponseDTO[]> {
    return this.http.get<DocumentResponseDTO[]>(`${this.apiUrl}/documents/getByLoanId/loan/${loanId}`).pipe(
      tap(docs => console.log(`Fetched documents for loan ${loanId}:`, docs)),
      catchError(this.handleError)
    );
  }

  getApprovedLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
    return this.http.get<LoanResponseDTO[]>(`${this.apiUrl}/loans/getByLoanOfficerId/loan-officer/${loanOfficerId}?status=APPROVED`).pipe(
      catchError(this.handleError)
    );
  }

  getCustomersByLoanOfficerId(loanOfficerId: number): Observable<CustomerResponseDTO[]> {
    return this.http.get<CustomerResponseDTO[]>(`${this.apiUrl}/customers/getCustomerByLoanOfficerId/loan-officer/${loanOfficerId}`).pipe(
      tap(customers => console.log('Fetched customers:', customers)),
      catchError((error: HttpErrorResponse) => {
        console.error(`Error fetching customers for loanOfficerId ${loanOfficerId}:`, error);
        return this.handleError(error);
      })
    );
  }

  verifyDocument(documentId: number, verification: { status: string }): Observable<DocumentResponseDTO> {
    return this.http.put<DocumentResponseDTO>(`${this.apiUrl}/documents/${documentId}/verify`, verification).pipe(
      tap(doc => console.log(`Verified document ${documentId}:`, doc)),
      catchError(this.handleError)
    );
  }

  markAsNPA(loanId: number, data: { approve: boolean }): Observable<LoanResponseDTO> {
    return this.http.put<LoanResponseDTO>(`${this.apiUrl}/loans/${loanId}/npa`, data).pipe(
      tap(loan => console.log(`Marked loan ${loanId} as NPA:`, loan)),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('API Error:', error);
    let errorMessage = 'An unexpected error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Client error: ${error.error.message}`;
    } else {
      errorMessage = `Server error: ${error.status} - ${error.message}`;
      if (error.error && typeof error.error === 'object') {
        console.log('Error Response Body:', error.error);
        errorMessage = error.error.message || `Server error: ${error.status} - ${error.statusText}`;
      } else if (error.error) {
        console.log('Error Response Text:', error.error);
        errorMessage = `Server error: ${error.status} - ${error.error}`;
      }
    }
    return throwError(() => new Error(errorMessage));
  }
}