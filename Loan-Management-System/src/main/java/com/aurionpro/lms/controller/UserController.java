//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//	@Autowired
//	private UserService userService;
//
//	@PostMapping("/registerUser")
//	public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO requestDTO,
//			@RequestParam String roleName) {
//		UserResponseDTO responseDTO = userService.registerUser(requestDTO, roleName);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@GetMapping("getUserById/{id}")
//	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
//		UserResponseDTO responseDTO = userService.getUserById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.exception.InvalidInputException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.service.UserService;
//
//import jakarta.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//	@Autowired
//	private UserService userService;
//
//	@PostMapping("/registerUser")
//	public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO requestDTO,
//			@Valid @RequestParam String roleName) {
//		try {
//			UserResponseDTO responseDTO = userService.registerUser(requestDTO, roleName);
//			return ResponseEntity.status(201).body(responseDTO);
//		} catch (InvalidInputException | ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error registering user", e);
//		}
//	}
//
//	@GetMapping("/getUserById/{id}")
//	public ResponseEntity<UserResponseDTO> getUserById(@Valid @PathVariable int id) {
//		try {
//			UserResponseDTO responseDTO = userService.getUserById(id);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching user with ID: " + id, e);
//		}
//	}
//}

package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.LoginRequestDTO;
import com.aurionpro.lms.dto.UserRequestDTO;
import com.aurionpro.lms.dto.UserResponseDTO;
import com.aurionpro.lms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
		UserResponseDTO responseDTO = userService.getUserById(id);
		return ResponseEntity.ok(responseDTO);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
		UserResponseDTO responseDTO = userService.login(loginRequestDTO);
		return ResponseEntity.ok(responseDTO);
	}
}