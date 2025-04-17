package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	@Size(max = 100, message = "Email must not exceed 100 characters")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	@Column(unique = true)
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Password must contain at least one uppercase, one lowercase, one digit, and one special character")
	private String password; // Stored as BCrypt hash

	@NotNull(message = "Role is required")
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@Size(max = 50, message = "First name must not exceed 50 characters")
	@Column(nullable = true)
	private String firstName;

	@Size(max = 50, message = "Last name must not exceed 50 characters")
	@Column(nullable = true)
	private String lastName;

	@Past(message = "Date of birth must be in the past")
	@Column(nullable = true)
	private LocalDate dateOfBirth;

	@Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
	@Column(nullable = true)
	private String mobileNumber;

	@Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
	@Column(nullable = true)
	private String gender;

	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted = false;
}