//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.ProfileUpdateRequestDTO;
//import com.aurionpro.lms.dto.ProfileResponseDTO;
//import com.aurionpro.lms.service.ProfileService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/profile")
//public class ProfileController {
//
//	@Autowired
//	private ProfileService profileService;
//
//	@PutMapping("/update/{userId}")
//	public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable int userId,
//			@RequestBody ProfileUpdateRequestDTO requestDTO) {
//		ProfileResponseDTO responseDTO = profileService.updateProfile(userId, requestDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/user/{userId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByUserId(@PathVariable int userId) {
//		ProfileResponseDTO responseDTO = profileService.getProfileByUserId(userId);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/customer/{customerId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByCustomerId(@PathVariable int customerId) {
//		ProfileResponseDTO responseDTO = profileService.getProfileByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/loan-officer/{loanOfficerId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByLoanOfficerId(@PathVariable int loanOfficerId) {
//		ProfileResponseDTO responseDTO = profileService.getProfileByLoanOfficerId(loanOfficerId);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/admin/{adminId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByAdminId(@PathVariable int adminId) {
//		ProfileResponseDTO responseDTO = profileService.getProfileByAdminId(adminId);
//		return ResponseEntity.ok(responseDTO);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.ProfileUpdateRequestDTO;
//import com.aurionpro.lms.dto.ProfileResponseDTO;
//import com.aurionpro.lms.service.ProfileService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/profile")
//public class ProfileController {
//
//	@Autowired
//	private ProfileService profileService;
//
//	@PutMapping("/update/{userId}")
//	public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable int userId,
//			@RequestBody ProfileUpdateRequestDTO requestDTO) {
//		ProfileResponseDTO responseDTO = profileService.updateProfile(userId, requestDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@PutMapping("/update/customer/{customerId}")
//	public ResponseEntity<ProfileResponseDTO> updateCustomerProfile(@PathVariable int customerId,
//			@RequestBody ProfileUpdateRequestDTO requestDTO) {
//		ProfileResponseDTO responseDTO = profileService.updateCustomerProfile(customerId, requestDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@PutMapping("/update/loan-officer/{loanOfficerId}")
//	public ResponseEntity<ProfileResponseDTO> updateLoanOfficerProfile(@PathVariable int loanOfficerId,
//			@RequestBody ProfileUpdateRequestDTO requestDTO) {
//		ProfileResponseDTO responseDTO = profileService.updateLoanOfficerProfile(loanOfficerId, requestDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@PutMapping("/update/admin/{adminId}")
//	public ResponseEntity<ProfileResponseDTO> updateAdminProfile(@PathVariable int adminId,
//			@RequestBody ProfileUpdateRequestDTO requestDTO) {
//		ProfileResponseDTO responseDTO = profileService.updateAdminProfile(adminId, requestDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/user/{userId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByUserId(@PathVariable int userId) {
//		ProfileResponseDTO responseDTO = profileService.getProfileByUserId(userId);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/customer/{customerId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByCustomerId(@PathVariable int customerId) {
//		ProfileResponseDTO responseDTO = profileService.getProfileByCustomerId(customerId);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/loan-officer/{loanOfficerId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByLoanOfficerId(@PathVariable int loanOfficerId) {
//		ProfileResponseDTO responseDTO = profileService.getProfileByLoanOfficerId(loanOfficerId);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/admin/{adminId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByAdminId(@PathVariable int adminId) {
//		ProfileResponseDTO responseDTO = profileService.getProfileByAdminId(adminId);
//		return ResponseEntity.ok(responseDTO);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.ProfileUpdateRequestDTO;
//import com.aurionpro.lms.dto.ProfileResponseDTO;
//import com.aurionpro.lms.exception.InvalidInputException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.service.ProfileService;
//
//import jakarta.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/profile")
//public class ProfileController {
//
//	@Autowired
//	private ProfileService profileService;
//
//	@PutMapping("/update/{userId}")
//	public ResponseEntity<ProfileResponseDTO> updateProfile(@Valid @PathVariable int userId,
//			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
//		try {
//			ProfileResponseDTO responseDTO = profileService.updateProfile(userId, requestDTO);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException | InvalidInputException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error updating profile for user ID: " + userId, e);
//		}
//	}
//
//	@PutMapping("/update/customer/{customerId}")
//	public ResponseEntity<ProfileResponseDTO> updateCustomerProfile(@Valid @PathVariable int customerId,
//			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
//		try {
//			ProfileResponseDTO responseDTO = profileService.updateCustomerProfile(customerId, requestDTO);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException | InvalidInputException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error updating customer profile for ID: " + customerId, e);
//		}
//	}
//
//	@PutMapping("/update/loan-officer/{loanOfficerId}")
//	public ResponseEntity<ProfileResponseDTO> updateLoanOfficerProfile(@Valid @PathVariable int loanOfficerId,
//			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
//		try {
//			ProfileResponseDTO responseDTO = profileService.updateLoanOfficerProfile(loanOfficerId, requestDTO);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException | InvalidInputException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error updating loan officer profile for ID: " + loanOfficerId, e);
//		}
//	}
//
//	@PutMapping("/update/admin/{adminId}")
//	public ResponseEntity<ProfileResponseDTO> updateAdminProfile(@Valid @PathVariable int adminId,
//			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
//		try {
//			ProfileResponseDTO responseDTO = profileService.updateAdminProfile(adminId, requestDTO);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException | InvalidInputException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error updating admin profile for ID: " + adminId, e);
//		}
//	}
//
//	@GetMapping("/user/{userId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByUserId(@Valid @PathVariable int userId) {
//		try {
//			ProfileResponseDTO responseDTO = profileService.getProfileByUserId(userId);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching profile for user ID: " + userId, e);
//		}
//	}
//
//	@GetMapping("/customer/{customerId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByCustomerId(@Valid @PathVariable int customerId) {
//		try {
//			ProfileResponseDTO responseDTO = profileService.getProfileByCustomerId(customerId);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching profile for customer ID: " + customerId, e);
//		}
//	}
//
//	@GetMapping("/loan-officer/{loanOfficerId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByLoanOfficerId(@Valid @PathVariable int loanOfficerId) {
//		try {
//			ProfileResponseDTO responseDTO = profileService.getProfileByLoanOfficerId(loanOfficerId);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching profile for loan officer ID: " + loanOfficerId, e);
//		}
//	}
//
//	@GetMapping("/admin/{adminId}")
//	public ResponseEntity<ProfileResponseDTO> getProfileByAdminId(@Valid @PathVariable int adminId) {
//		try {
//			ProfileResponseDTO responseDTO = profileService.getProfileByAdminId(adminId);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching profile for admin ID: " + adminId, e);
//		}
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.ProfileUpdateRequestDTO;
import com.aurionpro.lms.dto.ProfileResponseDTO;
import com.aurionpro.lms.service.ProfileService;
import jakarta.validation.Valid;
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
			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
		ProfileResponseDTO responseDTO = profileService.updateProfile(userId, requestDTO);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/update/customer/{customerId}")
	public ResponseEntity<ProfileResponseDTO> updateCustomerProfile(@PathVariable int customerId,
			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
		ProfileResponseDTO responseDTO = profileService.updateCustomerProfile(customerId, requestDTO);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/update/loan-officer/{loanOfficerId}")
	public ResponseEntity<ProfileResponseDTO> updateLoanOfficerProfile(@PathVariable int loanOfficerId,
			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
		ProfileResponseDTO responseDTO = profileService.updateLoanOfficerProfile(loanOfficerId, requestDTO);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/update/admin/{adminId}")
	public ResponseEntity<ProfileResponseDTO> updateAdminProfile(@PathVariable int adminId,
			@Valid @RequestBody ProfileUpdateRequestDTO requestDTO) {
		ProfileResponseDTO responseDTO = profileService.updateAdminProfile(adminId, requestDTO);
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