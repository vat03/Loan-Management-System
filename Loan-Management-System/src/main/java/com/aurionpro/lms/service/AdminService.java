package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.AdminResponseDTO;

import java.util.List;

public interface AdminService {
	AdminResponseDTO getAdminById(int id);

	List<AdminResponseDTO> getAllAdmins();
}