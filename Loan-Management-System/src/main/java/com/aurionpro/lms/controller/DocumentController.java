//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//import com.aurionpro.lms.service.DocumentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/documents")
//public class DocumentController {
//
//	@Autowired
//	private DocumentService documentService;
//
//	@PostMapping("/upload")
//	public ResponseEntity<DocumentResponseDTO> uploadDocument(@RequestBody DocumentRequestDTO requestDTO) {
//		DocumentResponseDTO responseDTO = documentService.uploadDocument(requestDTO);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@GetMapping("/getById/{id}")
//	public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable int id) {
//		DocumentResponseDTO responseDTO = documentService.getDocumentById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getByCustomerId/customer/{customerId}")
//	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByCustomerId(@PathVariable int customerId) {
//		List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//import com.aurionpro.lms.service.DocumentService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/documents")
//public class DocumentController {
//
//	@Autowired
//	private DocumentService documentService;
//
//	@PostMapping(value = "/upload", consumes = "multipart/form-data")
//	public ResponseEntity<DocumentResponseDTO> uploadDocument(@ModelAttribute DocumentRequestDTO requestDTO) {
//		DocumentResponseDTO responseDTO = documentService.uploadDocument(requestDTO);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@GetMapping("/getById/{id}")
//	public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable int id) {
//		DocumentResponseDTO responseDTO = documentService.getDocumentById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getByCustomerId/customer/{customerId}")
//	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByCustomerId(@PathVariable int customerId) {
//		List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//
//	@GetMapping("/getByLoanId/loan/{loanId}")
//	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByLoanId(@PathVariable int loanId) {
//		List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByLoanId(loanId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//import com.aurionpro.lms.dto.DocumentVerificationDTO;
//import com.aurionpro.lms.service.DocumentService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/documents")
//public class DocumentController {
//
//	@Autowired
//	private DocumentService documentService;
//
//	@PostMapping(value = "/upload", consumes = "multipart/form-data")
//	public ResponseEntity<DocumentResponseDTO> uploadDocument(@ModelAttribute DocumentRequestDTO requestDTO) {
//		DocumentResponseDTO responseDTO = documentService.uploadDocument(requestDTO);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@GetMapping("/getById/{id}")
//	public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable int id) {
//		DocumentResponseDTO responseDTO = documentService.getDocumentById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getByCustomerId/customer/{customerId}")
//	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByCustomerId(@PathVariable int customerId) {
//		List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//
//	@GetMapping("/getByLoanId/loan/{loanId}")
//	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByLoanId(@PathVariable int loanId) {
//		List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByLoanId(loanId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//
//	@PutMapping("/{documentId}/verify")
//	public ResponseEntity<DocumentResponseDTO> verifyDocument(@PathVariable int documentId,
//			@RequestBody DocumentVerificationDTO verificationDTO) {
//		DocumentResponseDTO responseDTO = documentService.verifyDocument(documentId, verificationDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.DocumentRequestDTO;
import com.aurionpro.lms.dto.DocumentResponseDTO;
import com.aurionpro.lms.dto.DocumentVerificationDTO;
import com.aurionpro.lms.exception.DocumentUploadException;
import com.aurionpro.lms.exception.InvalidInputException;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.service.DocumentService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	public ResponseEntity<DocumentResponseDTO> uploadDocument(@Valid @ModelAttribute DocumentRequestDTO requestDTO) {
		try {
			DocumentResponseDTO responseDTO = documentService.uploadDocument(requestDTO);
			return ResponseEntity.status(201).body(responseDTO);
		} catch (ResourceNotFoundException | DocumentUploadException | InvalidInputException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error uploading document", e);
		}
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<DocumentResponseDTO> getDocumentById(@Valid @PathVariable int id) {
		try {
			DocumentResponseDTO responseDTO = documentService.getDocumentById(id);
			return ResponseEntity.ok(responseDTO);
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error fetching document with ID: " + id, e);
		}
	}

	@GetMapping("/getByCustomerId/customer/{customerId}")
	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByCustomerId(@Valid @PathVariable int customerId) {
		try {
			List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByCustomerId(customerId);
			return ResponseEntity.ok(responseDTOs);
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error fetching documents for customer ID: " + customerId, e);
		}
	}

	@GetMapping("/getByLoanId/loan/{loanId}")
	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByLoanId(@Valid @PathVariable int loanId) {
		try {
			List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByLoanId(loanId);
			return ResponseEntity.ok(responseDTOs);
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error fetching documents for loan ID: " + loanId, e);
		}
	}

	@PutMapping("/{documentId}/verify")
	public ResponseEntity<DocumentResponseDTO> verifyDocument(@Valid @PathVariable int documentId,
			@Valid @RequestBody DocumentVerificationDTO verificationDTO) {
		try {
			DocumentResponseDTO responseDTO = documentService.verifyDocument(documentId, verificationDTO);
			return ResponseEntity.ok(responseDTO);
		} catch (ResourceNotFoundException | InvalidInputException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error verifying document with ID: " + documentId, e);
		}
	}
}