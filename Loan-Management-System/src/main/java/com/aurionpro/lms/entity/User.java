package com.aurionpro.lms.entity;

import org.hibernate.usertype.UserType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "Username is required")
	@Column(unique = true)
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Password must contain at least one uppercase, one lowercase, one digit, and one special character")
	@Column
	private String password;

	@ManyToOne
    @JoinColumn(name = "role_id", nullable = false)  // Foreign Key to Role Table
    private Role role;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userType", nullable = false, unique = true)
	private UserType userType; // FK to Admin, LoanOfficer, or Customer Table
}