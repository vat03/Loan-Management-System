//package com.aurionpro.lms.dto;
//
//import jakarta.validation.constraints.NotBlank;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Data
//public class ProfileUpdateRequestDTO {
//	@NotBlank(message = "Password is required")
//	private String password;
//
//	private String firstName;
//	private String lastName;
//	private LocalDate dateOfBirth;
//	private String mobileNumber;
//	private String gender; // "MALE", "FEMALE", "OTHER"
//}

package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDTO {
	@NotBlank(message = "Password is required")
	private String password;

	@Size(max = 50, message = "First name must not exceed 50 characters")
	private String firstName;

	@Size(max = 50, message = "Last name must not exceed 50 characters")
	private String lastName;

	@Past(message = "Date of birth must be in the past")
	private LocalDate dateOfBirth;

	@Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
	private String mobileNumber;

	@Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
	private String gender;
}