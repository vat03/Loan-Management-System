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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
import com.aurionpro.lms.dto.LoanSchemeUpdateDTO;
import com.aurionpro.lms.entity.DocumentType;
import com.aurionpro.lms.service.LoanSchemeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loan-schemes")
public class LoanSchemeController {

	@Autowired
	private LoanSchemeService loanSchemeService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<LoanSchemeResponseDTO> createLoanScheme(@RequestParam int adminId,
			@Valid @RequestBody LoanSchemeRequestDTO requestDTO) {
		LoanSchemeResponseDTO responseDTO = loanSchemeService.createLoanScheme(adminId, requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/getByLoanId/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<LoanSchemeResponseDTO> getLoanSchemeById(@PathVariable int id) {
		LoanSchemeResponseDTO responseDTO = loanSchemeService.getLoanSchemeById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getByAdminId/admin/{adminId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<LoanSchemeResponseDTO>> getLoanSchemesByAdminId(@PathVariable int adminId) {
		List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getLoanSchemesByAdminId(adminId);
		return ResponseEntity.ok(responseDTOs);
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<List<LoanSchemeResponseDTO>> getAllLoanSchemes() {
		List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getAllLoanSchemes();
		return ResponseEntity.ok(responseDTOs);
	}

	@PutMapping("/{id}/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<LoanSchemeResponseDTO> updateLoanScheme(@PathVariable int id, @RequestParam int adminId,
			@Valid @RequestBody LoanSchemeUpdateDTO updateDTO) {
		LoanSchemeResponseDTO responseDTO = loanSchemeService.updateLoanScheme(id, adminId, updateDTO);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("{schemeId}/document-types")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<List<DocumentType>> getRequiredDocumentTypes(@PathVariable int schemeId) {
		return ResponseEntity.ok(loanSchemeService.getRequiredDocumentTypes(schemeId));
	}
	
}