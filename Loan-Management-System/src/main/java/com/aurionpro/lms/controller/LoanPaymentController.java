//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.service.LoanPaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/loan-payments")
//public class LoanPaymentController {
//
//	@Autowired
//	private LoanPaymentService loanPaymentService;
//
//	@PostMapping("/makePayment/loan/{loanId}")
//	public ResponseEntity<Void> createLoanPayments(@PathVariable int loanId) {
//		loanPaymentService.createLoanPayments(loanId);
//		return ResponseEntity.status(201).build();
//	}
//
//	@GetMapping("/customer/{customerId}/pending")
//	public ResponseEntity<List<LoanResponseDTO>> getPendingPaymentsByCustomerId(@PathVariable int customerId) {
//		List<LoanResponseDTO> responseDTOs = loanPaymentService.getPendingPaymentsByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//}

package com.aurionpro.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.service.LoanPaymentService;

@RestController
@RequestMapping("/api/loan-payments")
public class LoanPaymentController {

	@Autowired
	private LoanPaymentService loanPaymentService;

	@PostMapping("/{loanPaymentId}/repay")
	public ResponseEntity<String> repayLoanPayment(@PathVariable int loanPaymentId) {
		loanPaymentService.processRepayment(loanPaymentId);
		return ResponseEntity.ok("Payment processed successfully");
	}

	@GetMapping("/loan/{loanId}")
	public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId) {
		List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId);
		return ResponseEntity.ok(payments);
	}
	
	@GetMapping("/customer/{customerId}/pending")
	public ResponseEntity<List<LoanResponseDTO>> getPendingPaymentsByCustomerId(@PathVariable int customerId) {
		List<LoanResponseDTO> responseDTOs = loanPaymentService.getPendingPaymentsByCustomerId(customerId);
		return ResponseEntity.ok(responseDTOs);
	}
}