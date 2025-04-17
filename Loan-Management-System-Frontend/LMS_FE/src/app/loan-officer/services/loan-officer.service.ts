// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable, of, forkJoin } from 'rxjs';
// import { catchError, map, switchMap } from 'rxjs/operators';
// import { environment } from '../../../environments/environment';
// import { LoanResponseDTO, DocumentResponseDTO, DocumentVerificationDTO, NPAMarkRequest, CustomerResponseDTO } from '../models/loan-officer.model';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoanOfficerService {
//   private apiUrl = environment.apiUrl;

//   constructor(private http: HttpClient) { }

//   getAssignedLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
//     return this.http.get<LoanResponseDTO[]>(`${this.apiUrl}/api/loans/getByLoanOfficerId/loan-officer/${loanOfficerId}`)
//       .pipe(catchError(err => {
//         console.error('Get assigned loans error:', err);
//         return of([]);
//       }));
//   }

//   getNpaLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
//     return this.getAssignedLoans(loanOfficerId).pipe(
//       map(loans => loans.filter(loan => loan.statusName === 'APPROVED' && loan.isNpa)),
//       catchError(err => {
//         console.error('Get NPA loans error:', err);
//         return of([]);
//       })
//     );
//   }

//   getPendingDocuments(loanOfficerId: number): Observable<DocumentResponseDTO[]> {
//     return this.getAssignedLoans(loanOfficerId).pipe(
//       switchMap(loans => {
//         const loanIds = loans.map(loan => loan.loanId);
//         if (loanIds.length === 0) {
//           return of([]);
//         }
//         const docObservables = loanIds.map(loanId =>
//           this.getDocumentsByLoanId(loanId).pipe(
//             map(docs => docs.filter(doc => doc.status === 'PENDING_VERIFICATION'))
//           )
//         );
//         return forkJoin(docObservables).pipe(
//           map(docArrays => docArrays.flat())
//         );
//       }),
//       catchError(err => {
//         console.error('Get pending documents error:', err);
//         return of([]);
//       })
//     );
//   }

//   getApprovedLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
//     return this.getAssignedLoans(loanOfficerId).pipe(
//       map(loans => loans.filter(loan => loan.statusName === 'APPROVED' && !loan.isNpa)),
//       catchError(err => {
//         console.error('Get approved loans error:', err);
//         return of([]);
//       })
//     );
//   }

//   getCustomersByLoanOfficerId(loanOfficerId: number): Observable<CustomerResponseDTO[]> {
//     return this.http.get<CustomerResponseDTO[]>(`${this.apiUrl}/api/customers/getCustomerByLoanOfficerId/loan-officer/${loanOfficerId}`)
//       .pipe(
//         map(customers => customers.filter(customer => !customer.isDeleted)),
//         catchError(err => {
//           console.error('Get customers by loan officer ID error:', err);
//           return of([]);
//         })
//       );
//   }

//   getDocumentsByLoanId(loanId: number): Observable<DocumentResponseDTO[]> {
//     return this.http.get<DocumentResponseDTO[]>(`${this.apiUrl}/api/documents/getByLoanId/loan/${loanId}`)
//       .pipe(catchError(err => {
//         console.error('Get documents by loan ID error:', err);
//         return of([]);
//       }));
//   }

//   verifyDocument(documentId: number, request: DocumentVerificationDTO): Observable<DocumentResponseDTO> {
//     return this.http.put<DocumentResponseDTO>(`${this.apiUrl}/api/documents/${documentId}/verify`, request)
//       .pipe(catchError(err => {
//         console.error('Verify document error:', err);
//         throw err;
//       }));
//   }

//   markAsNPA(loanId: number, request: NPAMarkRequest): Observable<void> {
//     return this.http.put<void>(`${this.apiUrl}/api/loans/${loanId}/npa`, { isNpa: request.approve })
//       .pipe(catchError(err => {
//         console.error('Mark as NPA error:', err);
//         throw err;
//       }));
//   }
// }








// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError, tap } from 'rxjs/operators';
// import { LoanResponseDTO, DocumentResponseDTO, CustomerResponseDTO } from '../models/loan-officer.model';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoanOfficerService {
//   private apiUrl = 'http://localhost:8080/api';

//   constructor(private http: HttpClient) { }

//   getAssignedLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
//     return this.http.get<LoanResponseDTO[]>(`${this.apiUrl}/loans/getByLoanOfficerId/loan-officer/${loanOfficerId}`).pipe(
//       catchError(this.handleError)
//     );
//   }

//   getNpaLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
//     return this.http.get<LoanResponseDTO[]>(`${this.apiUrl}/loans/getByLoanOfficerId/loan-officer/${loanOfficerId}?isNpa=true`).pipe(
//       catchError(this.handleError)
//     );
//   }

//   getPendingDocuments(loanOfficerId: number): Observable<DocumentResponseDTO[]> {
//     return this.http.get<DocumentResponseDTO[]>(`${this.apiUrl}/documents/getByLoanOfficerId/loan-officer/${loanOfficerId}?status=PENDING_VERIFICATION`).pipe(
//       tap(docs => console.log('Fetched pending documents:', docs)),
//       catchError(this.handleError)
//     );
//   }

//   getApprovedLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
//     return this.http.get<LoanResponseDTO[]>(`${this.apiUrl}/loans/getByLoanOfficerId/loan-officer/${loanOfficerId}?status=APPROVED`).pipe(
//       catchError(this.handleError)
//     );
//   }

//   getCustomersByLoanOfficerId(loanOfficerId: number): Observable<CustomerResponseDTO[]> {
//     return this.http.get<CustomerResponseDTO[]>(`${this.apiUrl}/customers/getByLoanOfficerId/loan-officer/${loanOfficerId}`).pipe(
//       catchError(this.handleError)
//     );
//   }

//   verifyDocument(documentId: number, verification: { status: string }): Observable<DocumentResponseDTO> {
//     return this.http.put<DocumentResponseDTO>(`${this.apiUrl}/documents/${documentId}/verify`, verification).pipe(
//       tap(doc => console.log(`Verified document ${documentId}:`, doc)),
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
//       }
//     }
//     return throwError(() => new Error(errorMessage));
//   }
// }




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
    return this.http.get<DocumentResponseDTO[]>(`${this.apiUrl}/documents/loan/${loanId}`).pipe(
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
    return this.http.get<CustomerResponseDTO[]>(`${this.apiUrl}/customers/getByLoanOfficerId/loan-officer/${loanOfficerId}`).pipe(
      catchError(this.handleError)
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
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Client error: ${error.error.message}`;
    } else {
      errorMessage = `Server error: ${error.status} - ${error.message}`;
      if (error.error) {
        console.log('Error Body:', error.error);
      }
    }
    return throwError(() => new Error(errorMessage));
  }
}