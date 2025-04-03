package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoanRequestDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.dto.LoanUpdateDTO;
import com.aurionpro.lms.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

	@Autowired
	private LoanService loanService;

	@PostMapping
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

	@GetMapping("/{id}")
	public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable int id) {
		LoanResponseDTO responseDTO = loanService.getLoanById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomerId(@PathVariable int customerId) {
		List<LoanResponseDTO> responseDTOs = loanService.getLoansByCustomerId(customerId);
		return ResponseEntity.ok(responseDTOs);
	}

	@GetMapping("/loan-officer/{loanOfficerId}")
	public ResponseEntity<List<LoanResponseDTO>> getLoansByLoanOfficerId(@PathVariable int loanOfficerId) {
		List<LoanResponseDTO> responseDTOs = loanService.getLoansByLoanOfficerId(loanOfficerId);
		return ResponseEntity.ok(responseDTOs);
	}
}