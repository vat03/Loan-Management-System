package com.aurionpro.lms.service;

import com.aurionpro.lms.entity.Admin;
import java.util.List;
import java.util.Optional;

public interface AdminService {
	Admin saveAdmin(Admin admin);

	Optional<Admin> getAdminById(int id);

	List<Admin> getAllAdmins();

	Admin updateAdmin(Admin admin);

	void deleteAdmin(int id);
}