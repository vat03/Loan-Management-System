// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoanSchemeService {

//   constructor() { }
// }

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  LoanSchemeRequest,
  LoanSchemeResponse,
  LoanSchemeUpdate,
  LoanSchemeSoftDelete
} from '../../admin/models/loan-scheme.model';

@Injectable({
  providedIn: 'root'
})
export class LoanSchemeService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  createLoanScheme(adminId: number, request: LoanSchemeRequest): Observable<LoanSchemeResponse> {
    return this.http.post<LoanSchemeResponse>(
      `${this.apiUrl}/api/loan-schemes/create?adminId=${adminId}`,
      request
    );
  }

  updateLoanScheme(id: number, adminId: number, request: LoanSchemeUpdate): Observable<LoanSchemeResponse> {
    return this.http.put<LoanSchemeResponse>(
      `${this.apiUrl}/api/loan-schemes/${id}?adminId=${adminId}`,
      request
    );
  }

  softDeleteLoanScheme(id: number, request: LoanSchemeSoftDelete): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/api/admin/loan-schemes/${id}/soft-delete`, request);
  }

  getAllLoanSchemes(): Observable<LoanSchemeResponse[]> {
    return this.http.get<LoanSchemeResponse[]>(`${this.apiUrl}/api/admin/loan-schemes`);
  }
}