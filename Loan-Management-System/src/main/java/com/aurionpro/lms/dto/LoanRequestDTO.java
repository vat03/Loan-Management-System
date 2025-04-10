//package com.aurionpro.lms.dto;
//
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//import java.math.BigDecimal;
//
//@Data
//public class LoanRequestDTO {
//	@NotNull(message = "Amount is required")
//	private BigDecimal amount;
//
//	@NotNull(message = "Loan scheme ID is required")
//	private Integer loanSchemeId;
//
//	@NotNull(message = "Customer ID is required")
//	private Integer customerId;
//}

package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequestDTO {
	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be positive")
	private BigDecimal amount;

	@NotNull(message = "Loan scheme ID is required")
	@Positive(message = "Loan scheme ID must be positive")
	private Integer loanSchemeId;

	@NotNull(message = "Customer ID is required")
	@Positive(message = "Customer ID must be positive")
	private Integer customerId;
}