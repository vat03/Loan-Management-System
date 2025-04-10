//package com.aurionpro.lms.dto;
//
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Data
//public class ProfileResponseDTO {
//	private int id;
//	private String username;
//	private String email;
//	private String roleName;
//	private String firstName;
//	private String lastName;
//	private LocalDate dateOfBirth;
//	private String mobileNumber;
//	private String gender;
//}

package com.aurionpro.lms.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileResponseDTO {
	private int id;
	private String username;
	private String email;
	private String roleName;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String mobileNumber;
	private String gender;
}