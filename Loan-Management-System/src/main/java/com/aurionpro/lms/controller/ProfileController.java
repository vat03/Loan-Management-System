package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.ProfileUpdateRequestDTO;
import com.aurionpro.lms.dto.ProfileResponseDTO;
import com.aurionpro.lms.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

//	@PutMapping("/update/{userId}")
//	public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable int userId,
//			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
//		ProfileResponseDTO responseDTO = profileService.updateProfile(userId, requestDTO);
//		return ResponseEntity.ok(responseDTO);
//	}

	@PutMapping("/update/customer/{customerId}")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<ProfileResponseDTO> updateCustomerProfile(@PathVariable int customerId,
			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
		ProfileResponseDTO responseDTO = profileService.updateCustomerProfile(customerId, requestDTO);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/update/loan-officer/{loanOfficerId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<ProfileResponseDTO> updateLoanOfficerProfile(@PathVariable int loanOfficerId,
			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
		ProfileResponseDTO responseDTO = profileService.updateLoanOfficerProfile(loanOfficerId, requestDTO);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/update/admin/{adminId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ProfileResponseDTO> updateAdminProfile(@PathVariable int adminId,
			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
		ProfileResponseDTO responseDTO = profileService.updateAdminProfile(adminId, requestDTO);
		return ResponseEntity.ok(responseDTO);
	}

//	@GetMapping("/user/{userId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByUserId(@PathVariable int userId) {
//		ProfileResponseDTO responseDTO = profileService.getProfileByUserId(userId);
//		return ResponseEntity.ok(responseDTO);
//	}

	@GetMapping("/customer/{customerId}")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<ProfileResponseDTO> getProfileByCustomerId(@PathVariable int customerId) {
		ProfileResponseDTO responseDTO = profileService.getProfileByCustomerId(customerId);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/loan-officer/{loanOfficerId}")
	@PreAuthorize("hasRole('ROLE_LOAN_OFFICER')")
	public ResponseEntity<ProfileResponseDTO> getProfileByLoanOfficerId(@PathVariable int loanOfficerId) {
		ProfileResponseDTO responseDTO = profileService.getProfileByLoanOfficerId(loanOfficerId);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/admin/{adminId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ProfileResponseDTO> getProfileByAdminId(@PathVariable int adminId) {
		ProfileResponseDTO responseDTO = profileService.getProfileByAdminId(adminId);
		return ResponseEntity.ok(responseDTO);
	}
}