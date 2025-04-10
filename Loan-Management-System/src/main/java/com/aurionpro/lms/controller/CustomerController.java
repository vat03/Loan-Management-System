//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/customers")
//public class CustomerController {
//
//	@Autowired
//	private CustomerService customerService;
//
//	@GetMapping("/getCustomerById/{id}")
//	public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable int id) {
//		CustomerResponseDTO responseDTO = customerService.getCustomerById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getCustomerByLoanOfficerId/loan-officer/{loanOfficerId}")
//	public ResponseEntity<List<CustomerResponseDTO>> getCustomersByLoanOfficerId(@PathVariable int loanOfficerId) {
//		List<CustomerResponseDTO> responseDTOs = customerService.getCustomersByLoanOfficerId(loanOfficerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//
//	@PutMapping("/{customerId}/assign-loan-officer")
//	public ResponseEntity<Void> assignLoanOfficer(@PathVariable int customerId, @RequestParam int loanOfficerId) {
//		customerService.assignLoanOfficer(customerId, loanOfficerId);
//		return ResponseEntity.noContent().build();
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.CustomerResponseDTO;
import com.aurionpro.lms.exception.ResourceNotFoundException;
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

	@GetMapping("/getCustomerById/{id}")
	public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable int id) {
		try {
			CustomerResponseDTO responseDTO = customerService.getCustomerById(id);
			return ResponseEntity.ok(responseDTO);
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error fetching customer with ID: " + id, e);
		}
	}

	@GetMapping("/getCustomerByLoanOfficerId/loan-officer/{loanOfficerId}")
	public ResponseEntity<List<CustomerResponseDTO>> getCustomersByLoanOfficerId(@PathVariable int loanOfficerId) {
		try {
			List<CustomerResponseDTO> responseDTOs = customerService.getCustomersByLoanOfficerId(loanOfficerId);
			return ResponseEntity.ok(responseDTOs);
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error fetching customers for loan officer ID: " + loanOfficerId, e);
		}
	}

	@PutMapping("/{customerId}/assign-loan-officer")
	public ResponseEntity<Void> assignLoanOfficer(@PathVariable int customerId, @RequestParam int loanOfficerId) {
		try {
			customerService.assignLoanOfficer(customerId, loanOfficerId);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error assigning loan officer to customer ID: " + customerId, e);
		}
	}
}