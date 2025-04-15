package com.aurionpro.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.CustomerResponseDTO;
import com.aurionpro.lms.dto.SelfDeleteRequest;
import com.aurionpro.lms.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/getCustomerById/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LOAN_OFFICER') or (hasRole('ROLE_CUSTOMER') and #id == authentication.principal.userId)")
	public ResponseEntity<CustomerResponseDTO> getCustomerById(@Valid @PathVariable int id) {
		CustomerResponseDTO responseDTO = customerService.getCustomerById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getCustomerByLoanOfficerId/loan-officer/{loanOfficerId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<List<CustomerResponseDTO>> getCustomersByLoanOfficerId(
			@Valid @PathVariable int loanOfficerId) {
		List<CustomerResponseDTO> responseDTOs = customerService.getCustomersByLoanOfficerId(loanOfficerId);
		return ResponseEntity.ok(responseDTOs);
	}

//	@PutMapping("/{customerId}/assign-loan-officer")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	public ResponseEntity<Void> assignLoanOfficer(@Valid @PathVariable int customerId,
//			@Valid @RequestParam int loanOfficerId) {
//		customerService.assignLoanOfficer(customerId, loanOfficerId);
//		return ResponseEntity.noContent().build();
//	}

	@PutMapping("/{id}/soft-delete")
	@PreAuthorize("@customerSecurityService.isAssignedLoanOfficer(authentication, #id)")
	public ResponseEntity<Void> softDeleteCustomer(@Valid @PathVariable int id) {
		customerService.softDeleteCustomer(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/me/soft-delete")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<Void> softDeleteCustomer(@Valid @RequestBody SelfDeleteRequest request) {
		customerService.selfDeleteCustomer(request.getCustomerId());
		return ResponseEntity.noContent().build();
	}
}