//package com.aurionpro.lms.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aurionpro.lms.dto.AdminResponseDTO;
//import com.aurionpro.lms.service.AdminService;
//
//@RestController
//@RequestMapping("/api/admins")
//public class AdminController {
//
//	@Autowired
//	private AdminService adminService;
//
//	@GetMapping("getAdminById/{id}")
//	public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable int id) {
//		AdminResponseDTO responseDTO = adminService.getAdminById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//	
//	@GetMapping("/getAllAdmins")
//    public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
//        List<AdminResponseDTO> admins = adminService.getAllAdmins();
//        return ResponseEntity.ok(admins);
//    }
//}

//package com.aurionpro.lms.controller;
//
//import com.aurionpro.lms.dto.AdminResponseDTO;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admins")
//public class AdminController {
//
//	@Autowired
//	private AdminService adminService;
//
//	@GetMapping("/getAdminById/{id}")
//	public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable int id) {
//		try {
//			AdminResponseDTO responseDTO = adminService.getAdminById(id);
//			return ResponseEntity.ok(responseDTO);
//		} catch (ResourceNotFoundException e) {
//			throw e; 
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching admin with ID: " + id, e);
//		}
//	}
//
//	@GetMapping("/getAllAdmins")
//	public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
//		try {
//			List<AdminResponseDTO> admins = adminService.getAllAdmins();
//			return ResponseEntity.ok(admins);
//		} catch (ResourceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error fetching all admins", e);
//		}
//	}
//}

//package com.aurionpro.lms.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aurionpro.lms.dto.AdminResponseDTO;
//import com.aurionpro.lms.service.AdminService;
//
//@RestController
//@RequestMapping("/api/admins")
//@CrossOrigin("http://localhost:4200")
//public class AdminController {
//
//	@Autowired
//	private AdminService adminService;
//
//	@GetMapping("/getAdminById/{id}")
//	public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable int id) {
//		AdminResponseDTO responseDTO = adminService.getAdminById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getAllAdmins")
//	public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
//		List<AdminResponseDTO> admins = adminService.getAllAdmins();
//		return ResponseEntity.ok(admins);
//	}
//}

//package com.aurionpro.lms.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aurionpro.lms.dto.AdminResponseDTO;
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//import com.aurionpro.lms.dto.LoanSchemeSoftDeleteRequest;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.service.AdminService;
//import com.aurionpro.lms.service.CustomerService;
//import com.aurionpro.lms.service.LoanSchemeService;
//import com.aurionpro.lms.service.UserService;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/api/admin")
//@CrossOrigin("http://localhost:4200")
//public class AdminController {
//
//	@Autowired
//	private AdminService adminService;
//
//	@Autowired
//	private CustomerService customerService;
//
//	@Autowired
//	private LoanSchemeService loanSchemeService;
//
//	@Autowired
//	private UserService userService;
//
//	@GetMapping("/getAdminById/{id}")
//	public ResponseEntity<AdminResponseDTO> getAdminById(@Valid @PathVariable int id) {
//		AdminResponseDTO responseDTO = adminService.getAdminById(id);
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/getAllAdmins")
//	public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
//		List<AdminResponseDTO> admins = adminService.getAllAdmins();
//		return ResponseEntity.ok(admins);
//	}
//
//	@GetMapping("/customers")
//	public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
//		List<CustomerResponseDTO> customers = customerService.getAllCustomers(true);
//		return ResponseEntity.ok(customers);
//	}
//
//	@PutMapping("/loan-schemes/{id}/soft-delete")
//	public ResponseEntity<Void> softDeleteLoanScheme(@Valid @PathVariable int id,
//			@RequestBody LoanSchemeSoftDeleteRequest request) {
//		loanSchemeService.softDeleteLoanScheme(id, request.getAdminId());
//		return ResponseEntity.noContent().build();
//	}
//
//	@GetMapping("/loan-schemes")
//	public ResponseEntity<List<LoanSchemeResponseDTO>> getAllLoanSchemes() {
//		List<LoanSchemeResponseDTO> schemes = loanSchemeService.getAllLoanSchemesForAdmin(true);
//		return ResponseEntity.ok(schemes);
//	}
//
//	@GetMapping("/users")
//	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
//		List<UserResponseDTO> users = userService.getAllUsers(true);
//		return ResponseEntity.ok(users);
//	}
//}

package com.aurionpro.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.lms.dto.AdminResponseDTO;
import com.aurionpro.lms.dto.CustomerResponseDTO;
import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
import com.aurionpro.lms.dto.LoanSchemeSoftDeleteRequest;
import com.aurionpro.lms.dto.UserResponseDTO;
import com.aurionpro.lms.service.AdminService;
import com.aurionpro.lms.service.CustomerService;
import com.aurionpro.lms.service.LoanSchemeService;
import com.aurionpro.lms.service.UserService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private LoanSchemeService loanSchemeService;

	@Autowired
	private UserService userService;

	@GetMapping("/getAdminById/{id}")
	public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable int id) {
		AdminResponseDTO responseDTO = adminService.getAdminById(id);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getAllAdmins")
	public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
		List<AdminResponseDTO> admins = adminService.getAllAdmins();
		return ResponseEntity.ok(admins);
	}

	@GetMapping("/customers")
	public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
		List<CustomerResponseDTO> customers = customerService.getAllCustomers(true);
		return ResponseEntity.ok(customers);
	}

	@PutMapping("/loan-schemes/{id}/soft-delete")
	public ResponseEntity<Void> softDeleteLoanScheme(@PathVariable int id,
			@RequestBody LoanSchemeSoftDeleteRequest request) {
		loanSchemeService.softDeleteLoanScheme(id, request.getAdminId());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/loan-schemes")
	public ResponseEntity<List<LoanSchemeResponseDTO>> getAllLoanSchemes() {
		List<LoanSchemeResponseDTO> schemes = loanSchemeService.getAllLoanSchemesForAdmin(true);
		return ResponseEntity.ok(schemes);
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		List<UserResponseDTO> users = userService.getAllUsers(true);
		return ResponseEntity.ok(users);
	}
}