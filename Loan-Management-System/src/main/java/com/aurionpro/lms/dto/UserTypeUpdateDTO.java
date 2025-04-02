package com.aurionpro.lms.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserTypeUpdateDTO {
	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
	private String firstName;

	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
	private String lastName;

	private Integer age;

	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be a 10-digit number")
	private String mobileNumber;

	@Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
	private String gender;

	private LocalDate birthDate;
}