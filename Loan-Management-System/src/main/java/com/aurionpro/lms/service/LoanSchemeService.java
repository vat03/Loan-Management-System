//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//
//import java.util.List;
//
//public interface LoanSchemeService {
//	LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO);
//
//	LoanSchemeResponseDTO getLoanSchemeById(int id);
//
//	List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId);
//
//	List<LoanSchemeResponseDTO> getAllLoanSchemes();
//
//	void softDeleteLoanScheme(Integer schemeId, Integer adminId);
//
//	List<LoanSchemeResponseDTO> getAllLoanSchemesForAdmin(boolean includeDeleted);
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
import com.aurionpro.lms.dto.LoanSchemeUpdateDTO;
import com.aurionpro.lms.entity.DocumentType;

import java.util.List;

public interface LoanSchemeService {
	LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO);

	LoanSchemeResponseDTO getLoanSchemeById(int id);

	List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId);

	List<LoanSchemeResponseDTO> getAllLoanSchemes();

	void softDeleteLoanScheme(Integer schemeId, Integer adminId);

	List<LoanSchemeResponseDTO> getAllLoanSchemesForAdmin(boolean includeDeleted);
	
	List<DocumentType> getRequiredDocumentTypes(int schemeId);

	LoanSchemeResponseDTO updateLoanScheme(Integer schemeId, Integer adminId, LoanSchemeUpdateDTO updateDTO);
}