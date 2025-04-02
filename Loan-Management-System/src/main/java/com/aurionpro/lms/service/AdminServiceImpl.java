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

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AdminResponseDTO getAdminById(int id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty() || !(userOpt.get().getUserType() instanceof Admin)) {
			throw new RuntimeException("Admin not found with ID: " + id);
		}
		User user = userOpt.get();
		Admin admin = (Admin) user.getUserType();

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(User.class, AdminResponseDTO.class)
				.addMapping(src -> ((Admin) src.getUserType()).getLoanOfficers().stream().map(LoanOfficer::getId)
						.collect(Collectors.toList()), AdminResponseDTO::setLoanOfficerIds)
				.addMapping(src -> ((Admin) src.getUserType()).getLoanSchemes().stream().map(LoanScheme::getId)
						.collect(Collectors.toList()), AdminResponseDTO::setLoanSchemeIds);
		return mapper.map(user, AdminResponseDTO.class);
	}

	@Override
	public List<AdminResponseDTO> getAllAdmins() {
		List<User> admins = userRepository.findAll().stream().filter(user -> user.getUserType() instanceof Admin)
				.collect(Collectors.toList());

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(User.class, AdminResponseDTO.class)
				.addMapping(src -> ((Admin) src.getUserType()).getLoanOfficers().stream().map(LoanOfficer::getId)
						.collect(Collectors.toList()), AdminResponseDTO::setLoanOfficerIds)
				.addMapping(src -> ((Admin) src.getUserType()).getLoanSchemes().stream().map(LoanScheme::getId)
						.collect(Collectors.toList()), AdminResponseDTO::setLoanSchemeIds);
		return admins.stream().map(user -> mapper.map(user, AdminResponseDTO.class)).collect(Collectors.toList());
	}
}