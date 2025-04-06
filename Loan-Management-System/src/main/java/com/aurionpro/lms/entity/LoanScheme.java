package com.aurionpro.lms.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loan_schemes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanScheme {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String schemeName;

	@Column(nullable = false)
	private BigDecimal interestRate;

	@Column(nullable = false)
	private Integer tenureMonths;

	@ManyToOne
	@JoinColumn(name = "admin_id", nullable = false)
	private Admin admin;
}