//package com.aurionpro.lms.entity;
//
//import java.util.List;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.PrimaryKeyJoinColumn;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//
//@AllArgsConstructor
//@RequiredArgsConstructor
//@Entity
//@Table(name = "admin")
//@PrimaryKeyJoinColumn(name = "id") // Ensuring proper inheritance
//public class Admin extends UserType {
//
//	@OneToMany(mappedBy = "admin", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
//	private List<LoanOfficer> loanOfficers;
//}

package com.aurionpro.lms.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin extends UserType {
	@OneToMany(mappedBy = "admin", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<LoanOfficer> loanOfficers;

	@OneToMany(mappedBy = "admin", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<LoanScheme> loanSchemes;
}