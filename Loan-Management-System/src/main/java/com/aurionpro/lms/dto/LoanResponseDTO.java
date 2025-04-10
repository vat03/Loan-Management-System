//package com.aurionpro.lms.dto;
//
//import lombok.Data;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Data
//public class LoanResponseDTO {
//	private int loanId;
//	private BigDecimal amount;
//	private String loanSchemeName;
//	private String statusName;
//	private LocalDate applicationDate;
//	private LocalDate dueDate;
//	private int loanOfficerId;
//	private int customerId;
//}

package com.aurionpro.lms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class LoanResponseDTO {
	private int loanId;
	private BigDecimal amount;
	private String loanSchemeName;
	private String statusName;
	private LocalDate applicationDate;
	private LocalDate dueDate;
	private int loanOfficerId;
	private int customerId;
}