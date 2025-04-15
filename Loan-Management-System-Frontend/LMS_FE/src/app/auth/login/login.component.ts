// import { Component } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { AuthService } from '../../core/auth/auth.service';
// import { Router } from '@angular/router';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatButtonModule } from '@angular/material/button';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-login',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatFormFieldModule,
//     MatInputModule,
//     MatButtonModule
//   ],
//   templateUrl: './login.component.html',
//   styleUrls: ['./login.component.scss']
// })
// export class LoginComponent {
//   form: FormGroup;

//   constructor(
//     private fb: FormBuilder,
//     private authService: AuthService,
//     private router: Router
//   ) {
//     this.form = this.fb.group({
//       username: ['', Validators.required],
//       password: ['', Validators.required]
//     });
//   }

//   onSubmit(): void {
//     if (this.form.valid) {
//       const { username, password } = this.form.value;
//       this.authService.login(username, password).subscribe({
//         next: () => {
//           this.router.navigate(['/admin']);
//         },
//         error: err => {
//           console.error('Login failed', err);
//           // Add for debugging
//           console.log('Request body sent:', { username, password });
//         }
//       });
//     }
//   }
// }







import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../core/auth/auth.service';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  form: FormGroup;
  errorMessage: string | null = null; // Add for user feedback

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      const { username, password } = this.form.value;
      this.authService.login(username, password).subscribe({
        next: () => {
          const role = this.authService.getRole();
          console.log('Login - Role:', role); // Debug
          if (role === 'ROLE_ADMIN') {
            this.router.navigate(['/admin']).then(() => console.log('Navigated to /admin'));
          } else if (role === 'ROLE_LOAN_OFFICER') {
            this.router.navigate(['/loan-officer']).then(() => console.log('Navigated to /loan-officer'));
          } else if (role === 'ROLE_CUSTOMER') {
            this.router.navigate(['/customer']).then(() => console.log('Navigated to /customer'));
          } else {
            this.router.navigate(['/']).then(() => console.log('Navigated to /'));
          }
        },
        error: err => {
          console.error('Login failed:', err);
          console.log('Request body sent:', { username, password });
          this.errorMessage = 'Login failed. Please check your credentials.';
        }
      });
    }
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}