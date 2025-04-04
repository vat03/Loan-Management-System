//package com.aurionpro.lms.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Pattern;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "user_type")
//@Inheritance(strategy = InheritanceType.JOINED)
//public abstract class UserType {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//
//	@Column
//	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
//	private String firstName;
//
//	@Column
//	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
//	private String lastName;
//
//	@Column
//	private Integer age; // Changed to Integer to allow null
//
//	@Column
//	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be a 10-digit number")
//	private String mobileNumber;
//
//	@Column
//	@Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
//	private String gender;
//
//	@Column
//	private LocalDate birthDate;
//}