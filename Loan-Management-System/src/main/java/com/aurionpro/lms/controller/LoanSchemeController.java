//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//import com.aurionpro.lms.service.LoanSchemeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/loan-schemes")
//public class LoanSchemeController {
//
//	@Autowired
//	private LoanSchemeService loanSchemeService;
//
//	@PostMapping("/create")
//	public ResponseEntity<LoanSchemeResponseDTO> createLoanScheme(@RequestParam int adminId,
//			@RequestBody LoanSchemeRequestDTO requestDTO) {
//		LoanSchemeResponseDTO responseDTO = loanSchemeService.createLoanScheme(adminId, requestDTO);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@GetMapping("/getByLoanId/{id}")
//	public ResponseEntity<LoanSchemeResponseDTO> getLoanSchemeById(@PathVariable int id) {
//		LoanSchemeResponseDTO responseDTO = loanSchemeService.getLoanSchemeById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getByAdminId/admin/{adminId}")
//	public ResponseEntity<List<LoanSchemeResponseDTO>> getLoanSchemesByAdminId(@PathVariable int adminId) {
//		List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getLoanSchemesByAdminId(adminId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//import com.aurionpro.lms.service.LoanSchemeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/loan-schemes")
//public class LoanSchemeController {
//
//	@Autowired
//	private LoanSchemeService loanSchemeService;
//
//	@PostMapping("/create")
//	public ResponseEntity<LoanSchemeResponseDTO> createLoanScheme(@RequestParam int adminId,
//			@RequestBody LoanSchemeRequestDTO requestDTO) {
//		LoanSchemeResponseDTO responseDTO = loanSchemeService.createLoanScheme(adminId, requestDTO);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@GetMapping("/getByLoanId/{id}")
//	public ResponseEntity<LoanSchemeResponseDTO> getLoanSchemeById(@PathVariable int id) {
//		LoanSchemeResponseDTO responseDTO = loanSchemeService.getLoanSchemeById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getByAdminId/admin/{adminId}")
//	public ResponseEntity<List<LoanSchemeResponseDTO>> getLoanSchemesByAdminId(@PathVariable int adminId) {
//		List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getLoanSchemesByAdminId(adminId);
//		return ResponseEntity.ok(responseDTOs);
//	}
//
//	@GetMapping("/all")
//	public ResponseEntity<List<LoanSchemeResponseDTO>> getAllLoanSchemes() {
//		List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getLoanSchemesByAdminId(0); // 0 for all if no
//																									// filter
//		return ResponseEntity.ok(responseDTOs);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//import com.aurionpro.lms.exception.InvalidInputException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.service.LoanSchemeService;
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
//@RequestMapping("/api/loan-schemes")
//public class LoanSchemeController {
//
//	@Autowired
//	private LoanSchemeService loanSchemeService;
//
//	@PostMapping("/create")
//	public ResponseEntity<LoanSchemeResponseDTO> createLoanScheme(@Valid @RequestParam int adminId,
//			@Valid @RequestBody LoanSchemeRequestDTO requestDTO) {
//		try {
//			LoanSchemeResponseDTO responseDTO = loanSchemeService.createLoanScheme(adminId, requestDTO);
//			return ResponseEntity.status(201).body(responseDTO);
//		} catch (ResourceNotFoundException | InvalidInputException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error creating loan scheme", e);
//		}
//	}
//
//	@GetMapping("/getByLoanId/{id}")
//	public ResponseEntity<LoanSchemeResponseDTO> getLoanSchemeById(@Valid @PathVariable int id) {
//		try {
//			LoanSchemeResponseDTO responseDTO = loanSchemeService.getLoanSchemeById(id);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching loan scheme with ID: " + id, e);
//		}
//	}
//
//	@GetMapping("/getByAdminId/admin/{adminId}")
//	public ResponseEntity<List<LoanSchemeResponseDTO>> getLoanSchemesByAdminId(@Valid @PathVariable int adminId) {
//		try {
//			List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getLoanSchemesByAdminId(adminId);
//			return ResponseEntity.ok(responseDTOs);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching loan schemes for admin ID: " + adminId, e);
//		}
//	}
//
//	@GetMapping("/all")
//	public ResponseEntity<List<LoanSchemeResponseDTO>> getAllLoanSchemes() {
//		try {
//			List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getLoanSchemesByAdminId(0); // 0 for all
//			return ResponseEntity.ok(responseDTOs);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching all loan schemes", e);
//		}
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
import com.aurionpro.lms.service.LoanSchemeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-schemes")
public class LoanSchemeController {

	@Autowired
	private LoanSchemeService loanSchemeService;

	@PostMapping("/create")
	public ResponseEntity<LoanSchemeResponseDTO> createLoanScheme(@RequestParam int adminId,
			@Valid @RequestBody LoanSchemeRequestDTO requestDTO) {
		LoanSchemeResponseDTO responseDTO = loanSchemeService.createLoanScheme(adminId, requestDTO);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/getByLoanId/{id}")
	public ResponseEntity<LoanSchemeResponseDTO> getLoanSchemeById(@PathVariable int id) {
		LoanSchemeResponseDTO responseDTO = loanSchemeService.getLoanSchemeById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getByAdminId/admin/{adminId}")
	public ResponseEntity<List<LoanSchemeResponseDTO>> getLoanSchemesByAdminId(@PathVariable int adminId) {
		List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getLoanSchemesByAdminId(adminId);
		return ResponseEntity.ok(responseDTOs);
	}

	@GetMapping("/all")
	public ResponseEntity<List<LoanSchemeResponseDTO>> getAllLoanSchemes() {
		List<LoanSchemeResponseDTO> responseDTOs = loanSchemeService.getLoanSchemesByAdminId(0);
		return ResponseEntity.ok(responseDTOs);
	}
}