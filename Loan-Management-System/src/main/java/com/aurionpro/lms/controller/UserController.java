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

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.LoginRequestDTO;
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/users")
//@CrossOrigin("http://localhost:4200")
//public class UserController {
//
//	@Autowired
//	private UserService userService;
//
//	@PostMapping("/registerUser")
//	public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO requestDTO,
//			@RequestParam String roleName) {
//		UserResponseDTO responseDTO = userService.registerUser(requestDTO, roleName);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@GetMapping("/getUserById/{id}")
//	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
//		UserResponseDTO responseDTO = userService.getUserById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@PostMapping("/login")
//	public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
//		UserResponseDTO responseDTO = userService.login(loginRequestDTO);
//		return ResponseEntity.ok(responseDTO);
//	}
//}

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

//package com.aurionpro.lms.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aurionpro.lms.dto.JwtResponseDTO;
//import com.aurionpro.lms.dto.LoginRequestDTO;
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.service.UserService;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/api/users")
//@CrossOrigin("http://localhost:4200")
//public class UserController {
//
//	@Autowired
//	private UserService userService;
//
//	@PostMapping("/registerUser")
//	public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO requestDTO,
//			@RequestParam String roleName) {
//		UserResponseDTO responseDTO = userService.registerUser(requestDTO, roleName);
//		return ResponseEntity.status(201).body(responseDTO);
//	}
//
//	@GetMapping("/getUserById/{id}")
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LOAN_OFFICER') or (hasRole('ROLE_CUSTOMER') and #id == authentication.principal.userId)")
//	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
//		UserResponseDTO responseDTO = userService.getUserById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
//		try {
//			JwtResponseDTO responseDTO = userService.login(loginRequestDTO);
//			return ResponseEntity.ok(responseDTO);
//		} catch (BadCredentialsException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//		}
//	}
//	
//	@GetMapping("/test-token")
//    @CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
//    public ResponseEntity<JwtResponseDTO> getTestToken() {
//        // Hardcode customer1 for testing
//        JwtResponseDTO responseDTO = userService.generateTestToken(4, "vat03", "ROLE_CUSTOMER");
//        return ResponseEntity.ok(responseDTO);
//    }
//}