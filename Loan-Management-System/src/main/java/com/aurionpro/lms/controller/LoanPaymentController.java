package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
import com.aurionpro.lms.dto.PaymentCompletionRequestDto;
import com.aurionpro.lms.exception.PaymentProcessingException;
import com.aurionpro.lms.service.LoanPaymentService;
import com.aurionpro.lms.service.PaymentService;
import com.razorpay.RazorpayException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loan-payments")
public class LoanPaymentController {

	@Autowired
	private LoanPaymentService loanPaymentService;

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/{loanPaymentId}/repay")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<String> repayLoanPayment(@PathVariable int loanPaymentId) throws RazorpayException {
		BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId);
		String orderId = paymentService.createPaymentOrder(loanPaymentId, amount);
		return ResponseEntity.ok(orderId);
	}

	@PostMapping("/complete")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<String> completePayment(
			@Valid @RequestBody PaymentCompletionRequestDto paymentCompletionRequestDto) throws RazorpayException {
		boolean isValid = paymentService.verifyPayment(paymentCompletionRequestDto.getOrderId(),
				paymentCompletionRequestDto.getPaymentId(), paymentCompletionRequestDto.getSignature());
		if (isValid) {
			loanPaymentService.processRepayment(paymentCompletionRequestDto.getLoanPaymentId());
			return ResponseEntity.ok("Payment processed successfully");
		} else {
			throw new PaymentProcessingException("Payment verification failed");
		}
	}

	@GetMapping("/loan/{loanId}")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_LOAN_OFFICER')")
	public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId,
			@RequestParam(required = false) String status) {
		List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId, status);
		return ResponseEntity.ok(payments);
	}

	@GetMapping("/getPaymentDetails/{loanPaymentId}")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_LOAN_OFFICER')")
	public ResponseEntity<LoanPaymentResponseDTO> getPaymentDetails(@PathVariable int loanPaymentId) {
		LoanPaymentResponseDTO payment = loanPaymentService.getPaymentDetails(loanPaymentId);
		return ResponseEntity.ok(payment);
	}

//	@GetMapping("/totalPaymentAmount/{loanPaymentId}/amount")
//	public ResponseEntity<BigDecimal> getTotalPaymentAmount(@PathVariable int loanPaymentId) {
//		BigDecimal amount = loanPaymentService.getPaymentAmount(loanPaymentId);
//		return ResponseEntity.ok(amount);
//	}

	@PostMapping("/npa/approve/{loanId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<String> approveNpaStatus(@PathVariable int loanId, @RequestParam boolean approve) {
		loanPaymentService.approveNpaStatus(loanId, approve);
		return ResponseEntity.ok(approve ? "NPA status approved" : "NPA status rejected");
	}

	@PostMapping("/check-npa")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> checkAndFlagNpaLoans() {
		loanPaymentService.checkAndFlagNpaLoans();
		return ResponseEntity.ok("NPA check completed");
	}
}