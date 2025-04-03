package com.aurionpro.lms.controller;

import com.aurionpro.lms.dto.AdminResponseDTO;
import com.aurionpro.lms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("getAdminById/{id}")
	public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable int id) {
		AdminResponseDTO responseDTO = adminService.getAdminById(id);
		return ResponseEntity.ok(responseDTO);
	}
}