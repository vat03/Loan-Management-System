//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//
//import java.util.List;
//
//public interface CustomerService {
//	CustomerResponseDTO getCustomerById(int id);
//
//	List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId);
//
//	void assignLoanOfficer(int customerId, int loanOfficerId);
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
	CustomerResponseDTO getCustomerById(int id);

	List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId);

	void assignLoanOfficer(int customerId, int loanOfficerId);

	void softDeleteCustomer(int customerId, int loanOfficerId);

	void selfDeleteCustomer(int customerId);

	List<CustomerResponseDTO> getAllCustomers(boolean includeDeleted);
}