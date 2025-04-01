package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends UserType {
	@ManyToOne
	@JoinColumn(name = "loan_officer_id", nullable = false) // Customer is assigned to a Loan Officer
	private LoanOfficer loanOfficer;
}
