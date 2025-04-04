package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.AdminResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.entity.LoanScheme;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.repository.AdminRepository;
import com.aurionpro.lms.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired // Fixed: Autowire AdminRepository
	private AdminRepository adminRepository;

	private ModelMapper mapper;

	private AdminServiceImpl() {
		this.mapper = new ModelMapper();
	}

	@Override
	public AdminResponseDTO getAdminById(int id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new RuntimeException("User not found with ID: " + id);
		}
		User user = userOpt.get();
		Optional<Admin> adminOpt = adminRepository.findByUserId(id);
		if (adminOpt.isEmpty()) {
			throw new RuntimeException("Admin not found with ID: " + id);
		}
		Admin admin = adminOpt.get();

		mapper.typeMap(User.class, AdminResponseDTO.class)
				.addMapping(
						src -> admin.getLoanOfficers().stream().map(LoanOfficer::getId).collect(Collectors.toList()),
						AdminResponseDTO::setLoanOfficerIds)
				.addMapping(src -> admin.getLoanSchemes().stream().map(LoanScheme::getId).collect(Collectors.toList()),
						AdminResponseDTO::setLoanSchemeIds);
		return mapper.map(user, AdminResponseDTO.class);
	}

	@Override
	public List<AdminResponseDTO> getAllAdmins() {
		List<Admin> admins = adminRepository.findAll();
		return admins.stream().map(admin -> {
			User user = admin.getUser();
			mapper.typeMap(User.class, AdminResponseDTO.class)
					.addMapping(src -> admin.getLoanOfficers().stream().map(LoanOfficer::getId)
							.collect(Collectors.toList()), AdminResponseDTO::setLoanOfficerIds)
					.addMapping(
							src -> admin.getLoanSchemes().stream().map(LoanScheme::getId).collect(Collectors.toList()),
							AdminResponseDTO::setLoanSchemeIds);
			return mapper.map(user, AdminResponseDTO.class);
		}).collect(Collectors.toList());
	}
}