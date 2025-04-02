package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
import com.aurionpro.lms.dto.LoanSchemeResponseDTO;

import java.util.List;

public interface LoanSchemeService {
	LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO);

	LoanSchemeResponseDTO getLoanSchemeById(int id);

	List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId);
}