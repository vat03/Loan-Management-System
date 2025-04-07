package com.aurionpro.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentCompletionRequestDto {
	private String orderId;
	private String paymentId;
	private String signature;
	private int loanPaymentId;
}