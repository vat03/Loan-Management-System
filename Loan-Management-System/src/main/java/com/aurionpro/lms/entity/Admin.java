//package com.aurionpro.lms.entity;
//
//import java.util.List;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "admin")
//public class Admin extends UserType {
//	@OneToMany(mappedBy = "admin", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
//	private List<LoanOfficer> loanOfficers;
//
//	@OneToMany(mappedBy = "admin", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
//	private List<LoanScheme> loanSchemes;
//}

package com.aurionpro.lms.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "admin")
public class Admin {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@OneToMany(mappedBy = "admin", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<LoanOfficer> loanOfficers;

	@OneToMany(mappedBy = "admin", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<LoanScheme> loanSchemes;
}