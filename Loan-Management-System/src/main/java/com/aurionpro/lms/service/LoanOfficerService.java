package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
import com.aurionpro.lms.dto.LoanOfficerResponseDTO;

import java.util.List;

public interface LoanOfficerService {
	LoanOfficerResponseDTO addLoanOfficer(int adminId, LoanOfficerRequestDTO requestDTO);

	LoanOfficerResponseDTO getLoanOfficerById(int id);

	List<LoanOfficerResponseDTO> getLoanOfficersByAdminId(int adminId);
}