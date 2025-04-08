package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loan {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loanId;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "loan_officer_id", nullable = false)
	private LoanOfficer loanOfficer;

	@ManyToOne
	@JoinColumn(name = "loan_scheme_id", nullable = false)
	private LoanScheme loanScheme;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false)
	private LocalDate applicationDate;

	@ManyToOne
	@JoinColumn(name = "loan_status_id", nullable = false)
	private LoanStatus status;
	
	@ManyToOne
    @JoinColumn(name = "npa_status_id", nullable = true)
    private NpaStatus npaStatus;

	@Column(nullable = false)
	private LocalDate dueDate;
}