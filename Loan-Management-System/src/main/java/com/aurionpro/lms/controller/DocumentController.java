package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.DocumentRequestDTO;
import com.aurionpro.lms.dto.DocumentResponseDTO;
import com.aurionpro.lms.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@PostMapping("/upload")
	public ResponseEntity<DocumentResponseDTO> uploadDocument(@RequestBody DocumentRequestDTO requestDTO) {
		DocumentResponseDTO responseDTO = documentService.uploadDocument(requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable int id) {
		DocumentResponseDTO responseDTO = documentService.getDocumentById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getByCustomerId/customer/{customerId}")
	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByCustomerId(@PathVariable int customerId) {
		List<DocumentResponseDTO> responseDTOs = documentService.getDocumentsByCustomerId(customerId);
		return ResponseEntity.ok(responseDTOs);
	}
}