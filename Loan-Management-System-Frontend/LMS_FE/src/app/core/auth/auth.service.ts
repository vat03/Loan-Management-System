// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError, tap } from 'rxjs/operators';
// import { environment } from '../../../environments/environment';
// import { UserRequestDTO, UserResponseDTO } from '../models/user.model';
// import { ProfileResponseDTO } from '../../admin/models/profile.model';

// interface LoginResponse {
//   token: string;
//   userId: number;
//   username: string;
//   role: string;
//   adminId?: number;
//   customerId?: number;
//   loanOfficerId?: number;
// }

// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//   private apiUrl = environment.apiUrl;

//   constructor(private http: HttpClient) { }

//   registerUser(request: UserRequestDTO, roleName: string): Observable<UserResponseDTO> {
//     const url = `${this.apiUrl}/api/users/registerUser`;
//     console.log('Registering user:', request, 'with role:', roleName, 'to:', url);
//     return this.http.post<UserResponseDTO>(url, { ...request, roleName }).pipe(
//       tap(response => console.log('Registration response:', response)),
//       catchError((error: HttpErrorResponse) => {
//         console.error('Registration failed:', {
//           status: error.status,
//           statusText: error.statusText,
//           message: error.message,
//           url: error.url,
//           errorDetails: error.error?.message || 'Unknown error'
//         });
//         let errorMessage = 'Registration failed. Please try again.';
//         if (error.status === 400) {
//           errorMessage = error.error?.message || 'Invalid input. Check username, email, or password.';
//         } else if (error.status === 409) {
//           errorMessage = 'Username or email already taken.';
//         } else if (error.status === 404) {
//           errorMessage = 'Role not available. Please contact support.';
//         }
//         return throwError(() => new Error(errorMessage));
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
//           if (response.adminId) {
//             localStorage.setItem('adminId', response.adminId.toString());
//           }
//           if (response.customerId) {
//             localStorage.setItem('customerId', response.customerId.toString());
//           }
//           if (response.loanOfficerId) {
//             localStorage.setItem('loanOfficerId', response.loanOfficerId.toString());
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
//     localStorage.removeItem('loanOfficerId');
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

//   getLoanOfficerId(): number | null {
//     const loanOfficerId = localStorage.getItem('loanOfficerId');
//     return loanOfficerId ? +loanOfficerId : null;
//   }

//   isLoggedIn(): boolean {
//     return !!this.getToken();
//   }

//   getUserProfile(): Observable<ProfileResponseDTO> {
//     const userId = this.getUserId();
//     if (!userId) {
//       return throwError(() => new Error('User ID not found. Please log in.'));
//     }
//     const url = `${this.apiUrl}/api/users/profile/${userId}`;
//     return this.http.get<ProfileResponseDTO>(url).pipe(
//       tap(response => console.log('Fetched user profile:', response)),
//       catchError((error: HttpErrorResponse) => {
//         console.error('Failed to fetch user profile:', {
//           status: error.status,
//           statusText: error.statusText,
//           message: error.message,
//           url: error.url,
//           errorDetails: error.error?.message || 'Unknown error'
//         });
//         let errorMessage = 'Failed to load profile. Please try again.';
//         if (error.status === 404) {
//           errorMessage = 'Profile not found.';
//         } else if (error.status === 401) {
//           errorMessage = 'Unauthorized. Please log in again.';
//         }
//         return throwError(() => new Error(errorMessage));
//       })
//     );
//   }
// }



import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { UserRequestDTO, UserResponseDTO } from '../models/user.model';
import { ProfileResponseDTO } from '../../admin/models/profile.model';

interface LoginResponse {
  token: string;
  userId: number;
  username: string;
  role: string;
  adminId?: number;
  customerId?: number;
  loanOfficerId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  registerUser(request: UserRequestDTO, roleName: string): Observable<UserResponseDTO> {
    const url = `${this.apiUrl}/api/users/registerUser`;
    console.log('Registering user:', request, 'with role:', roleName, 'to:', url);
    return this.http.post<UserResponseDTO>(url, { ...request, roleName }).pipe(
      tap(response => console.log('Registration response:', response)),
      catchError((error: HttpErrorResponse) => {
        console.error('Registration failed:', {
          status: error.status,
          statusText: error.statusText,
          message: error.message,
          url: error.url,
          errorDetails: error.error?.message || 'Unknown error'
        });
        let errorMessage = 'Registration failed. Please try again.';
        if (error.status === 400) {
          errorMessage = error.error?.message || 'Invalid input. Check username, email, or password.';
        } else if (error.status === 409) {
          errorMessage = 'Username or email already taken.';
        } else if (error.status === 404) {
          errorMessage = 'Role not available. Please contact support.';
        }
        return throwError(() => new Error(errorMessage));
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
          if (response.loanOfficerId) {
            localStorage.setItem('loanOfficerId', response.loanOfficerId.toString());
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
    localStorage.removeItem('loanOfficerId');
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

  getLoanOfficerId(): number | null {
    const loanOfficerId = localStorage.getItem('loanOfficerId');
    return loanOfficerId ? +loanOfficerId : null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getUserProfile(): Observable<ProfileResponseDTO> {
    const role = this.getRole();
    const userId = this.getUserId();
    if (!role || !userId) {
      return throwError(() => new Error('User not logged in or role not found.'));
    }

    let url: string;
    let id: number | null;

    switch (role) {
      case 'ROLE_ADMIN':
        id = this.getAdminId();
        url = `${this.apiUrl}/api/profile/admin/${id}`;
        break;
      case 'ROLE_LOAN_OFFICER':
        id = this.getLoanOfficerId();
        url = `${this.apiUrl}/api/profile/loan-officer/${id}`;
        break;
      case 'ROLE_CUSTOMER':
        id = this.getCustomerId();
        url = `${this.apiUrl}/api/profile/customer/${id}`;
        break;
      default:
        return throwError(() => new Error('Invalid role: ' + role));
    }

    if (!id) {
      return throwError(() => new Error('Profile ID not found for role: ' + role));
    }

    return this.http.get<ProfileResponseDTO>(url).pipe(
      tap(response => console.log(`Fetched profile for ${role} with ID ${id}:`, response)),
      catchError((error: HttpErrorResponse) => {
        console.error(`Failed to fetch profile for ${role} with ID ${id}:`, {
          status: error.status,
          statusText: error.statusText,
          message: error.message,
          url: error.url,
          errorDetails: error.error?.message || 'Unknown error'
        });
        let errorMessage = 'Failed to load profile. Please try again.';
        if (error.status === 404) {
          errorMessage = 'Profile not found.';
        } else if (error.status === 401) {
          errorMessage = 'Unauthorized. Please log in again.';
        }
        return throwError(() => new Error(errorMessage));
      })
    );
  }
}