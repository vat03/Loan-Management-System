package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
import com.aurionpro.lms.service.LoanSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-schemes")
public class LoanSchemeController {

	@Autowired
	private LoanSchemeService loanSchemeService;

	@PostMapping
	public ResponseEntity<LoanSchemeResponseDTO> createLoanScheme(@RequestParam int adminId,
			@RequestBody LoanSchemeRequestDTO requestDTO) {
		LoanSchemeResponseDTO responseDTO = loanSchemeService.createLoanScheme(adminId, requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LoanSchemeResponseDTO> getLoanSchemeById(@PathVariable int id) {
		LoanSchemeResponseDTO responseDTO = loanSchemeService.getLoanSchemeById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/admin/{adminId}")
	public ResponseEntity<List<LoanSchemeResponseDTO>> getLoanSchemesByAdminId(@PathVariable int adminId) {
		List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getLoanSchemesByAdminId(adminId);
		return ResponseEntity.ok(responseDTOs);
	}
}