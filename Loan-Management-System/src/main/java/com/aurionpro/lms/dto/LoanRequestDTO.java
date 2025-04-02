package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequestDTO {
	@NotNull(message = "Amount is required")
	private BigDecimal amount;

	@NotNull(message = "Loan scheme ID is required")
	private Integer loanSchemeId;

	@NotNull(message = "Customer ID is required")
	private Integer customerId;
}