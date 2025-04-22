//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//
//import java.util.List;
//
//public interface DocumentService {
//	DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO);
//
//	DocumentResponseDTO getDocumentById(int id);
//
//	List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId);
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//
//import java.util.List;
//
//public interface DocumentService {
//	DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO);
//
//	DocumentResponseDTO getDocumentById(int id);
//
//	List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId);
//
//	List<DocumentResponseDTO> getDocumentsByLoanId(int loanId);
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.DocumentRequestDTO;
import com.aurionpro.lms.dto.DocumentResponseDTO;
import com.aurionpro.lms.dto.DocumentVerificationDTO;

import java.util.List;

public interface DocumentService {
	DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO);

	DocumentResponseDTO getDocumentById(int id);

	List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId);

	List<DocumentResponseDTO> getDocumentsByLoanId(int loanId);

	DocumentResponseDTO verifyDocument(int documentId, DocumentVerificationDTO verificationDTO);
	
	List<DocumentResponseDTO> getDocumentsByLoanIdAndStatus(int loanId, String status);
	
	List<DocumentResponseDTO> getDocumentsByLoanOfficerId(int loanOfficerId, String status);
}