//package com.aurionpro.lms.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Entity
//@Table(name = "roles")
//public class Role {
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int roleId;
//
//	@NotBlank(message = "Role name is required")
//	@Column(unique = true)
//	private String roleName; // "ADMIN", "LOAN_OFFICER", "CUSTOMER"
//
//	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<User> users;
//}

package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;

	@NotBlank(message = "Role name is required")
	@Column(unique = true)
	private String roleName;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<User> users;
}