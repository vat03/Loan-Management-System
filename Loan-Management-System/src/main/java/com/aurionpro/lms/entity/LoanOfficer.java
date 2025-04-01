package com.aurionpro.lms.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "loan_officer")
public class LoanOfficer extends UserType {
	@ManyToOne
	@JoinColumn(name = "admin_id", nullable = false) // Loan Officers are added by Admin
	private Admin admin;

	@OneToMany(mappedBy = "loanOfficer", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<Customer> customers;

}
