package com.aurionpro.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.JwtResponseDTO;
import com.aurionpro.lms.dto.LoginRequestDTO;
import com.aurionpro.lms.dto.UserRequestDTO;
import com.aurionpro.lms.dto.UserResponseDTO;
import com.aurionpro.lms.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/registerUser")
	public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO requestDTO,
			@RequestParam String roleName) {
		UserResponseDTO responseDTO = userService.registerUser(requestDTO, roleName);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/getUserById/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LOAN_OFFICER')")
	// or (hasRole('ROLE_CUSTOMER') and #id == authentication.principal.userId)
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
		UserResponseDTO responseDTO = userService.getUserById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
		JwtResponseDTO responseDTO = userService.login(loginRequestDTO);
		return ResponseEntity.ok(responseDTO);
	}
}