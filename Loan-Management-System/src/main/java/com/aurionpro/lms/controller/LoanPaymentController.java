package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.service.LoanPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-payments")
public class LoanPaymentController {

	@Autowired
	private LoanPaymentService loanPaymentService;

	@PostMapping("/loan/{loanId}")
	public ResponseEntity<Void> createLoanPayments(@PathVariable int loanId) {
		loanPaymentService.createLoanPayments(loanId);
		return ResponseEntity.status(201).build();
	}

	@GetMapping("/customer/{customerId}/pending")
	public ResponseEntity<List<LoanResponseDTO>> getPendingPaymentsByCustomerId(@PathVariable int customerId) {
		List<LoanResponseDTO> responseDTOs = loanPaymentService.getPendingPaymentsByCustomerId(customerId);
		return ResponseEntity.ok(responseDTOs);
	}
}