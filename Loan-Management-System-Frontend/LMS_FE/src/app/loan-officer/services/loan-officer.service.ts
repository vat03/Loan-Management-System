// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoanOfficerService {

//   constructor() { }
// }

// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { environment } from '../../../environments/environment';
// import { LoanResponseDTO, DocumentResponseDTO, DocumentVerificationDTO, NPAMarkRequest } from '../models/loan-officer.model';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoanOfficerService {
//   private apiUrl = environment.apiUrl;

//   constructor(private http: HttpClient) { }

//   getAssignedLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
//     return this.http.get<LoanResponseDTO[]>(`${this.apiUrl}/api/loans/getByLoanOfficerId/loan-officer/${loanOfficerId}`);
//   }

//   getDocumentsByLoanId(loanId: number): Observable<DocumentResponseDTO[]> {
//     return this.http.get<DocumentResponseDTO[]>(`${this.apiUrl}/api/documents/getByLoanId/loan/${loanId}`);
//   }

//   verifyDocument(documentId: number, request: DocumentVerificationDTO): Observable<DocumentResponseDTO> {
//     return this.http.put<DocumentResponseDTO>(`${this.apiUrl}/api/documents/${documentId}/verify`, request);
//   }

//   markAsNPA(loanId: number, request: NPAMarkRequest): Observable<void> {
//     return this.http.put<void>(`${this.apiUrl}/api/loan-payments/npa/approve/${loanId}?approve=${request.approve}`, {});
//   }
// }


// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable, of } from 'rxjs';
// import { catchError, map } from 'rxjs/operators';
// import { environment } from '../../../environments/environment';
// import { LoanResponseDTO, DocumentResponseDTO, DocumentVerificationDTO, NPAMarkRequest } from '../models/loan-officer.model';

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
//       map(loans => loans.map(loan => loan.loanId)),
//       map(loanIds => {
//         const docs: DocumentResponseDTO[] = [];
//         loanIds.forEach(loanId => {
//           this.getDocumentsByLoanId(loanId).subscribe({
//             next: documents => {
//               docs.push(...documents.filter(doc => doc.status === 'PENDING_VERIFICATION'));
//             }
//           });
//         });
//         return docs;
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




import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, forkJoin } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { LoanResponseDTO, DocumentResponseDTO, DocumentVerificationDTO, NPAMarkRequest } from '../models/loan-officer.model';

@Injectable({
  providedIn: 'root'
})
export class LoanOfficerService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getAssignedLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
    return this.http.get<LoanResponseDTO[]>(`${this.apiUrl}/api/loans/getByLoanOfficerId/loan-officer/${loanOfficerId}`)
      .pipe(catchError(err => {
        console.error('Get assigned loans error:', err);
        return of([]);
      }));
  }

  getNpaLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
    return this.getAssignedLoans(loanOfficerId).pipe(
      map(loans => loans.filter(loan => loan.statusName === 'APPROVED' && loan.isNpa)),
      catchError(err => {
        console.error('Get NPA loans error:', err);
        return of([]);
      })
    );
  }

  getPendingDocuments(loanOfficerId: number): Observable<DocumentResponseDTO[]> {
    return this.getAssignedLoans(loanOfficerId).pipe(
      switchMap(loans => {
        const loanIds = loans.map(loan => loan.loanId);
        if (loanIds.length === 0) {
          return of([]);
        }
        const docObservables = loanIds.map(loanId =>
          this.getDocumentsByLoanId(loanId).pipe(
            map(docs => docs.filter(doc => doc.status === 'PENDING_VERIFICATION'))
          )
        );
        return forkJoin(docObservables).pipe(
          map(docArrays => docArrays.flat())
        );
      }),
      catchError(err => {
        console.error('Get pending documents error:', err);
        return of([]);
      })
    );
  }

  getApprovedLoans(loanOfficerId: number): Observable<LoanResponseDTO[]> {
    return this.getAssignedLoans(loanOfficerId).pipe(
      map(loans => loans.filter(loan => loan.statusName === 'APPROVED' && !loan.isNpa)),
      catchError(err => {
        console.error('Get approved loans error:', err);
        return of([]);
      })
    );
  }

  getDocumentsByLoanId(loanId: number): Observable<DocumentResponseDTO[]> {
    return this.http.get<DocumentResponseDTO[]>(`${this.apiUrl}/api/documents/getByLoanId/loan/${loanId}`)
      .pipe(catchError(err => {
        console.error('Get documents by loan ID error:', err);
        return of([]);
      }));
  }

  verifyDocument(documentId: number, request: DocumentVerificationDTO): Observable<DocumentResponseDTO> {
    return this.http.put<DocumentResponseDTO>(`${this.apiUrl}/api/documents/${documentId}/verify`, request)
      .pipe(catchError(err => {
        console.error('Verify document error:', err);
        throw err;
      }));
  }

  markAsNPA(loanId: number, request: NPAMarkRequest): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/api/loans/${loanId}/npa`, { isNpa: request.approve })
      .pipe(catchError(err => {
        console.error('Mark as NPA error:', err);
        throw err;
      }));
  }
}