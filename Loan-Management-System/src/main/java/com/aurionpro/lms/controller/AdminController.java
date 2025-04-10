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

package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.AdminResponseDTO;
import com.aurionpro.lms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

	@Autowired
	private AdminService adminService;

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
}