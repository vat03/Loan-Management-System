//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
//import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.RoleRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanOfficerServiceImpl implements LoanOfficerService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private RoleRepository roleRepository;
//
//	private ModelMapper mapper;
//
//	private LoanOfficerServiceImpl() {
//		this.mapper = new ModelMapper();
//	}
//
//	@Override
//	public LoanOfficerResponseDTO addLoanOfficer(int adminId, LoanOfficerRequestDTO requestDTO) {
//		Optional<User> userOpt = userRepository.findById(adminId);
//		if (userOpt.isEmpty() || !(userOpt.get().getUserType() instanceof Admin)) {
//			throw new RuntimeException("Admin not found with ID: " + adminId);
//		}
//		Admin admin = (Admin) userOpt.get().getUserType();
//
//		Optional<Role> roleOpt = roleRepository.findByRoleName("LOAN_OFFICER");
//		if (roleOpt.isEmpty()) {
//			throw new RuntimeException("Role not found: LOAN_OFFICER");
//		}
//		Role role = roleOpt.get();
//
//		LoanOfficer loanOfficer = new LoanOfficer();
//		loanOfficer.setAdmin(admin);
//
//		User user = mapper.map(requestDTO, User.class);
//		user.setRole(role);
//		user.setUserType(loanOfficer);
//
//		user = userRepository.save(user);
//
//		mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
//				.addMapping(src -> ((LoanOfficer) src.getUserType()).getAdmin().getId(),
//						LoanOfficerResponseDTO::setAdminId)
//				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
//						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
//		return mapper.map(user, LoanOfficerResponseDTO.class);
//	}
//
//	@Override
//	public LoanOfficerResponseDTO getLoanOfficerById(int id) {
//		Optional<User> userOpt = userRepository.findById(id);
//		if (userOpt.isEmpty() || !(userOpt.get().getUserType() instanceof LoanOfficer)) {
//			throw new RuntimeException("Loan Officer not found with ID: " + id);
//		}
//		User user = userOpt.get();
//
//		mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
//				.addMapping(src -> ((LoanOfficer) src.getUserType()).getAdmin().getId(),
//						LoanOfficerResponseDTO::setAdminId)
//				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
//						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
//		return mapper.map(user, LoanOfficerResponseDTO.class);
//	}
//
//	@Override
//	public List<LoanOfficerResponseDTO> getLoanOfficersByAdminId(int adminId) {
//		Optional<User> userOpt = userRepository.findById(adminId);
//		if (userOpt.isEmpty() || !(userOpt.get().getUserType() instanceof Admin)) {
//			throw new RuntimeException("Admin not found with ID: " + adminId);
//		}
//
//		List<User> loanOfficers = userRepository.findAll().stream()
//				.filter(user -> user.getUserType() instanceof LoanOfficer
//						&& ((LoanOfficer) user.getUserType()).getAdmin().getId() == adminId)
//				.collect(Collectors.toList());
//
//		mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
//				.addMapping(src -> ((LoanOfficer) src.getUserType()).getAdmin().getId(),
//						LoanOfficerResponseDTO::setAdminId)
//				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
//						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
//		return loanOfficers.stream().map(user -> mapper.map(user, LoanOfficerResponseDTO.class))
//				.collect(Collectors.toList());
//	}
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.entity.Role;
import com.aurionpro.lms.entity.User;
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

	private ModelMapper mapper;

	private LoanOfficerServiceImpl() {
		this.mapper = new ModelMapper();
	}

	@Override
	public LoanOfficerResponseDTO addLoanOfficer(int adminId, LoanOfficerRequestDTO requestDTO) {
		Optional<User> userOpt = userRepository.findById(adminId);
		if (userOpt.isEmpty() || !(userOpt.get().getUserType() instanceof Admin)) {
			throw new RuntimeException("Admin not found with ID: " + adminId);
		}
		User adminUser = userOpt.get(); // User with id=1
		Admin admin = (Admin) adminUser.getUserType(); // Admin with id=2

		Optional<Role> roleOpt = roleRepository.findByRoleName("LOAN_OFFICER");
		if (roleOpt.isEmpty()) {
			throw new RuntimeException("Role not found: LOAN_OFFICER");
		}
		Role role = roleOpt.get();

		LoanOfficer loanOfficer = new LoanOfficer();
		loanOfficer.setAdmin(admin); // Sets admin_id=2 in loan_officer table

		User user = mapper.map(requestDTO, User.class);
		user.setRole(role);
		user.setUserType(loanOfficer);

		user = userRepository.save(user);

		mapper.typeMap(User.class, LoanOfficerResponseDTO.class).addMapping(User::getId, LoanOfficerResponseDTO::setId) // Loan
																														// officer
																														// User
																														// ID
																														// (e.g.,
																														// 2)
				.addMapping(src -> adminUser.getId(), LoanOfficerResponseDTO::setAdminId) // User ID of admin (e.g., 1)
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

		// Since we can't modify Admin to add getUser(), we need to fetch the admin's
		// User ID separately
		int adminId = ((LoanOfficer) user.getUserType()).getAdmin().getId(); // Admin entity ID (e.g., 2)
		Optional<User> adminUserOpt = userRepository.findAll().stream()
				.filter(u -> u.getUserType() instanceof Admin && ((Admin) u.getUserType()).getId() == adminId)
				.findFirst();
		if (adminUserOpt.isEmpty()) {
			throw new RuntimeException("Admin user not found for LoanOfficer with ID: " + id);
		}
		int adminUserId = adminUserOpt.get().getId(); // User ID of admin (e.g., 1)

		mapper.typeMap(User.class, LoanOfficerResponseDTO.class).addMapping(User::getId, LoanOfficerResponseDTO::setId) // Loan
																														// officer
																														// User
																														// ID
				.addMapping(src -> adminUserId, LoanOfficerResponseDTO::setAdminId) // User ID of admin
				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
		return mapper.map(user, LoanOfficerResponseDTO.class);
	}

	@Override
	public List<LoanOfficerResponseDTO> getLoanOfficersByAdminId(int adminId) {
		Optional<User> userOpt = userRepository.findById(adminId);
		if (userOpt.isEmpty() || !(userOpt.get().getUserType() instanceof Admin)) {
			throw new RuntimeException("Admin not found with ID: " + adminId);
		}
		User adminUser = userOpt.get();
		Admin admin = (Admin) adminUser.getUserType();

		List<User> loanOfficers = userRepository.findAll().stream()
				.filter(user -> user.getUserType() instanceof LoanOfficer
						&& ((LoanOfficer) user.getUserType()).getAdmin().getId() == admin.getId())
				.collect(Collectors.toList());

		mapper.typeMap(User.class, LoanOfficerResponseDTO.class).addMapping(User::getId, LoanOfficerResponseDTO::setId)
				.addMapping(src -> adminUser.getId(), LoanOfficerResponseDTO::setAdminId)
				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
		return loanOfficers.stream().map(user -> mapper.map(user, LoanOfficerResponseDTO.class))
				.collect(Collectors.toList());
	}
}