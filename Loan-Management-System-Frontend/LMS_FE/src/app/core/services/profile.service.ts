// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class ProfileService {

//   constructor() { }
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
    console.log('Fetching profile from:', url);
    return this.http.get<ProfileResponseDTO>(url).pipe(
      tap(response => console.log('Profile response:', response)),
      tap({ error: err => console.error('Fetch profile failed:', err) })
    );
  }

  updateProfile(adminId: number, request: ProfileUpdateRequestDTO): Observable<ProfileResponseDTO> {
    const url = `${this.apiUrl}/update/admin/${adminId}`;
    console.log('Updating profile:', request, 'to:', url);
    return this.http.put<ProfileResponseDTO>(url, request).pipe(
      tap(response => console.log('Profile updated:', response)),
      tap({ error: err => console.error('Update profile failed:', err) })
    );
  }
}