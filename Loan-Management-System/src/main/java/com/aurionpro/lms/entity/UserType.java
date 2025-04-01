package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "user_type")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserType {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
	private String lastName;

	@Min(value = 18, message = "Age must be at least 18")
	private int age;

	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be a 10-digit number")
	private String mobileNumber;

	@NotBlank(message = "Gender is required")
	@Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
	private String gender;
}
