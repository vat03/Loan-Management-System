package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
import com.aurionpro.lms.service.LoanOfficerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-officers")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class LoanOfficerController {

	@Autowired
	private LoanOfficerService loanOfficerService;

	@PostMapping("/addLoanOfficer")
	public ResponseEntity<LoanOfficerResponseDTO> addLoanOfficer(@RequestParam int adminId,
			@Valid @RequestBody LoanOfficerRequestDTO requestDTO) {
		LoanOfficerResponseDTO responseDTO = loanOfficerService.addLoanOfficer(adminId, requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/getLoanOfficerById/{id}")
	public ResponseEntity<LoanOfficerResponseDTO> getLoanOfficerById(@PathVariable int id) {
		LoanOfficerResponseDTO responseDTO = loanOfficerService.getLoanOfficerById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getAllLoanOfficers")
	public ResponseEntity<List<LoanOfficerResponseDTO>> getAllLoanOfficers() {
		return ResponseEntity.ok(loanOfficerService.getAllLoanOfficers());
	}

	@GetMapping("/admin/{adminId}")
	public ResponseEntity<List<LoanOfficerResponseDTO>> getLoanOfficersByAdminId(@PathVariable int adminId) {
		List<LoanOfficerResponseDTO> responseDTOs = loanOfficerService.getLoanOfficersByAdminId(adminId);
		return ResponseEntity.ok(responseDTOs);
	}
}