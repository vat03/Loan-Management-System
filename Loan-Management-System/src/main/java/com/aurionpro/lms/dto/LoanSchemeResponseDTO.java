package com.aurionpro.lms.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LoanSchemeResponseDTO {
	private int id;
	private String schemeName;
	private BigDecimal interestRate;
	private Integer tenureMonths;
	private int adminId; // The admin who created this scheme
}