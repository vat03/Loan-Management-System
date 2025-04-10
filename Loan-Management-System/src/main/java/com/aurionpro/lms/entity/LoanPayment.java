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

//package com.aurionpro.lms.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "loan_payment")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class LoanPayment {
//
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
//	@Column(name = "due_date", nullable = false)
//	private LocalDate dueDate;
//
//	@Column(nullable = false)
//	private String status;
//
//	@Column(name = "interest_rate", nullable = false)
//	private BigDecimal interestRate; // Store interest rate for this payment
//
//	@Column(name = "penalty_percentage")
//	private BigDecimal penaltyPercentage; // Penalty if paid late (e.g., 2% of amount)
//	
//	@Column(name = "penalty_amount", nullable = true)
//    private BigDecimal penaltyAmount;
//}

package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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

	@NotNull(message = "Loan is required")
	@ManyToOne
	@JoinColumn(name = "loan_id", nullable = false)
	private Loan loan;

	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be positive")
	@Column(nullable = false)
	private BigDecimal amount;

	@NotNull(message = "Due date is required")
	@FutureOrPresent(message = "Due date cannot be in the past")
	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@NotBlank(message = "Status is required")
	@Column(nullable = false)
	private String status;

	@NotNull(message = "Interest rate is required")
	@Positive(message = "Interest rate must be positive")
	@Column(name = "interest_rate", nullable = false)
	private BigDecimal interestRate;

	@PositiveOrZero(message = "Penalty percentage cannot be negative")
	@Column(name = "penalty_percentage")
	private BigDecimal penaltyPercentage;

	@PositiveOrZero(message = "Penalty amount cannot be negative")
	@Column(name = "penalty_amount", nullable = true)
	private BigDecimal penaltyAmount;
}