package com.aurionpro.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.DocumentRequestDTO;
import com.aurionpro.lms.dto.DocumentResponseDTO;
import com.aurionpro.lms.dto.DocumentVerificationDTO;
import com.aurionpro.lms.exception.InvalidInputException;
import com.aurionpro.lms.service.DocumentService;

import jakarta.validation.Valid;

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

//	@GetMapping("/getByLoanId/loan/{loanId}")
//	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
//	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByLoanId(@PathVariable int loanId) {
//		List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByLoanId(loanId);
//		return ResponseEntity.ok(responseDTOs);
//	}

	@GetMapping("/getByLoanId/loan/{loanId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByLoanId(@PathVariable int loanId,
			@RequestParam(required = false) String status) {
		List<DocumentResponseDTO> responseDTOs;
		if (status != null) {
			responseDTOs = documentService.getDocumentsByLoanIdAndStatus(loanId, status);
		} else {
			responseDTOs = documentService.getDocumentsByLoanId(loanId);
		}
		return ResponseEntity.ok(responseDTOs);
	}
	
	@GetMapping("/getByLoanOfficerId/loan-officer/{loanOfficerId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByLoanOfficerId(
	        @PathVariable int loanOfficerId,
	        @RequestParam(required = false) String status) {
	    List<DocumentResponseDTO> responseDTOs;
	    if (status != null) {
	        responseDTOs = documentService.getDocumentsByLoanOfficerId(loanOfficerId, status);
	    } else {
	        throw new InvalidInputException("Status parameter is required");
	    }
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