// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-profile',
//   standalone: true,
//   imports: [],
//   templateUrl: './profile.component.html',
//   styleUrl: './profile.component.scss'
// })
// export class ProfileComponent {

// }


// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { ProfileService } from '../../core/services/profile.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { ProfileResponseDTO, ProfileUpdateRequestDTO } from '../models/profile.model';
// import { MatCardModule } from '@angular/material/card';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatButtonModule } from '@angular/material/button';
// import { MatSelectModule } from '@angular/material/select';
// import { MatDatepickerModule } from '@angular/material/datepicker';
// import { MatNativeDateModule } from '@angular/material/core';
// import { CommonModule } from '@angular/common';
// import { DatePipe } from '@angular/common';

// @Component({
//   selector: 'app-profile',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatFormFieldModule,
//     MatInputModule,
//     MatButtonModule,
//     MatSelectModule,
//     MatDatepickerModule,
//     MatNativeDateModule
//   ],
//   templateUrl: './profile.component.html',
//   styleUrls: ['./profile.component.scss'],
//   providers: [DatePipe]
// })
// export class ProfileComponent implements OnInit {
//   profile: ProfileResponseDTO | null = null;
//   updateForm: FormGroup;
//   errorMessage: string | null = null;
//   successMessage: string | null = null;

//   constructor(
//     private profileService: ProfileService,
//     private authService: AuthService,
//     private fb: FormBuilder,
//     private datePipe: DatePipe
//   ) {
//     this.updateForm = this.fb.group({
//       password: ['', Validators.required],
//       firstName: ['', Validators.maxLength(50)],
//       lastName: ['', Validators.maxLength(50)],
//       dateOfBirth: [''],
//       mobileNumber: ['', [Validators.pattern('^\\d{10}$')]],
//       gender: ['']
//     });
//   }

//   ngOnInit(): void {
//     const adminId = this.authService.getUserId();
//     if (adminId) {
//       this.loadProfile(adminId);
//     } else {
//       this.errorMessage = 'User not logged in.';
//     }
//   }

//   loadProfile(adminId: number): void {
//     this.profileService.getProfile(adminId).subscribe({
//       next: profile => {
//         this.profile = profile;
//         this.updateForm.patchValue({
//           firstName: profile.firstName,
//           lastName: profile.lastName,
//           dateOfBirth: profile.dateOfBirth ? new Date(profile.dateOfBirth) : '',
//           mobileNumber: profile.mobileNumber,
//           gender: profile.gender
//         });
//       },
//       error: err => {
//         console.error('Fetch profile failed:', err);
//         this.errorMessage = 'Failed to load profile.';
//       }
//     });
//   }

//   updateProfile(): void {
//     if (this.updateForm.invalid) {
//       this.errorMessage = 'Please fill the form correctly.';
//       return;
//     }

//     const adminId = this.authService.getUserId();
//     if (!adminId) {
//       this.errorMessage = 'User not logged in.';
//       return;
//     }

//     const request: ProfileUpdateRequestDTO = {
//       password: this.updateForm.value.password,
//       firstName: this.updateForm.value.firstName || undefined,
//       lastName: this.updateForm.value.lastName || undefined,
//       dateOfBirth: this.updateForm.value.dateOfBirth
//         ? this.datePipe.transform(this.updateForm.value.dateOfBirth, 'dd/MM/yyyy')
//         : undefined,
//       mobileNumber: this.updateForm.value.mobileNumber || undefined,
//       gender: this.updateForm.value.gender || undefined
//     };

//     this.profileService.updateProfile(adminId, request).subscribe({
//       next: profile => {
//         this.profile = profile;
//         this.successMessage = 'Profile updated successfully.';
//         this.errorMessage = null;
//         this.updateForm.patchValue({
//           password: '',
//           firstName: profile.firstName,
//           lastName: profile.lastName,
//           dateOfBirth: profile.dateOfBirth ? new Date(profile.dateOfBirth) : '',
//           mobileNumber: profile.mobileNumber,
//           gender: profile.gender
//         });
//       },
//       error: err => {
//         console.error('Update profile failed:', err);
//         this.errorMessage = err.status === 401 || err.status === 400
//           ? 'Incorrect password or invalid data.'
//           : 'Failed to update profile.';
//         this.successMessage = null;
//       }
//     });
//   }
// }





// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { ProfileService } from '../../core/services/profile.service';
// import { AuthService } from '../../core/auth/auth.service';
// import { ProfileResponseDTO, ProfileUpdateRequestDTO } from '../models/profile.model';
// import { MatCardModule } from '@angular/material/card';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatButtonModule } from '@angular/material/button';
// import { MatSelectModule } from '@angular/material/select';
// import { MatDatepickerModule } from '@angular/material/datepicker';
// import { MatNativeDateModule } from '@angular/material/core';
// import { CommonModule } from '@angular/common';
// import { DatePipe } from '@angular/common';

// @Component({
//   selector: 'app-profile',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     MatCardModule,
//     MatFormFieldModule,
//     MatInputModule,
//     MatButtonModule,
//     MatSelectModule,
//     MatDatepickerModule,
//     MatNativeDateModule
//   ],
//   templateUrl: './profile.component.html',
//   styleUrls: ['./profile.component.scss'],
//   providers: [DatePipe]
// })
// export class ProfileComponent implements OnInit {
//   profile: ProfileResponseDTO | null = null;
//   updateForm: FormGroup;
//   errorMessage: string | null = null;
//   successMessage: string | null = null;

//   constructor(
//     private profileService: ProfileService,
//     private authService: AuthService,
//     private fb: FormBuilder,
//     private datePipe: DatePipe
//   ) {
//     this.updateForm = this.fb.group({
//       password: ['', Validators.required],
//       firstName: ['', Validators.maxLength(50)],
//       lastName: ['', Validators.maxLength(50)],
//       dateOfBirth: [''],
//       mobileNumber: ['', [Validators.pattern('^\\d{10}$')]],
//       gender: ['']
//     });
//   }

//   ngOnInit(): void {
//     const adminId = this.authService.getUserId();
//     if (adminId) {
//       this.loadProfile(adminId);
//     } else {
//       this.errorMessage = 'User not logged in.';
//     }
//   }

//   loadProfile(adminId: number): void {
//     this.profileService.getProfile(adminId).subscribe({
//       next: profile => {
//         this.profile = profile;
//         this.updateForm.patchValue({
//           firstName: profile.firstName,
//           lastName: profile.lastName,
//           dateOfBirth: profile.dateOfBirth ? new Date(profile.dateOfBirth) : '',
//           mobileNumber: profile.mobileNumber,
//           gender: profile.gender
//         });
//       },
//       error: err => {
//         console.error('Fetch profile failed:', err);
//         this.errorMessage = 'Failed to load profile.';
//       }
//     });
//   }

//   updateProfile(): void {
//     if (this.updateForm.invalid) {
//       this.errorMessage = 'Please fill the form correctly.';
//       return;
//     }

//     const adminId = this.authService.getUserId();
//     if (!adminId) {
//       this.errorMessage = 'User not logged in.';
//       return;
//     }

//     const formDateOfBirth = this.updateForm.value.dateOfBirth;
//     const formattedDateOfBirth = formDateOfBirth && formDateOfBirth instanceof Date
//       ? this.datePipe.transform(formDateOfBirth, 'dd/MM/yyyy')
//       : undefined;

//     const request: ProfileUpdateRequestDTO = {
//       password: this.updateForm.value.password,
//       firstName: this.updateForm.value.firstName || undefined,
//       lastName: this.updateForm.value.lastName || undefined,
//       dateOfBirth: formattedDateOfBirth,
//       mobileNumber: this.updateForm.value.mobileNumber || undefined,
//       gender: this.updateForm.value.gender || undefined
//     };

