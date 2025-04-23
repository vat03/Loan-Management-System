// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError, map, tap } from 'rxjs/operators';
// import { Loan, LoanPayment, LoanScheme, Document, DocumentType, Profile } from '../models/customer.model';
// import { AuthService } from '../../core/auth/auth.service';

// @Injectable({
//   providedIn: 'root'
// })
// export class CustomerService {
//   private apiUrl = 'http://localhost:8080/api';

//   constructor(
//     private http: HttpClient,
//     private authService: AuthService
//   ) { }

//   private getAuthHeaders(isFormData: boolean = false): HttpHeaders {
//     const token = this.authService.getToken();
//     let headers = new HttpHeaders();
//     if (token) {
//       headers = headers.set('Authorization', `Bearer ${token}`);
//       console.log('Authorization Header:', headers.get('Authorization')); // Debug token
//     } else {
//       console.warn('No token found in localStorage');
//     }
//     if (!isFormData) {
//       headers = headers.set('Content-Type', 'application/json');
//     }
//     return headers;
//   }

//   getLoanSchemes(): Observable<LoanScheme[]> {
//     return this.http.get<any[]>(`${this.apiUrl}/loan-schemes/all`, { headers: this.getAuthHeaders() }).pipe(
//       map(data => data.map(item => ({
//         id: item.id,
//         name: item.schemeName,
//         interestRate: item.interestRate,
//         tenure: item.tenureMonths,
//         isDeleted: item.deleted ?? item.isDeleted
//       }))),
//       catchError(this.handleError)
//     );
//   }

//   getRequiredDocumentTypes(schemeId: number): Observable<DocumentType[]> {
//     return this.http.get<DocumentType[]>(`${this.apiUrl}/loan-schemes/${schemeId}/document-types`, { headers: this.getAuthHeaders() }).pipe(
//       tap(docs => console.log(`Fetched document types for scheme ${schemeId}:`, docs)),
//       catchError(this.handleError)
//     );
//   }

//   uploadDocument(formData: FormData): Observable<Document> {
//     // Log FormData contents for debugging
//     const formDataEntries: { [key: string]: any } = {};
//     formData.forEach((value, key) => {
//       formDataEntries[key] = value;
//     });
//     console.log('Uploading document with FormData:', formDataEntries);

//     return this.http.post<any>(`${this.apiUrl}/documents/upload`, formData, { headers: this.getAuthHeaders(true) }).pipe(
//       tap(response => console.log('Raw backend response for document upload:', response)), // Log raw response
//       map(data => {
//         if (!data || !data.documentId || !data.documentUrl) {
//           throw new Error('Invalid document response from server');
//         }
//         return {
//           documentId: data.documentId,
//           loanId: null, // Not provided in DocumentResponseDTO
//           documentTypeId: Number(formDataEntries['documentTypeId']), // Use FormData value
//           documentUrl: data.documentUrl,
//           documentName: data.documentName,
//           status: data.status
//         };
//       }),
//       tap(doc => console.log('Mapped uploaded document:', doc)),
//       catchError(this.handleError)
//     );
//   }

//   applyForLoan(loan: any): Observable<Loan> {
//     return this.http.post<Loan>(`${this.apiUrl}/loans/apply`, loan, { headers: this.getAuthHeaders() }).pipe(
//       catchError(this.handleError)
//     );
//   }

//   getCustomerLoans(customerId: number): Observable<Loan[]> {
//     return this.http.get<Loan[]>(`${this.apiUrl}/loans/getByCustomerId/customer/${customerId}`, { headers: this.getAuthHeaders() }).pipe(
//       catchError(this.handleError)
//     );
//   }

//   getLoanPayments(loanId: number, status?: string): Observable<LoanPayment[]> {
//     const url = status
//       ? `${this.apiUrl}/loan-payments/loan/${loanId}?status=${status}`
//       : `${this.apiUrl}/loan-payments/loan/${loanId}`;
//     return this.http.get<LoanPayment[]>(url, { headers: this.getAuthHeaders() }).pipe(
//       catchError(this.handleError)
//     );
//   }

//   getPaymentAmount(paymentId: number): Observable<string> {
//     return this.http.get(`${this.apiUrl}/loan-payments/totalPaymentAmount/${paymentId}/amount`, {
//       headers: this.getAuthHeaders(),
//       responseType: 'text'
//     }).pipe(
//       tap(amount => console.log(`Fetched amount for payment ${paymentId}:`, amount)),
//       catchError(this.handleError)
//     );
//   }

//   initiatePayment(paymentId: number): Observable<string> {
//     return this.http.post(`${this.apiUrl}/loan-payments/${paymentId}/repay`, {}, {
//       headers: this.getAuthHeaders(),
//       responseType: 'text'
//     }).pipe(
//       tap(orderId => console.log(`Initiate Payment Response for payment ${paymentId}:`, orderId)),
//       catchError(this.handleError)
//     );
//   }

//   completePayment(payment: any): Observable<string> {
//     return this.http.post(`${this.apiUrl}/loan-payments/complete`, payment, {
//       headers: this.getAuthHeaders(),
//       responseType: 'text'
//     }).pipe(
//       tap(result => console.log('Complete Payment Response:', result)),
//       catchError(this.handleError)
//     );
//   }

//   getProfile(customerId: number): Observable<Profile> {
//     return this.http.get<Profile>(`${this.apiUrl}/customers/${customerId}/profile`, { headers: this.getAuthHeaders() }).pipe(
//       tap(profile => console.log(`Fetched profile for customer ${customerId}:`, profile)),
//       catchError(this.handleError)
//     );
//   }

