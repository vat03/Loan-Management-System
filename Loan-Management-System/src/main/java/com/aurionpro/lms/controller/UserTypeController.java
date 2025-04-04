//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.UserTypeResponseDTO;
//import com.aurionpro.lms.dto.UserTypeUpdateDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/user-types")
//public class UserTypeController {
//
//	@Autowired
//	private UserTypeService userTypeService;
//
//	@PutMapping("/{userId}")
//	public ResponseEntity<UserTypeResponseDTO> updateUserProfile(@PathVariable int userId,
//			@RequestBody UserTypeUpdateDTO updateDTO) {
//		UserTypeResponseDTO responseDTO = userTypeService.updateUserProfile(userId, updateDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/{userId}")
//	public ResponseEntity<UserTypeResponseDTO> getUserProfile(@PathVariable int userId) {
//		UserTypeResponseDTO responseDTO = userTypeService.getUserProfile(userId);
//		return ResponseEntity.ok(responseDTO);
//	}
//}