//package com.aurionpro.lms.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aurionpro.lms.dto.LoanRequestDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.dto.LoanUpdateDTO;
//import com.aurionpro.lms.service.LoanService;
//
//@RestController
//@RequestMapping("/api/loans")
//public class LoanController {
//
//	@Autowired
//	private LoanService loanService;
//
//	@PostMapping("/apply")
//	public ResponseEntity<LoanResponseDTO> applyForLoan(@RequestBody LoanRequestDTO requestDTO) {
//		LoanResponseDTO responseDTO = loanService.applyForLoan(requestDTO);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@PutMapping("/{id}/status")
//	public ResponseEntity<LoanResponseDTO> updateLoanStatus(@PathVariable int id,
//			@RequestBody LoanUpdateDTO updateDTO) {
//		LoanResponseDTO responseDTO = loanService.updateLoanStatus(id, updateDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getByLoanId/{id}")
//	public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable int id) {
//		LoanResponseDTO responseDTO = loanService.getLoanById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getByCustomerId/customer/{customerId}")
//	public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomerId(@PathVariable int customerId) {
//		List<LoanResponseDTO> responseDTOs = loanService.getLoansByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//
//	@GetMapping("/getByLoanOfficerId/loan-officer/{loanOfficerId}")
//	public ResponseEntity<List<LoanResponseDTO>> getLoansByLoanOfficerId(@PathVariable int loanOfficerId) {
//		List<LoanResponseDTO> responseDTOs = loanService.getLoansByLoanOfficerId(loanOfficerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//	
////	@PostMapping("/repay/{loanPaymentId}")
////    public ResponseEntity<String> repayLoanPayment(@PathVariable int loanPaymentId) {
////        loanPaymentService.processRepayment(loanPaymentId);
////        return ResponseEntity.ok("Payment processed successfully");
////    }
////
////    @GetMapping("/payments/{loanId}")
////    public ResponseEntity<List<LoanPaymentResponseDTO>> getLoanPayments(@PathVariable int loanId) {
////        List<LoanPaymentResponseDTO> payments = loanPaymentService.getPaymentsByLoanId(loanId);
////        return ResponseEntity.ok(payments);
////    }
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanRequestDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.dto.LoanUpdateDTO;
//import com.aurionpro.lms.exception.InvalidInputException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.service.LoanService;
//
//import jakarta.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/loans")
//public class LoanController {
//
//	@Autowired
//	private LoanService loanService;
//
//	@PostMapping("/apply")
//	public ResponseEntity<LoanResponseDTO> applyForLoan(@Valid @RequestBody LoanRequestDTO requestDTO) {
//		try {
//			LoanResponseDTO responseDTO = loanService.applyForLoan(requestDTO);
//			return ResponseEntity.status(201).body(responseDTO);
//		} catch (ResourceNotFoundException | InvalidInputException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error applying for loan", e);
//		}
//	}
//
//	@PutMapping("/{id}/status")
//	public ResponseEntity<LoanResponseDTO> updateLoanStatus(@Valid @PathVariable int id,
//			@RequestBody LoanUpdateDTO updateDTO) {
//		try {
//			LoanResponseDTO responseDTO = loanService.updateLoanStatus(id, updateDTO);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException | InvalidInputException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error updating loan status for ID: " + id, e);
//		}
//	}
//
//	@GetMapping("/getByLoanId/{id}")
//	public ResponseEntity<LoanResponseDTO> getLoanById(@Valid @PathVariable int id) {
//		try {
//			LoanResponseDTO responseDTO = loanService.getLoanById(id);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching loan with ID: " + id, e);
//		}
//	}
//
//	@GetMapping("/getByCustomerId/customer/{customerId}")
//	public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomerId(@Valid @PathVariable int customerId) {
//		try {
//			List<LoanResponseDTO> responseDTOs = loanService.getLoansByCustomerId(customerId);
//			return ResponseEntity.ok(responseDTOs);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching loans for customer ID: " + customerId, e);
//		}
//	}
//
//	@GetMapping("/getByLoanOfficerId/loan-officer/{loanOfficerId}")
//	public ResponseEntity<List<LoanResponseDTO>> getLoansByLoanOfficerId(@Valid @PathVariable int loanOfficerId) {
//		try {
//			List<LoanResponseDTO> responseDTOs = loanService.getLoansByLoanOfficerId(loanOfficerId);
//			return ResponseEntity.ok(responseDTOs);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching loans for loan officer ID: " + loanOfficerId, e);
//		}
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanRequestDTO;
//import com.aurionpro.lms.dto.LoanResponseDTO;
//import com.aurionpro.lms.dto.LoanUpdateDTO;
//import com.aurionpro.lms.service.LoanService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/loans")
//@CrossOrigin("http://localhost:4200")
//public class LoanController {
//
//	@Autowired
//	private LoanService loanService;
//
//	@PostMapping("/apply")
//	public ResponseEntity<LoanResponseDTO> applyForLoan(@Valid @RequestBody LoanRequestDTO requestDTO) {
//		LoanResponseDTO responseDTO = loanService.applyForLoan(requestDTO);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@PutMapping("/{id}/status")
//	public ResponseEntity<LoanResponseDTO> updateLoanStatus(@PathVariable int id,
//			@Valid @RequestBody LoanUpdateDTO updateDTO) {
//		LoanResponseDTO responseDTO = loanService.updateLoanStatus(id, updateDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getByLoanId/{id}")
//	public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable int id) {
//		LoanResponseDTO responseDTO = loanService.getLoanById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getByCustomerId/customer/{customerId}")
//	public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomerId(@PathVariable int customerId) {
//		List<LoanResponseDTO> responseDTOs = loanService.getLoansByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//
//	@GetMapping("/getByLoanOfficerId/loan-officer/{loanOfficerId}")
//	public ResponseEntity<List<LoanResponseDTO>> getLoansByLoanOfficerId(@PathVariable int loanOfficerId) {
//		List<LoanResponseDTO> responseDTOs = loanService.getLoansByLoanOfficerId(loanOfficerId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//}

