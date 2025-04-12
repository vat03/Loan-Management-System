package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LoanSchemeUpdateDTO {
	@NotNull(message = "Interest rate is required")
	@Positive(message = "Interest rate must be positive")
	private BigDecimal interestRate;

	@NotNull(message = "Tenure in months is required")
	@Positive(message = "Tenure must be positive")
	private Integer tenureMonths;

	@NotEmpty(message = "At least one document type is required")
	private List<Integer> requiredDocumentTypeIds;
}