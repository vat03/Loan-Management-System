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

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
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
//		try {
//			CustomerResponseDTO responseDTO = customerService.getCustomerById(id);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching customer with ID: " + id, e);
//		}
//	}
//
//	@GetMapping("/getCustomerByLoanOfficerId/loan-officer/{loanOfficerId}")
//	public ResponseEntity<List<CustomerResponseDTO>> getCustomersByLoanOfficerId(@PathVariable int loanOfficerId) {
//		try {
//			List<CustomerResponseDTO> responseDTOs = customerService.getCustomersByLoanOfficerId(loanOfficerId);
//			return ResponseEntity.ok(responseDTOs);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching customers for loan officer ID: " + loanOfficerId, e);
//		}
//	}
//
//	@PutMapping("/{customerId}/assign-loan-officer")
//	public ResponseEntity<Void> assignLoanOfficer(@PathVariable int customerId, @RequestParam int loanOfficerId) {
//		try {
//			customerService.assignLoanOfficer(customerId, loanOfficerId);
//			return ResponseEntity.noContent().build();
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error assigning loan officer to customer ID: " + customerId, e);
//		}
//	}
//}

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
//@CrossOrigin("http://localhost:4200")
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.CustomerResponseDTO;
import com.aurionpro.lms.dto.CustomerSoftDeleteRequest;
import com.aurionpro.lms.dto.SelfDeleteRequest;
import com.aurionpro.lms.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("http://localhost:4200")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/getCustomerById/{id}")
	public ResponseEntity<CustomerResponseDTO> getCustomerById(@Valid @PathVariable int id) {
		CustomerResponseDTO responseDTO = customerService.getCustomerById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getCustomerByLoanOfficerId/loan-officer/{loanOfficerId}")
	public ResponseEntity<List<CustomerResponseDTO>> getCustomersByLoanOfficerId(@Valid @PathVariable int loanOfficerId) {
		List<CustomerResponseDTO> responseDTOs = customerService.getCustomersByLoanOfficerId(loanOfficerId);
		return ResponseEntity.ok(responseDTOs);
	}

	@PutMapping("/{customerId}/assign-loan-officer")
	public ResponseEntity<Void> assignLoanOfficer(@Valid @PathVariable int customerId, @Valid @RequestParam int loanOfficerId) {
		customerService.assignLoanOfficer(customerId, loanOfficerId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/soft-delete")
	public ResponseEntity<Void> softDeleteCustomer(@Valid @PathVariable int id,
			@Valid @RequestBody CustomerSoftDeleteRequest request) {
		customerService.softDeleteCustomer(id, request.getLoanOfficerId());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/me/soft-delete")
	public ResponseEntity<Void> selfDeleteCustomer(@Valid @RequestBody SelfDeleteRequest request) {
		customerService.selfDeleteCustomer(request.getCustomerId());
		return ResponseEntity.noContent().build();
	}
}