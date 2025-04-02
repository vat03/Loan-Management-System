package com.aurionpro.lms.dto;

import lombok.Data;

@Data
public class LoanSchemeResponseDTO {
	private int id;
	private String schemeName;
	private Double interestRate;
	private Integer tenureMonths;
	private int adminId; // The admin who created this scheme
}