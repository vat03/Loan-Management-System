//package com.aurionpro.lms.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Data
//@AllArgsConstructor
//@RequiredArgsConstructor
//public class LoanPaymentResponseDTO {
//	private int id;
//	private int loanId;
//	private BigDecimal amount;
//	private LocalDate dueDate;
//	private String status;
//	private BigDecimal penaltyAmount;
//}

package com.aurionpro.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanPaymentResponseDTO {
	private int id;
	private int loanId;
	private BigDecimal amount;
	private LocalDate dueDate;
	private String status;
	private BigDecimal penaltyAmount;
}