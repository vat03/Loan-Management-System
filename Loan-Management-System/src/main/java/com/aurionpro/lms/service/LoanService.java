package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanRequestDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.dto.LoanUpdateDTO;

import java.util.List;

public interface LoanService {
	LoanResponseDTO applyForLoan(LoanRequestDTO requestDTO);

	LoanResponseDTO updateLoanStatus(int loanId, LoanUpdateDTO updateDTO);

	LoanResponseDTO getLoanById(int id);

	List<LoanResponseDTO> getLoansByCustomerId(int customerId);

	List<LoanResponseDTO> getLoansByLoanOfficerId(int loanOfficerId);
	
	LoanResponseDTO markLoanAsNpa(int loanId, boolean isNpa);
}