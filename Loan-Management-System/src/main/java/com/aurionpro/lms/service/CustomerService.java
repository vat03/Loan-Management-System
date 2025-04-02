package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
	CustomerResponseDTO getCustomerById(int id);

	List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId);

	void assignLoanOfficer(int customerId, int loanOfficerId);
}