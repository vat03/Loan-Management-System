package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.CustomerResponseDTO;
import com.aurionpro.lms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable int id) {
		CustomerResponseDTO responseDTO = customerService.getCustomerById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/loan-officer/{loanOfficerId}")
	public ResponseEntity<List<CustomerResponseDTO>> getCustomersByLoanOfficerId(@PathVariable int loanOfficerId) {
		List<CustomerResponseDTO> responseDTOs = customerService.getCustomersByLoanOfficerId(loanOfficerId);
		return ResponseEntity.ok(responseDTOs);
	}

	@PutMapping("/{customerId}/assign-loan-officer")
	public ResponseEntity<Void> assignLoanOfficer(@PathVariable int customerId, @RequestParam int loanOfficerId) {
		customerService.assignLoanOfficer(customerId, loanOfficerId);
		return ResponseEntity.noContent().build();
	}
}