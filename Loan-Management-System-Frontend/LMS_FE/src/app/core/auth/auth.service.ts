// import { Injectable } from '@angular/core';
// import { HttpClient, HttpParams } from '@angular/common/http';
// import { Observable, tap } from 'rxjs';
// import { environment } from '../../../environments/environment';
// import { UserRequestDTO, UserResponseDTO } from '../models/user.model';

// interface LoginResponse {
//   token: string;
//   userId: number;
//   username: string;
//   role: string;
// }

// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//   private apiUrl = environment.apiUrl;

//   constructor(private http: HttpClient) { }

//   registerUser(request: UserRequestDTO, roleName: string): Observable<UserResponseDTO> {
//     const url = `${this.apiUrl}/api/users/registerUser`;
//     const params = new HttpParams().set('roleName', roleName);
//     console.log('Registering user:', request, 'with role:', roleName, 'to:', url);
//     return this.http.post<UserResponseDTO>(url, request, { params }).pipe(
//       tap(response => console.log('Registration response:', response)),
//       tap({
//         error: err => console.error('Registration failed:', {
//           status: err.status,
//           statusText: err.statusText,
//           message: err.message,
//           url: err.url
//         })
//       })
//     );
//   }

//   login(username: string, password: string): Observable<LoginResponse> {
//     return this.http
//       .post<LoginResponse>(`${this.apiUrl}/api/users/login`, { username, password })
//       .pipe(
//         tap(response => {
//           localStorage.setItem('token', response.token);
//           localStorage.setItem('role', response.role);
//           localStorage.setItem('userId', response.userId.toString());
//         })
//       );
//   }

//   logout(): void {
//     localStorage.removeItem('token');
//     localStorage.removeItem('role');
//     localStorage.removeItem('userId');
//   }

//   getToken(): string | null {
//     return localStorage.getItem('token');
//   }

//   getRole(): string | null {
//     return localStorage.getItem('role');
//   }

//   getUserId(): number | null {
//     const userId = localStorage.getItem('userId');
//     return userId ? +userId : null;
//   }

//   isLoggedIn(): boolean {
//     return !!this.getToken();
//   }
// }






// import { Injectable } from '@angular/core';
// import { HttpClient, HttpParams } from '@angular/common/http';
// import { Observable, tap } from 'rxjs';
// import { environment } from '../../../environments/environment';
// import { UserRequestDTO, UserResponseDTO } from '../models/user.model';

// interface LoginResponse {
//   token: string;
//   userId: number;
//   username: string;
//   role: string;
// }

// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//   private apiUrl = environment.apiUrl;

//   constructor(private http: HttpClient) { }

//   registerUser(request: UserRequestDTO, roleName: string): Observable<UserResponseDTO> {
//     const url = `${this.apiUrl}/api/users/registerUser`;
//     const params = new HttpParams().set('roleName', roleName);
//     console.log('Registering user:', request, 'with role:', roleName, 'to:', url);
//     return this.http.post<UserResponseDTO>(url, request, { params }).pipe(
//       tap(response => console.log('Registration response:', response)),
//       tap({
//         error: err => console.error('Registration failed:', {
//           status: err.status,
//           statusText: err.statusText,
//           message: err.message,
//           url: err.url,
//           errorDetails: err.error
//         })
//       })
//     );
//   }

//   login(username: string, password: string): Observable<LoginResponse> {
//     return this.http
//       .post<LoginResponse>(`${this.apiUrl}/api/users/login`, { username, password })
//       .pipe(
//         tap(response => {
//           localStorage.setItem('token', response.token);
//           localStorage.setItem('role', response.role);
//           localStorage.setItem('userId', response.userId.toString());
//         })
//       );
//   }

//   logout(): void {
//     localStorage.removeItem('token');
//     localStorage.removeItem('role');
//     localStorage.removeItem('userId');
//   }

//   getToken(): string | null {
//     return localStorage.getItem('token');
//   }

//   getRole(): string | null {
//     return localStorage.getItem('role');
//   }

//   getUserId(): number | null {
//     const userId = localStorage.getItem('userId');
//     return userId ? +userId : null;
//   }

//   isLoggedIn(): boolean {
//     return !!this.getToken();
//   }
// }






import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { UserRequestDTO, UserResponseDTO } from '../models/user.model';

