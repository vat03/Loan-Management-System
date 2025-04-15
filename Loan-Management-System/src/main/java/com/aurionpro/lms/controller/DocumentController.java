package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.DocumentRequestDTO;
import com.aurionpro.lms.dto.DocumentResponseDTO;
import com.aurionpro.lms.dto.DocumentVerificationDTO;
import com.aurionpro.lms.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<DocumentResponseDTO> uploadDocument(@Valid @ModelAttribute DocumentRequestDTO requestDTO) {
		DocumentResponseDTO responseDTO = documentService.uploadDocument(requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/getById/{id}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable int id) {
		DocumentResponseDTO responseDTO = documentService.getDocumentById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getByCustomerId/customer/{customerId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByCustomerId(@PathVariable int customerId) {
		List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByCustomerId(customerId);
		return ResponseEntity.ok(responseDTOs);
	}

	@GetMapping("/getByLoanId/loan/{loanId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByLoanId(@PathVariable int loanId) {
		List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByLoanId(loanId);
		return ResponseEntity.ok(responseDTOs);
	}

	@PutMapping("/{documentId}/verify")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<DocumentResponseDTO> verifyDocument(@PathVariable int documentId,
			@Valid @RequestBody DocumentVerificationDTO verificationDTO) {
		DocumentResponseDTO responseDTO = documentService.verifyDocument(documentId, verificationDTO);
		return ResponseEntity.ok(responseDTO);
	}
}