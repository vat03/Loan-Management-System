//package com.aurionpro.lms.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//@Data
//@RequiredArgsConstructor
//@AllArgsConstructor
//public class PaymentCompletionRequestDto {
//	private String orderId;
//	private String paymentId;
//	private String signature;
//	private int loanPaymentId;
//}

package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentCompletionRequestDto {
	@NotBlank(message = "Order ID is required")
	private String orderId;

	@NotBlank(message = "Payment ID is required")
	private String paymentId;

	@NotBlank(message = "Signature is required")
	private String signature;

	@Positive(message = "Loan payment ID must be positive")
	private int loanPaymentId;
}