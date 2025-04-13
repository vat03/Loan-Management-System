// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable, tap } from 'rxjs';
// import { environment } from '../../../environments/environment';

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
//     console.log('Registering user:', request, 'with role:', roleName);
//     return this.http.post<UserResponseDTO>(url, request, { params }).pipe(
//       tap(response => console.log('Registration response:', response)),
//       tap({ error: err => console.error('Registration failed:', err) })
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
          url: err.url
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
        })
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('userId');
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

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}