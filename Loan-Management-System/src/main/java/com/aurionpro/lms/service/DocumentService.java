package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.DocumentRequestDTO;
import com.aurionpro.lms.dto.DocumentResponseDTO;

import java.util.List;

public interface DocumentService {
	DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO);

	DocumentResponseDTO getDocumentById(int id);

	List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId);
}