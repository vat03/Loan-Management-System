package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class Customer extends UserType {
	@ManyToOne
	@JoinColumn(name = "loan_officer_id", nullable = false)
	private LoanOfficer loanOfficer;
}