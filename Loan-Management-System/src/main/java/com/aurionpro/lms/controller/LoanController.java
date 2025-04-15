package com.aurionpro.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.LoanRequestDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

	@Autowired
	private LoanService loanService;

	@PostMapping("/apply")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<LoanResponseDTO> applyForLoan(@Valid @RequestBody LoanRequestDTO requestDTO) {
		LoanResponseDTO responseDTO = loanService.applyForLoan(requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

//	@PutMapping("/{id}/status")
//	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
//	public ResponseEntity<LoanResponseDTO> updateLoanStatus(@PathVariable int id,
//			@Valid @RequestBody LoanUpdateDTO updateDTO) {
//		LoanResponseDTO responseDTO = loanService.updateLoanStatus(id, updateDTO);
//		return ResponseEntity.ok(responseDTO);
//	}

	@GetMapping("/getByLoanId/{id}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable int id) {
		LoanResponseDTO responseDTO = loanService.getLoanById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getByCustomerId/customer/{customerId}")
	@PreAuthorize("hasAnyRole('ROLE_LOAN_OFFICER', 'ROLE_CUSTOMER')")
	public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomerId(@PathVariable int customerId) {
		List<LoanResponseDTO> responseDTOs = loanService.getLoansByCustomerId(customerId);
		return ResponseEntity.ok(responseDTOs);
	}

	@GetMapping("/getByLoanOfficerId/loan-officer/{loanOfficerId}")
	@PreAuthorize("hasAnyRole('ROLE_LOAN_OFFICER', 'ROLE_ADMIN')")
	public ResponseEntity<List<LoanResponseDTO>> getLoansByLoanOfficerId(@PathVariable int loanOfficerId) {
		List<LoanResponseDTO> responseDTOs = loanService.getLoansByLoanOfficerId(loanOfficerId);
		return ResponseEntity.ok(responseDTOs);
	}
	
	@PutMapping("/{loanId}/npa")
    @PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
    public ResponseEntity<LoanResponseDTO> markLoanAsNpa(@PathVariable int loanId, @RequestBody NpaRequestDTO requestDTO) {
        LoanResponseDTO responseDTO = loanService.markLoanAsNpa(loanId, requestDTO.isNpa());
        return ResponseEntity.ok(responseDTO);
    }
}

class NpaRequestDTO {
    private boolean isNpa;

    public boolean isNpa() {
        return isNpa;
    }

    public void setIsNpa(boolean isNpa) {
        this.isNpa = isNpa;
    }
}