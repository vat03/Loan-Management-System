// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class CustomerService {

//   constructor() { }
// }


// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError, map } from 'rxjs/operators';
// import { LoanScheme, Loan, Document, LoanPayment, Profile } from '../models/customer.model';

// @Injectable({
//     providedIn: 'root'
// })
// export class CustomerService {
//     private apiUrl = 'http://localhost:8080/api';

//     constructor(private http: HttpClient) { }

//     private handleError(error: HttpErrorResponse): Observable<never> {
//         let errorMessage = 'An error occurred. Please try again.';
//         if (error.error instanceof ErrorEvent) {
//             errorMessage = `Client error: ${error.error.message}`;
//         } else {
//             errorMessage = `Server error: ${error.status} - ${error.error.message || error.message}`;
//         }
//         return throwError(() => new Error(errorMessage));
//     }

//     getLoanSchemes(): Observable<LoanScheme[]> {
//         return this.http.get<LoanScheme[]>(`${this.apiUrl}/loan-schemes/all`)
//             .pipe(catchError(this.handleError));
//     }

//     applyForLoan(loan: { amount: number; loanSchemeId: number; customerId: number }): Observable<Loan> {
//         return this.http.post<Loan>(`${this.apiUrl}/loans/apply`, loan)
//             .pipe(catchError(this.handleError));
//     }

//     uploadDocument(formData: FormData): Observable<Document> {
//         return this.http.post<Document>(`${this.apiUrl}/documents/upload`, formData)
//             .pipe(catchError(this.handleError));
//     }

//     getCustomerLoans(customerId: number): Observable<Loan[]> {
//         return this.http.get<Loan[]>(`${this.apiUrl}/loans/getByCustomerId/customer/${customerId}`)
//             .pipe(catchError(this.handleError));
//     }

//     getLoanPayments(loanId: number, status?: string): Observable<LoanPayment[]> {
//         const url = status
//             ? `${this.apiUrl}/loan-payments/loan/${loanId}?status=${status}`
//             : `${this.apiUrl}/loan-payments/loan/${loanId}`;
//         return this.http.get<LoanPayment[]>(url)
//             .pipe(catchError(this.handleError));
//     }

//     initiatePayment(loanPaymentId: number): Observable<string> {
//         return this.http.post<string>(`${this.apiUrl}/loan-payments/${loanPaymentId}/repay`, {})
//             .pipe(catchError(this.handleError));
//     }

//     completePayment(payment: { orderId: string; paymentId: string; signature: string; loanPaymentId: number }): Observable<string> {
//         return this.http.post<string>(`${this.apiUrl}/loan-payments/complete`, payment)
//             .pipe(catchError(this.handleError));
//     }

//     getProfile(customerId: number): Observable<Profile> {
//         return this.http.get<Profile>(`${this.apiUrl}/profile/customer/${customerId}`)
//             .pipe(catchError(this.handleError));
//     }
// }

import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { LoanScheme, Loan, Document, LoanPayment, Profile } from '../models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An error occurred. Please try again.';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Client error: ${error.error.message}`;
    } else {
      errorMessage = `Server error: ${error.status} - ${error.error.message || error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }

  getLoanSchemes(): Observable<LoanScheme[]> {
    return this.http.get<LoanScheme[]>(`${this.apiUrl}/loan-schemes/all`)
      .pipe(catchError(this.handleError));
  }

  applyForLoan(loan: { amount: number; loanSchemeId: number; customerId: number }): Observable<Loan> {
    return this.http.post<Loan>(`${this.apiUrl}/loans/apply`, loan)
      .pipe(catchError(this.handleError));
  }

  uploadDocument(formData: FormData): Observable<Document> {
    return this.http.post<Document>(`${this.apiUrl}/documents/upload`, formData)
      .pipe(catchError(this.handleError));
  }

  getCustomerLoans(customerId: number): Observable<Loan[]> {
    return this.http.get<Loan[]>(`${this.apiUrl}/loans/getByCustomerId/customer/${customerId}`)
      .pipe(catchError(this.handleError));
  }

  getLoanPayments(loanId: number, status?: string): Observable<LoanPayment[]> {
    const url = status
      ? `${this.apiUrl}/loan-payments/loan/${loanId}?status=${status}`
      : `${this.apiUrl}/loan-payments/loan/${loanId}`;
    return this.http.get<LoanPayment[]>(url)
      .pipe(catchError(this.handleError));
  }

  initiatePayment(loanPaymentId: number): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/loan-payments/${loanPaymentId}/repay`, {})
      .pipe(catchError(this.handleError));
  }

  completePayment(payment: { orderId: string; paymentId: string; signature: string; loanPaymentId: number }): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/loan-payments/complete`, payment)
      .pipe(catchError(this.handleError));
  }

  getProfile(customerId: number): Observable<Profile> {
    return this.http.get<Profile>(`${this.apiUrl}/profile/customer/${customerId}`)
      .pipe(catchError(this.handleError));
  }
}