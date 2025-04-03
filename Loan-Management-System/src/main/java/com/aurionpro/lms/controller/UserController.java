package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.UserRequestDTO;
import com.aurionpro.lms.dto.UserResponseDTO;
import com.aurionpro.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/registerUser")
	public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO requestDTO,
			@RequestParam String roleName) {
		UserResponseDTO responseDTO = userService.registerUser(requestDTO, roleName);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("getUserById/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
		UserResponseDTO responseDTO = userService.getUserById(id);
		return ResponseEntity.ok(responseDTO);
	}
}