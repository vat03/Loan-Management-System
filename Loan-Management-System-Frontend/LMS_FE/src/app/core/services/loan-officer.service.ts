// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class LoanOfficerService {

//   constructor() { }
// }


import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoanOfficerRequest, LoanOfficerResponse } from '../../admin/models/loan-officer.model';

@Injectable({
  providedIn: 'root'
})
export class LoanOfficerService {
  private apiUrl = `${environment.apiUrl}/api/loan-officers`;

  constructor(private http: HttpClient) { }

  addLoanOfficer(adminId: number, request: LoanOfficerRequest): Observable<LoanOfficerResponse> {
    return this.http.post<LoanOfficerResponse>(`${this.apiUrl}/addLoanOfficer?adminId=${adminId}`, request);
  }

  getLoanOfficerById(id: number): Observable<LoanOfficerResponse> {
    return this.http.get<LoanOfficerResponse>(`${this.apiUrl}/getLoanOfficerById/${id}`);
  }

  getAllLoanOfficers(): Observable<LoanOfficerResponse[]> {
    return this.http.get<LoanOfficerResponse[]>(this.apiUrl);
  }
}