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

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO) {
		Optional<User> adminOpt = userRepository.findById(adminId);
		if (adminOpt.isEmpty() || !(adminOpt.get().getUserType() instanceof Admin)) {
			throw new RuntimeException("Admin not found with ID: " + adminId);
		}
		Admin admin = (Admin) adminOpt.get().getUserType();

		LoanScheme loanScheme = modelMapper.map(requestDTO, LoanScheme.class);
		loanScheme.setAdmin(admin);

		loanScheme = loanSchemeRepository.save(loanScheme);

		ModelMapper mapper = new ModelMapper();
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

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(LoanScheme.class, LoanSchemeResponseDTO.class).addMapping(src -> src.getAdmin().getId(),
				LoanSchemeResponseDTO::setAdminId);
		return mapper.map(loanScheme, LoanSchemeResponseDTO.class);
	}

	@Override
	public List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId) {
		List<LoanScheme> schemes = loanSchemeRepository.findByAdminId(adminId);
		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(LoanScheme.class, LoanSchemeResponseDTO.class).addMapping(src -> src.getAdmin().getId(),
				LoanSchemeResponseDTO::setAdminId);
		return schemes.stream().map(scheme -> mapper.map(scheme, LoanSchemeResponseDTO.class))
				.collect(Collectors.toList());
	}
}