//package com.aurionpro.lms.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//
//@Data
//public class LoanOfficerRequestDTO {
//	@NotBlank(message = "Username is required")
//	private String username;
//
//	@Email(message = "Invalid email format")
//	@NotBlank(message = "Email is required")
//	private String email;
//
//	@NotBlank(message = "Password is required")
//	@Size(min = 8, message = "Password must be at least 8 characters long")
//	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Password must contain at least one uppercase, one lowercase, one digit, and one special character")
//	private String password;
//}

package com.aurionpro.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoanOfficerRequestDTO {
	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	private String username;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	@Size(max = 100, message = "Email must not exceed 100 characters")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 100, message = "Password must be between 8 and ，看 characters")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Password must contain at least one uppercase, one lowercase, one digit, and one special character")
	private String password;
}