package com.aurionpro.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.LoanRequestDTO;
import com.aurionpro.lms.dto.LoanResponseDTO;
import com.aurionpro.lms.dto.LoanUpdateDTO;
import com.aurionpro.lms.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin("http://localhost:4200")
public class LoanController {

	@Autowired
	private LoanService loanService;

	@PostMapping("/apply")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<LoanResponseDTO> applyForLoan(@Valid @RequestBody LoanRequestDTO requestDTO) {
		LoanResponseDTO responseDTO = loanService.applyForLoan(requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

//	@PutMapping("/{id}/status")
//	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
//	public ResponseEntity<LoanResponseDTO> updateLoanStatus(@PathVariable int id,
//			@Valid @RequestBody LoanUpdateDTO updateDTO) {
//		LoanResponseDTO responseDTO = loanService.updateLoanStatus(id, updateDTO);
//		return ResponseEntity.ok(responseDTO);
//	}

	@GetMapping("/getByLoanId/{id}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable int id) {
		LoanResponseDTO responseDTO = loanService.getLoanById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getByCustomerId/customer/{customerId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomerId(@PathVariable int customerId) {
		List<LoanResponseDTO> responseDTOs = loanService.getLoansByCustomerId(customerId);
		return ResponseEntity.ok(responseDTOs);
	}

	@GetMapping("/getByLoanOfficerId/loan-officer/{loanOfficerId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER', 'ROLE_ADMIN')")
	public ResponseEntity<List<LoanResponseDTO>> getLoansByLoanOfficerId(@PathVariable int loanOfficerId) {
		List<LoanResponseDTO> responseDTOs = loanService.getLoansByLoanOfficerId(loanOfficerId);
		return ResponseEntity.ok(responseDTOs);
	}
}