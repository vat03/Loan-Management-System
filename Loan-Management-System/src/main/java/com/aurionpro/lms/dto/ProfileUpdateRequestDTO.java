package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDTO {
	@NotBlank(message = "Password is required")
	private String password;

	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String mobileNumber;
	private String gender; // "MALE", "FEMALE", "OTHER"
}