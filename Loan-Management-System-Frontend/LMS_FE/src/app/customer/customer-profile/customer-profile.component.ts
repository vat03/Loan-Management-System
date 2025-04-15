// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-customer-profile',
//   standalone: true,
//   imports: [],
//   templateUrl: './customer-profile.component.html',
//   styleUrl: './customer-profile.component.scss'
// })
// export class CustomerProfileComponent {

// }


import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ProfileService } from '../../core/services/profile.service';
import { AuthService } from '../../core/auth/auth.service';
// import { ProfileResponseDTO, ProfileUpdateRequestDTO } from '../models/profile.model';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CommonModule } from '@angular/common';
import { DatePipe } from '@angular/common';
import { HeaderComponent } from '../../shared/components/header/header.component';
import { ProfileResponseDTO, ProfileUpdateRequestDTO } from '../../admin/models/profile.model';

@Component({
  selector: 'app-customer-profile',
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
    MatNativeDateModule,
    HeaderComponent
  ],
  templateUrl: './customer-profile.component.html',
  styleUrls: ['./customer-profile.component.scss'],
  providers: [DatePipe]
})
export class CustomerProfileComponent implements OnInit {
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
    const customerId = this.authService.getCustomerId();
    const role = this.authService.getRole();
    if (role !== 'ROLE_CUSTOMER' || !customerId) {
      this.errorMessage = 'Please log in as a customer to view your profile.';
      return;
    }

    this.loadCustomerProfile(customerId);
  }

  loadCustomerProfile(customerId: number): void {
    this.profileService.getCustomerProfile(customerId).subscribe({
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
        console.error('Fetch customer profile failed:', err);
        this.errorMessage = 'Failed to load profile. Please try again.';
      }
    });
  }

  updateProfile(): void {
    if (this.updateForm.invalid) {
      this.errorMessage = 'Please fill the form correctly.';
      return;
    }

    const customerId = this.authService.getCustomerId();
    if (!customerId) {
      this.errorMessage = 'Please log in to update your profile.';
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

    console.log('Updating profile with:', request);
    this.profileService.updateProfile(customerId, request, 'ROLE_CUSTOMER').subscribe({
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