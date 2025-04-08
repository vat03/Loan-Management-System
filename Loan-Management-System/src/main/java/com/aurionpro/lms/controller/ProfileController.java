package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.ProfileUpdateRequestDTO;
import com.aurionpro.lms.dto.ProfileResponseDTO;
import com.aurionpro.lms.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@PutMapping("/update/{userId}")
	public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable int userId,
			@RequestBody ProfileUpdateRequestDTO requestDTO) {
		ProfileResponseDTO responseDTO = profileService.updateProfile(userId, requestDTO);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<ProfileResponseDTO> getProfileByUserId(@PathVariable int userId) {
		ProfileResponseDTO responseDTO = profileService.getProfileByUserId(userId);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<ProfileResponseDTO> getProfileByCustomerId(@PathVariable int customerId) {
		ProfileResponseDTO responseDTO = profileService.getProfileByCustomerId(customerId);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/loan-officer/{loanOfficerId}")
	public ResponseEntity<ProfileResponseDTO> getProfileByLoanOfficerId(@PathVariable int loanOfficerId) {
		ProfileResponseDTO responseDTO = profileService.getProfileByLoanOfficerId(loanOfficerId);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/admin/{adminId}")
	public ResponseEntity<ProfileResponseDTO> getProfileByAdminId(@PathVariable int adminId) {
		ProfileResponseDTO responseDTO = profileService.getProfileByAdminId(adminId);
		return ResponseEntity.ok(responseDTO);
	}
}