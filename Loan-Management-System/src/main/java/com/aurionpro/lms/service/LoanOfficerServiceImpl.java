package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.entity.Role;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.repository.AdminRepository;
import com.aurionpro.lms.repository.RoleRepository;
import com.aurionpro.lms.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanOfficerServiceImpl implements LoanOfficerService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public LoanOfficerResponseDTO addLoanOfficer(int adminId, LoanOfficerRequestDTO requestDTO) {
		Optional<Admin> adminOpt = adminRepository.findById(adminId);
		if (adminOpt.isEmpty()) {
			throw new RuntimeException("Admin not found with ID: " + adminId);
		}
		Admin admin = adminOpt.get();

		Optional<Role> roleOpt = roleRepository.findByRoleName("LOAN_OFFICER");
		if (roleOpt.isEmpty()) {
			throw new RuntimeException("Role not found: LOAN_OFFICER");
		}
		Role role = roleOpt.get();

		LoanOfficer loanOfficer = new LoanOfficer();
		loanOfficer.setAdmin(admin);

		User user = modelMapper.map(requestDTO, User.class);
		user.setRole(role);
		user.setUserType(loanOfficer);

		user = userRepository.save(user);

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
				.addMapping(src -> ((LoanOfficer) src.getUserType()).getAdmin().getId(),
						LoanOfficerResponseDTO::setAdminId)
				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
		return mapper.map(user, LoanOfficerResponseDTO.class);
	}

	@Override
	public LoanOfficerResponseDTO getLoanOfficerById(int id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty() || !(userOpt.get().getUserType() instanceof LoanOfficer)) {
			throw new RuntimeException("Loan Officer not found with ID: " + id);
		}
		User user = userOpt.get();

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
				.addMapping(src -> ((LoanOfficer) src.getUserType()).getAdmin().getId(),
						LoanOfficerResponseDTO::setAdminId)
				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
		return mapper.map(user, LoanOfficerResponseDTO.class);
	}

	@Override
	public List<LoanOfficerResponseDTO> getLoanOfficersByAdminId(int adminId) {
		Optional<Admin> adminOpt = adminRepository.findById(adminId);
		if (adminOpt.isEmpty()) {
			throw new RuntimeException("Admin not found with ID: " + adminId);
		}

		List<User> loanOfficers = userRepository.findAll().stream()
				.filter(user -> user.getUserType() instanceof LoanOfficer
						&& ((LoanOfficer) user.getUserType()).getAdmin().getId() == adminId) // Fixed: use == instead of
																								// equals()
				.collect(Collectors.toList());

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
				.addMapping(src -> ((LoanOfficer) src.getUserType()).getAdmin().getId(),
						LoanOfficerResponseDTO::setAdminId)
				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
		return loanOfficers.stream().map(user -> mapper.map(user, LoanOfficerResponseDTO.class))
				.collect(Collectors.toList());
	}
}