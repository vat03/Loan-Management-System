package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanResponseDTO;

import java.util.List;

public interface LoanPaymentService {
	void createLoanPayments(int loanId); // Generate payment schedule

	List<LoanResponseDTO> getPendingPaymentsByCustomerId(int customerId);
}