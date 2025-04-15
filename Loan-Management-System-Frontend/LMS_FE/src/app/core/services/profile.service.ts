// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class ProfileService {

//   constructor() { }
// }

// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { environment } from '../../../environments/environment';
// import { ProfileResponseDTO, ProfileUpdateRequestDTO } from '../../admin/models/profile.model';
// import { tap } from 'rxjs/operators';

// @Injectable({
//   providedIn: 'root'
// })
// export class ProfileService {
//   private apiUrl = `${environment.apiUrl}/api/profile`;

//   constructor(private http: HttpClient) { }

//   getProfile(adminId: number): Observable<ProfileResponseDTO> {
//     const url = `${this.apiUrl}/admin/${adminId}`;
//     console.log('Fetching profile from:', url);
//     return this.http.get<ProfileResponseDTO>(url).pipe(
//       tap(response => console.log('Profile response:', response)),
//       tap({ error: err => console.error('Fetch profile failed:', err) })
//     );
//   }

//   updateProfile(adminId: number, request: ProfileUpdateRequestDTO): Observable<ProfileResponseDTO> {
//     const url = `${this.apiUrl}/update/admin/${adminId}`;
//     console.log('Updating profile:', request, 'to:', url);
//     return this.http.put<ProfileResponseDTO>(url, request).pipe(
//       tap(response => console.log('Profile updated:', response)),
//       tap({ error: err => console.error('Update profile failed:', err) })
//     );
//   }
// }






// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { environment } from '../../../environments/environment';
// import { ProfileResponseDTO, ProfileUpdateRequestDTO } from '../../admin/models/profile.model';
// import { tap } from 'rxjs/operators';

// @Injectable({
//   providedIn: 'root'
// })
// export class ProfileService {
//   private apiUrl = `${environment.apiUrl}/api/profile`;

//   constructor(private http: HttpClient) { }

//   getProfile(adminId: number): Observable<ProfileResponseDTO> {
//     const url = `${this.apiUrl}/admin/${adminId}`;
//     console.log('Fetching admin profile from:', url);
//     return this.http.get<ProfileResponseDTO>(url).pipe(
//       tap(response => console.log('Admin profile response:', response)),
//       tap({ error: err => console.error('Fetch admin profile failed:', err) })
//     );
//   }

//   getCustomerProfile(customerId: number): Observable<ProfileResponseDTO> {
//     const url = `${this.apiUrl}/customer/${customerId}`;
//     console.log('Fetching customer profile from:', url);
//     return this.http.get<ProfileResponseDTO>(url).pipe(
//       tap(response => console.log('Customer profile response:', response)),
//       tap({ error: err => console.error('Fetch customer profile failed:', err) })
//     );
//   }

//   updateProfile(id: number, request: ProfileUpdateRequestDTO, role: string): Observable<ProfileResponseDTO> {
//     const endpoint = role === 'ROLE_ADMIN' ? 'admin' : 'customer';
//     const url = `${this.apiUrl}/update/${endpoint}/${id}`;
//     console.log('Updating profile:', request, 'to:', url);
//     return this.http.put<ProfileResponseDTO>(url, request).pipe(
//       tap(response => console.log('Profile updated:', response)),
//       tap({ error: err => console.error('Update profile failed:', err) })
//     );
//   }
// }



import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProfileResponseDTO, ProfileUpdateRequestDTO } from '../../admin/models/profile.model';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = `${environment.apiUrl}/api/profile`;

  constructor(private http: HttpClient) { }

  getProfile(adminId: number): Observable<ProfileResponseDTO> {
    const url = `${this.apiUrl}/admin/${adminId}`;
    console.log('Fetching admin profile from:', url);
    return this.http.get<ProfileResponseDTO>(url).pipe(
      tap(response => console.log('Admin profile response:', response)),
      tap({ error: err => console.error('Fetch admin profile failed:', err) })
    );
  }

  getLoanOfficerProfile(loanOfficerId: number): Observable<ProfileResponseDTO> {
    const url = `${this.apiUrl}/loan-officer/${loanOfficerId}`;
    console.log("Loan officer:"+url);
    
    console.log('Fetching Loan Officer profile from:', url);
    return this.http.get<ProfileResponseDTO>(url).pipe(
      tap(response => console.log('Loan Officer profile response:', response)),
      tap({ error: err => console.error('Fetch loan officer profile failed:', err) })
    );
  }

  getCustomerProfile(customerId: number): Observable<ProfileResponseDTO> {
    const url = `${this.apiUrl}/customer/${customerId}`;
    console.log('Fetching customer profile from:', url);
    return this.http.get<ProfileResponseDTO>(url).pipe(
      tap(response => console.log('Customer profile response:', response)),
      tap({ error: err => console.error('Fetch customer profile failed:', err) })
    );
  }

  updateProfile(id: number, request: ProfileUpdateRequestDTO, role: string | null): Observable<ProfileResponseDTO> {
    const endpoint = role === 'ROLE_ADMIN' ? 'admin' : role === 'ROLE_CUSTOMER' ? 'customer' : role === 'ROLE_LOAN_OFFICER' ? 'loan-officer' : null;
    if (!endpoint) {
      throw new Error('Invalid role for profile update');
    }
    const url = `${this.apiUrl}/update/${endpoint}/${id}`;
    console.log('Updating profile:', request, 'to:', url);
    return this.http.put<ProfileResponseDTO>(url, request).pipe(
      tap(response => console.log('Profile updated:', response)),
      tap({ error: err => console.error('Update profile failed:', err) })
    );
  }
}