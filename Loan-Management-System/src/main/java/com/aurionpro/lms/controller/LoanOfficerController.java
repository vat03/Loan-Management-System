//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
//import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
//import com.aurionpro.lms.service.LoanOfficerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/loan-officers")
//public class LoanOfficerController {
//
//	@Autowired
//	private LoanOfficerService loanOfficerService;
//
//	@PostMapping("/addLoanOfficer")
//	public ResponseEntity<LoanOfficerResponseDTO> addLoanOfficer(@RequestParam int adminId,
//			@RequestBody LoanOfficerRequestDTO requestDTO) {
//		LoanOfficerResponseDTO responseDTO = loanOfficerService.addLoanOfficer(adminId, requestDTO);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@GetMapping("/getLoanOfficerById/{id}")
//	public ResponseEntity<LoanOfficerResponseDTO> getLoanOfficerById(@PathVariable int id) {
//		LoanOfficerResponseDTO responseDTO = loanOfficerService.getLoanOfficerById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/admin/{adminId}")
//	public ResponseEntity<List<LoanOfficerResponseDTO>> getLoanOfficersByAdminId(@PathVariable int adminId) {
//		List<LoanOfficerResponseDTO> responseDTOs = loanOfficerService.getLoanOfficersByAdminId(adminId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
//import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
//import com.aurionpro.lms.exception.InvalidInputException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.service.LoanOfficerService;
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
//@RequestMapping("/api/loan-officers")
//public class LoanOfficerController {
//
//	@Autowired
//	private LoanOfficerService loanOfficerService;
//
//	@PostMapping("/addLoanOfficer")
//	public ResponseEntity<LoanOfficerResponseDTO> addLoanOfficer(@Valid @RequestParam int adminId,
//			@Valid @RequestBody LoanOfficerRequestDTO requestDTO) {
//		try {
//			LoanOfficerResponseDTO responseDTO = loanOfficerService.addLoanOfficer(adminId, requestDTO);
//			return ResponseEntity.status(201).body(responseDTO);
//		} catch (ResourceNotFoundException | InvalidInputException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error adding loan officer", e);
//		}
//	}
//
//	@GetMapping("/getLoanOfficerById/{id}")
//	public ResponseEntity<LoanOfficerResponseDTO> getLoanOfficerById(@Valid @PathVariable int id) {
//		try {
//			LoanOfficerResponseDTO responseDTO = loanOfficerService.getLoanOfficerById(id);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching loan officer with ID: " + id, e);
//		}
//	}
//
//	@GetMapping("/admin/{adminId}")
//	public ResponseEntity<List<LoanOfficerResponseDTO>> getLoanOfficersByAdminId(@Valid @PathVariable int adminId) {
//		try {
//			List<LoanOfficerResponseDTO> responseDTOs = loanOfficerService.getLoanOfficersByAdminId(adminId);
//			return ResponseEntity.ok(responseDTOs);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching loan officers for admin ID: " + adminId, e);
//		}
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
import com.aurionpro.lms.service.LoanOfficerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-officers")
//@CrossOrigin("http://localhost:4200")
//@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
//		RequestMethod.DELETE, RequestMethod.OPTIONS })
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class LoanOfficerController {

	@Autowired
	private LoanOfficerService loanOfficerService;

	@PostMapping("/addLoanOfficer")
	public ResponseEntity<LoanOfficerResponseDTO> addLoanOfficer(@RequestParam int adminId,
			@Valid @RequestBody LoanOfficerRequestDTO requestDTO) {
		LoanOfficerResponseDTO responseDTO = loanOfficerService.addLoanOfficer(adminId, requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/getLoanOfficerById/{id}")
	public ResponseEntity<LoanOfficerResponseDTO> getLoanOfficerById(@PathVariable int id) {
		LoanOfficerResponseDTO responseDTO = loanOfficerService.getLoanOfficerById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getAllLoanOfficers")
	public ResponseEntity<List<LoanOfficerResponseDTO>> getAllLoanOfficers() {
		return ResponseEntity.ok(loanOfficerService.getAllLoanOfficers());
	}

	// --not needed
	@GetMapping("/admin/{adminId}")
	public ResponseEntity<List<LoanOfficerResponseDTO>> getLoanOfficersByAdminId(@PathVariable int adminId) {
		List<LoanOfficerResponseDTO> responseDTOs = loanOfficerService.getLoanOfficersByAdminId(adminId);
		return ResponseEntity.ok(responseDTOs);
	}
}