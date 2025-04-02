package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanSchemeRequestDTO {
	@NotBlank(message = "Scheme name is required")
	private String schemeName;

	@NotNull(message = "Interest rate is required")
	private Double interestRate;

	@NotNull(message = "Tenure in months is required")
	private Integer tenureMonths;
}