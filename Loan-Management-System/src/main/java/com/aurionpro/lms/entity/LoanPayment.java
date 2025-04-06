//package com.aurionpro.lms.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "loan_payments")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class LoanPayment {
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//
//	@ManyToOne
//	@JoinColumn(name = "loan_id", nullable = false)
//	private Loan loan;
//
//	@Column(nullable = false)
//	private BigDecimal amount;
//
//	@Column(nullable = false)
//	private LocalDate dueDate;
//
//	@Column(nullable = false)
//	private String status; // "PENDING", "PAID", "OVERDUE"
//}

package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loan_payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanPayment {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "loan_id", nullable = false)
	private Loan loan;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@Column(nullable = false)
	private String status;

	@Column(name = "interest_rate", nullable = false)
	private BigDecimal interestRate; // Store interest rate for this payment

	@Column(name = "penalty_percentage")
	private BigDecimal penaltyPercentage; // Penalty if paid late (e.g., 2% of amount)
	
	@Column(name = "penalty_amount")
    private BigDecimal penaltyAmount;
}