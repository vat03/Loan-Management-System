package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.LoanScheme;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.repository.AdminRepository;
import com.aurionpro.lms.repository.LoanSchemeRepository;
import com.aurionpro.lms.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanSchemeServiceImpl implements LoanSchemeService {

	@Autowired
	private LoanSchemeRepository loanSchemeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdminRepository adminRepository;

	private ModelMapper mapper;

	private LoanSchemeServiceImpl() {
		this.mapper = new ModelMapper();
	}

	@Override
	public LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO) {
		Optional<User> adminOpt = userRepository.findById(adminId);
		if (adminOpt.isEmpty()) {
			throw new RuntimeException("User not found with ID: " + adminId);
		}
		Optional<Admin> adminEntityOpt = adminRepository.findByUserId(adminId);
		if (adminEntityOpt.isEmpty()) {
			throw new RuntimeException("Admin not found with ID: " + adminId);
		}
		Admin admin = adminEntityOpt.get();

		LoanScheme loanScheme = mapper.map(requestDTO, LoanScheme.class);
		loanScheme.setAdmin(admin);

		loanScheme = loanSchemeRepository.save(loanScheme);

		mapper.typeMap(LoanScheme.class, LoanSchemeResponseDTO.class).addMapping(src -> src.getAdmin().getId(),
				LoanSchemeResponseDTO::setAdminId);
		return mapper.map(loanScheme, LoanSchemeResponseDTO.class);
	}

	@Override
	public LoanSchemeResponseDTO getLoanSchemeById(int id) {
		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(id);
		if (schemeOpt.isEmpty()) {
			throw new RuntimeException("Loan scheme not found with ID: " + id);
		}
		LoanScheme loanScheme = schemeOpt.get();

		mapper.typeMap(LoanScheme.class, LoanSchemeResponseDTO.class).addMapping(src -> src.getAdmin().getId(),
				LoanSchemeResponseDTO::setAdminId);
		return mapper.map(loanScheme, LoanSchemeResponseDTO.class);
	}

	@Override
	public List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId) {
		List<LoanScheme> schemes = loanSchemeRepository.findByAdminId(adminId);
		mapper.typeMap(LoanScheme.class, LoanSchemeResponseDTO.class).addMapping(src -> src.getAdmin().getId(),
				LoanSchemeResponseDTO::setAdminId);
		return schemes.stream().map(scheme -> mapper.map(scheme, LoanSchemeResponseDTO.class))
				.collect(Collectors.toList());
	}
}