//     this.profileService.updateProfile(adminId, request).subscribe({
//       next: profile => {
//         this.profile = profile;
//         this.successMessage = 'Profile updated successfully.';
//         this.errorMessage = null;
//         this.updateForm.patchValue({
//           password: '',
//           firstName: profile.firstName,
//           lastName: profile.lastName,
//           dateOfBirth: profile.dateOfBirth ? new Date(profile.dateOfBirth) : '',
//           mobileNumber: profile.mobileNumber,
//           gender: profile.gender
//         });
//       },
//       error: err => {
//         console.error('Update profile failed:', err);
//         this.errorMessage = err.status === 401 || err.status === 400
//           ? 'Incorrect password or invalid data.'
//           : 'Failed to update profile.';
//         this.successMessage = null;
//       }
//     });
//   }
// }

















import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ProfileService } from '../../core/services/profile.service';
import { AuthService } from '../../core/auth/auth.service';
import { ProfileResponseDTO, ProfileUpdateRequestDTO } from '../models/profile.model';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CommonModule } from '@angular/common';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [DatePipe]
})
export class ProfileComponent implements OnInit {
  profile: ProfileResponseDTO | null = null;
  updateForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private profileService: ProfileService,
    private authService: AuthService,
    private fb: FormBuilder,
    private datePipe: DatePipe
  ) {
    this.updateForm = this.fb.group({
      password: ['', Validators.required],
      firstName: ['', Validators.maxLength(50)],
      lastName: ['', Validators.maxLength(50)],
      dateOfBirth: [''],
      mobileNumber: ['', [Validators.pattern('^\\d{10}$')]],
      gender: ['']
    });
  }

  ngOnInit(): void {
    const adminId = this.authService.getUserId();
    if (adminId) {
      this.loadProfile(adminId);
    } else {
      this.errorMessage = 'User not logged in.';
    }
  }

  loadProfile(adminId: number): void {
    this.profileService.getProfile(adminId).subscribe({
      next: profile => {
        this.profile = profile;
        this.updateForm.patchValue({
          firstName: profile.firstName,
          lastName: profile.lastName,
          dateOfBirth: profile.dateOfBirth ? new Date(profile.dateOfBirth) : '',
          mobileNumber: profile.mobileNumber,
          gender: profile.gender
        });
      },
      error: err => {
        console.error('Fetch profile failed:', err);
        this.errorMessage = 'Failed to load profile.';
      }
    });
  }

  updateProfile(): void {
    if (this.updateForm.invalid) {
      this.errorMessage = 'Please fill the form correctly.';
      return;
    }

    const adminId = this.authService.getUserId();
    if (!adminId) {
      this.errorMessage = 'User not logged in.';
      return;
    }

    const formDateOfBirth = this.updateForm.value.dateOfBirth;
    const formattedDateOfBirth = formDateOfBirth && formDateOfBirth instanceof Date
      ? this.datePipe.transform(formDateOfBirth, 'yyyy-MM-dd')
      : undefined;

    const request: ProfileUpdateRequestDTO = {
      password: this.updateForm.value.password,
      firstName: this.updateForm.value.firstName || undefined,
      lastName: this.updateForm.value.lastName || undefined,
      dateOfBirth: formattedDateOfBirth,
      mobileNumber: this.updateForm.value.mobileNumber || undefined,
      gender: this.updateForm.value.gender || undefined
    };

    console.log('Form values:', this.updateForm.value); // Debug form values
    this.profileService.updateProfile(adminId, request).subscribe({
      next: profile => {
        this.profile = profile;
        this.successMessage = 'Profile updated successfully.';
        this.errorMessage = null;
        this.updateForm.patchValue({
          password: '',
          firstName: profile.firstName,
          lastName: profile.lastName,
          dateOfBirth: profile.dateOfBirth ? new Date(profile.dateOfBirth) : '',
          mobileNumber: profile.mobileNumber,
          gender: profile.gender
        });
      },
      error: err => {
        console.error('Update profile error:', err);
        this.errorMessage = err.status === 401 || err.status === 400
          ? 'Incorrect password or invalid data.'
          : 'Failed to update profile. Please try again.';
        this.successMessage = null;
      }
    });
  }
}