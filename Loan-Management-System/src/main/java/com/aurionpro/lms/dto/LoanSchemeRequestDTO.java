//package com.aurionpro.lms.dto;
//
//import java.math.BigDecimal;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//@Data
//public class LoanSchemeRequestDTO {
//	@NotBlank(message = "Scheme name is required")
//	private String schemeName;
//
//	@NotNull(message = "Interest rate is required")
//	private BigDecimal interestRate;
//
//	@NotNull(message = "Tenure in months is required")
//	private Integer tenureMonths;
//}

package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LoanSchemeRequestDTO {
	@NotBlank(message = "Scheme name is required")
	private String schemeName;

	@NotNull(message = "Interest rate is required")
	private BigDecimal interestRate;

	@NotNull(message = "Tenure in months is required")
	private Integer tenureMonths;

	@NotEmpty(message = "At least one document type is required")
	private List<Integer> requiredDocumentTypeIds;
}