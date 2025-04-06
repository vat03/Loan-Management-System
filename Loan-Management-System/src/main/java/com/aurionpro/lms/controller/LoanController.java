package com.aurionpro.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
import com.aurionpro.lms.dto.LoanRequestDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.dto.LoanUpdateDTO;
import com.aurionpro.lms.service.LoanPaymentService;
import com.aurionpro.lms.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

	@Autowired
	private LoanService loanService;
	
	@Autowired
    private LoanPaymentService loanPaymentService;

	@PostMapping("/apply")
	public ResponseEntity<LoanResponseDTO> applyForLoan(@RequestBody LoanRequestDTO requestDTO) {
		LoanResponseDTO responseDTO = loanService.applyForLoan(requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<LoanResponseDTO> updateLoanStatus(@PathVariable int id,
			@RequestBody LoanUpdateDTO updateDTO) {
		LoanResponseDTO responseDTO = loanService.updateLoanStatus(id, updateDTO);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getByLoanId/{id}")
	public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable int id) {
		LoanResponseDTO responseDTO = loanService.getLoanById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getByCustomerId/customer/{customerId}")
	public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomerId(@PathVariable int customerId) {
		List<LoanResponseDTO> responseDTOs = loanService.getLoansByCustomerId(customerId);
		return ResponseEntity.ok(responseDTOs);
	}

	@GetMapping("/getByLoanOfficerId/loan-officer/{loanOfficerId}")
	public ResponseEntity<List<LoanResponseDTO>> getLoansByLoanOfficerId(@PathVariable int loanOfficerId) {
		List<LoanResponseDTO> responseDTOs = loanService.getLoansByLoanOfficerId(loanOfficerId);
		return ResponseEntity.ok(responseDTOs);
	}
	
	@PostMapping("/repay/{loanPaymentId}")
    public ResponseEntity<String> repayLoanPayment(@PathVariable int loanPaymentId) {
        loanPaymentService.processRepayment(loanPaymentId);
        return ResponseEntity.ok("Payment processed successfully");
    }

    @GetMapping("/payments/{loanId}")
    public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId) {
        List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId);
        return ResponseEntity.ok(payments);
    }
}