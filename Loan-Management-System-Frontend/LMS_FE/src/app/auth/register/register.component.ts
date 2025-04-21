// import { Component } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { AuthService } from '../../core/auth/auth.service';
// import { Router, RouterModule } from '@angular/router';
// import { MatCardModule } from '@angular/material/card';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatButtonModule } from '@angular/material/button';
// import { MatSelectModule } from '@angular/material/select';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-register',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatFormFieldModule,
//     MatInputModule,
//     MatButtonModule,
//     MatSelectModule,
//     RouterModule
//   ],
//   templateUrl: './register.component.html',
//   styleUrls: ['./register.component.scss']
// })
// export class RegisterComponent {
//   registerForm: FormGroup;
//   errorMessage: string | null = null;
//   successMessage: string | null = null;

//   roles = ['ADMIN', 'CUSTOMER'];

//   constructor(
//     private fb: FormBuilder,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.registerForm = this.fb.group({
//       username: ['', [
//         Validators.required,
//         Validators.minLength(3),
//         Validators.maxLength(50)
//       ]],
//       email: ['', [
//         Validators.required,
//         Validators.email,
//         Validators.maxLength(100)
//       ]],
//       password: ['', [
//         Validators.required,
//         Validators.minLength(8),
//         Validators.maxLength(100),
//         Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/)
//       ]],
//       roleName: ['', Validators.required]
//     });
//   }

//   register(): void {
//     if (this.registerForm.invalid) {
//       this.errorMessage = 'Please fill the form correctly.';
//       return;
//     }

//     const { username, email, password, roleName } = this.registerForm.value;
//     console.log('Form values:', { username, email, password, roleName });

//     this.authService.registerUser({ username, email, password }, roleName).subscribe({
//       next: response => {
//         this.successMessage = 'Registration successful! Redirecting to login...';
//         this.errorMessage = null;
//         console.log('Registration success:', response);
//         setTimeout(() => {
//           this.router.navigate(['/login']);
//         }, 2000);
//       },
//       error: err => {
//         console.error('Registration error:', err);
//         this.errorMessage = err.status === 400
//           ? 'Invalid input. Check username, email, or password.'
//           : err.status === 404
//             ? 'Selected role is not available. Please choose another role.'
//             : err.status === 409
//               ? 'Username or email already taken.'
//               : err.status === 0
//                 ? 'Cannot connect to server. Please ensure the backend is running.'
//                 : 'Registration failed. Please try again.';
//         this.successMessage = null;
//       }
//     });
//   }
// }





// import { Component } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { AuthService } from '../../core/auth/auth.service';
// import { Router, RouterModule } from '@angular/router';
// import { MatCardModule } from '@angular/material/card';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatButtonModule } from '@angular/material/button';
// import { MatSelectModule } from '@angular/material/select';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-register',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatFormFieldModule,
//     MatInputModule,
//     MatButtonModule,
//     MatSelectModule,
//     RouterModule
//   ],
//   templateUrl: './register.component.html',
//   styleUrls: ['./register.component.scss']
// })
// export class RegisterComponent {
//   registerForm: FormGroup;
//   errorMessage: string | null = null;
//   successMessage: string | null = null;

//   constructor(
//     private fb: FormBuilder,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.registerForm = this.fb.group({
//       username: ['', [
//         Validators.required,
//         Validators.minLength(3),
//         Validators.maxLength(50)
//       ]],
//       email: ['', [
//         Validators.required,
//         Validators.email,
//         Validators.maxLength(100)
//       ]],
//       password: ['', [
//         Validators.required,
//         Validators.minLength(8),
//         Validators.maxLength(100),
//         Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/)
//       ]]
//     });
//   }

//   register(): void {
//     if (this.registerForm.invalid) {
//       this.errorMessage = 'Please fill the form correctly.';
//       return;
//     }

//     const { username, email, password } = this.registerForm.value;
//     const roleName = 'CUSTOMER'; // Hardcoded role

//     this.authService.registerUser({ username, email, password }, roleName).subscribe({
//       next: response => {
//         this.successMessage = 'Registration successful! Redirecting to login...';
//         this.errorMessage = null;
//         console.log('Registration success:', response);
//         setTimeout(() => {
//           this.router.navigate(['/login']);
//         }, 2000);
//       },
//       error: err => {
//         console.error('Registration error:', err);
//         this.errorMessage = err.status === 400
//           ? 'Invalid input. Check username, email, or password.'
//           : err.status === 404
//             ? 'Role not available. Please contact support.'
//             : err.status === 409
//               ? 'Username or email already taken.'
//               : err.status === 0
//                 ? 'Cannot connect to server. Please ensure the backend is running.'
//                 : 'Registration failed. Please try again.';
//         this.successMessage = null;
//       }
//     });
//   }
// }




import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../core/auth/auth.service';
import { Router, RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    RouterModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50)
      ]],
      email: ['', [
        Validators.required,
        Validators.email,
        Validators.maxLength(100)
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(100),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/)
      ]]
    });
  }

  register(): void {
    if (this.registerForm.invalid) {
      this.errorMessage = 'Please fill the form correctly.';
      this.successMessage = null;
      return;
    }

    const { username, email, password } = this.registerForm.value;
    const roleName = 'CUSTOMER';

    this.authService.registerUser({ username, email, password }, roleName).subscribe({
      next: response => {
        this.successMessage = 'Registration successful! Redirecting to login...';
        this.errorMessage = null;
        console.log('Registration success:', response);
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: err => {
        console.error('Registration error:', err);
        this.errorMessage = err.message;
        this.successMessage = null;
      }
    });
  }
}