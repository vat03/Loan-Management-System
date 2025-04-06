package com.aurionpro.lms.service;

import java.util.List;

import com.aurionpro.lms.dto.LoanPaymentResponseDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;

public interface LoanPaymentService {
	void createLoanPayments(int loanId); // Generate payment schedule

	List<LoanResponseDTO> getPendingPaymentsByCustomerId(int customerId);
	
	void processRepayment(int loanPaymentId);
	
	List<LoanPaymentResponseDTO> getPaymentsByLoanId(int loanId);
}