interface LoginResponse {
  token: string;
  userId: number;
  username: string;
  role: string;
  adminId?: number;
  customerId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  registerUser(request: UserRequestDTO, roleName: string): Observable<UserResponseDTO> {
    const url = `${this.apiUrl}/api/users/registerUser`;
    const params = new HttpParams().set('roleName', roleName);
    console.log('Registering user:', request, 'with role:', roleName, 'to:', url);
    return this.http.post<UserResponseDTO>(url, request, { params }).pipe(
      tap(response => console.log('Registration response:', response)),
      tap({
        error: err => console.error('Registration failed:', {
          status: err.status,
          statusText: err.statusText,
          message: err.message,
          url: err.url,
          errorDetails: err.error
        })
      })
    );
  }

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.apiUrl}/api/users/login`, { username, password })
      .pipe(
        tap(response => {
          localStorage.setItem('token', response.token);
          localStorage.setItem('role', response.role);
          localStorage.setItem('userId', response.userId.toString());
          if (response.adminId) {
            localStorage.setItem('adminId', response.adminId.toString());
          }
          if (response.customerId) {
            localStorage.setItem('customerId', response.customerId.toString());
          }
        })
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('userId');
    localStorage.removeItem('adminId');
    localStorage.removeItem('customerId');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  getUserId(): number | null {
    const userId = localStorage.getItem('userId');
    return userId ? +userId : null;
  }

  getAdminId(): number | null {
    const adminId = localStorage.getItem('adminId');
    return adminId ? +adminId : null;
  }

  getCustomerId(): number | null {
    const customerId = localStorage.getItem('customerId');
    return customerId ? +customerId : null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}


// import { Injectable } from '@angular/core';
// import { HttpClient, HttpParams } from '@angular/common/http';
// import { Observable, tap } from 'rxjs';
// import { environment } from '../../../environments/environment';
// import { UserRequestDTO, UserResponseDTO } from '../models/user.model';

// interface LoginResponse {
//   token: string;
//   userId: number;
//   username: string;
//   role: string;
//   adminId?: number;
//   customerId?: number;
// }

// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//   private apiUrl = environment.apiUrl;

//   constructor(private http: HttpClient) { }

//   registerUser(request: UserRequestDTO, roleName: string): Observable<UserResponseDTO> {
//     const url = `${this.apiUrl}/api/users/registerUser`;
//     const params = new HttpParams().set('roleName', roleName);
//     console.log('Registering user:', request, 'with role:', roleName, 'to:', url);
//     return this.http.post<UserResponseDTO>(url, request, { params }).pipe(
//       tap(response => console.log('Registration response:', response)),
//       tap({
//         error: err => console.error('Registration failed:', {
//           status: err.status,
//           statusText: err.statusText,
//           message: err.message,
//           url: err.url,
//           errorDetails: err.error
//         })
//       })
//     );
//   }

//   login(username: string, password: string): Observable<LoginResponse> {
//     return this.http
//       .post<LoginResponse>(`${this.apiUrl}/api/users/login`, { username, password })
//       .pipe(
//         tap(response => {
//           if (response.token) {
//             localStorage.setItem('token', response.token);
//           } else {
//             console.error('Login response missing token');
//           }
//           if (response.role) {
//             localStorage.setItem('role', response.role);
//           } else {
//             console.error('Login response missing role');
//           }
//           localStorage.setItem('userId', response.userId.toString());
//           if (response.adminId) {
//             localStorage.setItem('adminId', response.adminId.toString());
//           }
//           if (response.customerId) {
//             localStorage.setItem('customerId', response.customerId.toString());
//           }
//         })
//       );
//   }

//   logout(): void {
//     localStorage.removeItem('token');
//     localStorage.removeItem('role');
//     localStorage.removeItem('userId');
//     localStorage.removeItem('adminId');
//     localStorage.removeItem('customerId');
//   }

//   getToken(): string | null {
//     return localStorage.getItem('token');
//   }

//   getRole(): string | null {
//     return localStorage.getItem('role');
//   }

//   getUserId(): number | null {
//     const userId = localStorage.getItem('userId');
//     return userId ? +userId : null;
//   }

//   getAdminId(): number | null {
//     const adminId = localStorage.getItem('adminId');
//     return adminId ? +adminId : null;
//   }

//   getCustomerId(): number | null {
//     const customerId = localStorage.getItem('customerId');
//     return customerId ? +customerId : null;
//   }

//   isLoggedIn(): boolean {
//     return !!this.getToken();
//   }
// }