//   private handleError(error: HttpErrorResponse): Observable<never> {
//     console.error('API Error:', error);
//     let errorMessage = 'An error occurred';
//     if (error.error instanceof ErrorEvent) {
//       errorMessage = `Client error: ${error.error.message}`;
//     } else {
//       errorMessage = `Server error: ${error.status} - ${error.message}`;
//       if (error.error) {
//         console.log('Error Body:', error.error);
//         errorMessage += `: ${JSON.stringify(error.error)}`;
//       }
//     }
//     return throwError(() => new Error(errorMessage));
//   }
// }


//upar wala best chal rha hai


import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Loan, LoanPayment, LoanScheme, Document, DocumentType, Profile } from '../models/customer.model';
import { AuthService } from '../../core/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  private getAuthHeaders(isFormData: boolean = false): HttpHeaders {
    const token = this.authService.getToken();
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
      console.log('Authorization Header:', headers.get('Authorization')); // Debug token
    } else {
      console.warn('No token found in localStorage');
    }
    if (!isFormData) {
      headers = headers.set('Content-Type', 'application/json');
    }
    return headers;
  }

  getLoanSchemes(): Observable<LoanScheme[]> {
    return this.http.get<any[]>(`${this.apiUrl}/loan-schemes/all`, { headers: this.getAuthHeaders() }).pipe(
      map(data => data.map(item => ({
        id: item.id,
        name: item.schemeName,
        interestRate: item.interestRate,
        tenure: item.tenureMonths,
        isDeleted: item.deleted ?? item.isDeleted
      }))),
      catchError(this.handleError)
    );
  }

  getRequiredDocumentTypes(schemeId: number): Observable<DocumentType[]> {
    return this.http.get<DocumentType[]>(`${this.apiUrl}/loan-schemes/${schemeId}/document-types`, { headers: this.getAuthHeaders() }).pipe(
      tap(docs => console.log(`Fetched document types for scheme ${schemeId}:`, docs)),
      catchError(this.handleError)
    );
  }

  uploadDocument(formData: FormData): Observable<Document> {
    // Log FormData contents for debugging
    const formDataEntries: { [key: string]: any } = {};
    formData.forEach((value, key) => {
      formDataEntries[key] = value;
    });
    console.log('Uploading document with FormData:', formDataEntries);

    return this.http.post<any>(`${this.apiUrl}/documents/upload`, formData, { headers: this.getAuthHeaders(true) }).pipe(
      tap(response => console.log('Raw backend response for document upload:', response)), // Log raw response
      map(data => {
        if (!data || !data.documentId || !data.documentUrl) {
          throw new Error('Invalid document response from server');
        }
        return {
          documentId: data.documentId,
          loanId: null, // Not provided in DocumentResponseDTO
          documentTypeId: Number(formDataEntries['documentTypeId']), // Use FormData value
          documentUrl: data.documentUrl,
          documentName: data.documentName,
          status: data.status
        };
      }),
      tap(doc => console.log('Mapped uploaded document:', doc)),
      catchError(this.handleError)
    );
  }

  updateDocumentLoanIds(documentIds: number[], loanId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/documents/update-loan?loanId=${loanId}`, documentIds, { headers: this.getAuthHeaders() }).pipe(
      tap(() => console.log(`Updated loanId ${loanId} for documentIds:`, documentIds)),
      catchError(this.handleError)
    );
  }

  applyForLoan(loan: any): Observable<Loan> {
    return this.http.post<Loan>(`${this.apiUrl}/loans/apply`, loan, { headers: this.getAuthHeaders() }).pipe(
      catchError(this.handleError)
    );
  }

  getCustomerLoans(customerId: number): Observable<Loan[]> {
    return this.http.get<Loan[]>(`${this.apiUrl}/loans/getByCustomerId/customer/${customerId}`, { headers: this.getAuthHeaders() }).pipe(
      catchError(this.handleError)
    );
  }

  getLoanPayments(loanId: number, status?: string): Observable<LoanPayment[]> {
    const url = status
      ? `${this.apiUrl}/loan-payments/loan/${loanId}?status=${status}`
      : `${this.apiUrl}/loan-payments/loan/${loanId}`;
    return this.http.get<LoanPayment[]>(url, { headers: this.getAuthHeaders() }).pipe(
      catchError(this.handleError)
    );
  }

  getPaymentAmount(paymentId: number): Observable<string> {
    return this.http.get(`${this.apiUrl}/loan-payments/totalPaymentAmount/${paymentId}/amount`, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    }).pipe(
      tap(amount => console.log(`Fetched amount for payment ${paymentId}:`, amount)),
      catchError(this.handleError)
    );
  }

  initiatePayment(paymentId: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/loan-payments/${paymentId}/repay`, {}, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    }).pipe(
      tap(orderId => console.log(`Initiate Payment Response for payment ${paymentId}:`, orderId)),
      catchError(this.handleError)
    );
  }

  completePayment(payment: any): Observable<string> {
    return this.http.post(`${this.apiUrl}/loan-payments/complete`, payment, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    }).pipe(
      tap(result => console.log('Complete Payment Response:', result)),
      catchError(this.handleError)
    );
  }

  getProfile(customerId: number): Observable<Profile> {
    return this.http.get<Profile>(`${this.apiUrl}/customers/${customerId}/profile`, { headers: this.getAuthHeaders() }).pipe(
      tap(profile => console.log(`Fetched profile for customer ${customerId}:`, profile)),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('API Error:', error);
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Client error: ${error.error.message}`;
    } else {
      errorMessage = `Server error: ${error.status} - ${error.message}`;
      if (error.error) {
        console.log('Error Body:', error.error);
        errorMessage += `: ${JSON.stringify(error.error)}`;
      }
    }
    return throwError(() => new Error(errorMessage));
  }
}