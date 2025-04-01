package com.aurionpro.lms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loans")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loanId;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "loan_officer_id", nullable = false)
	private LoanOfficer loanOfficer;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false)
	private Double interestRate;

	@Column(nullable = false)
	private Integer tenureMonths;

	@Column(nullable = false)
	private LocalDate applicationDate;

	@ManyToOne
	@JoinColumn(name = "loan_status_id", nullable = false)
	private LoanStatus status;

	@Column(nullable = false)
	private LocalDate dueDate;
}
