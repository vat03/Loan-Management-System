import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoanOfficerRequest, LoanOfficerResponse } from '../../admin/models/loan-officer.model';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoanOfficerService {
  private apiUrl = `${environment.apiUrl}/api/loan-officers`;

  constructor(private http: HttpClient) { }

  // TODO: Backend endpoint POST http://localhost:8080/api/loan-officers/addLoanOfficer?adminId=<id>
  // Body: { "email": "string", "username": "string", "password": "string" }
  // Response: { "id": number, "username": "string", "email": "string", "adminId": number, "customerIds": number[] }
  addLoanOfficer(adminId: number, request: LoanOfficerRequest): Observable<LoanOfficerResponse> {
    const url = `${this.apiUrl}/addLoanOfficer?adminId=${adminId}`;
    console.log('Adding loan officer:', request, 'to:', url);
    return this.http.post<LoanOfficerResponse>(url, request).pipe(
      tap(response => console.log('Loan officer added:', response)),
      tap({ error: err => console.error('Add loan officer failed:', err) })
    );
  }

  // TODO: Backend endpoint GET http://localhost:8080/api/loan-officers/getLoanOfficerById/{id}
  // Response: { "id": number, "username": "string", "email": "string", "adminId": number, "customerIds": number[] }
  getLoanOfficerById(id: number): Observable<LoanOfficerResponse> {
    const url = `${this.apiUrl}/getLoanOfficerById/${id}`;
    console.log('Fetching loan officer:', url);
    return this.http.get<LoanOfficerResponse>(url).pipe(
      tap(response => console.log('Loan officer response:', response)),
      tap({ error: err => console.error('Fetch loan officer failed:', err) })
    );
  }

  // TODO: Backend endpoint GET http://localhost:8080/api/loan-officers/getAllLoanOfficers
  // Response: [{ "id": number, "username": "string", "email": "string", "adminId": number, "customerIds": number[] }, ...]
  getAllLoanOfficers(): Observable<LoanOfficerResponse[]> {
    const url = `${this.apiUrl}/getAllLoanOfficers`;
    console.log('Fetching loan officers from:', url);
    return this.http.get<LoanOfficerResponse[]>(url).pipe(
      tap(data => console.log('Loan officers response:', data)),
      tap({ error: err => console.error('Fetch loan officers failed:', err) })
    );
  }
}