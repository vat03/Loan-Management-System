//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.entity.UserType;
//import com.aurionpro.lms.repository.RoleRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private RoleRepository roleRepository;
//
//	private ModelMapper mapper;
//
//	private UserServiceImpl() {
//		this.mapper = new ModelMapper();
//	}
//
//	@Override
//	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO, String roleName) {
//		Optional<Role> roleOpt = roleRepository.findByRoleName(roleName);
//		if (roleOpt.isEmpty()) {
//			throw new RuntimeException("Role not found: " + roleName);
//		}
//		Role role = roleOpt.get();
//
//		User user = mapper.map(userRequestDTO, User.class);
//		user.setRole(role);
//
//		UserType userType;
//		switch (roleName) {
//		case "ADMIN":
//			userType = new com.aurionpro.lms.entity.Admin();
//			break;
//		case "LOAN_OFFICER":
//			userType = new com.aurionpro.lms.entity.LoanOfficer();
//			break;
//		case "CUSTOMER":
//			userType = new com.aurionpro.lms.entity.Customer();
//			break;
//		default:
//			throw new RuntimeException("Unsupported role: " + roleName);
//		}
//		user.setUserType(userType);
//
//		user = userRepository.save(user);
//
//		//ModelMapper mapper = new ModelMapper();
//		mapper.typeMap(User.class, UserResponseDTO.class).addMapping(src -> src.getRole().getRoleName(),
//				UserResponseDTO::setRoleName);
//		return mapper.map(user, UserResponseDTO.class);
//	}
//
//	@Override
//	public UserResponseDTO getUserById(int id) {
//		Optional<User> userOpt = userRepository.findById(id);
//		if (userOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + id);
//		}
//		User user = userOpt.get();
//
//		//ModelMapper mapper = new ModelMapper();
//		mapper.typeMap(User.class, UserResponseDTO.class).addMapping(src -> src.getRole().getRoleName(),
//				UserResponseDTO::setRoleName);
//		return mapper.map(user, UserResponseDTO.class);
//	}
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.UserRequestDTO;
import com.aurionpro.lms.dto.UserResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Role;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.repository.AdminRepository;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import com.aurionpro.lms.repository.RoleRepository;
import com.aurionpro.lms.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private LoanOfficerRepository loanOfficerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	private ModelMapper mapper;

	private UserServiceImpl() {
		this.mapper = new ModelMapper();
	}

	@Override
	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO, String roleName) {
		Optional<Role> roleOpt = roleRepository.findByRoleName(roleName);
		if (roleOpt.isEmpty()) {
			throw new RuntimeException("Role not found: " + roleName);
		}
		Role role = roleOpt.get();

		User user = mapper.map(userRequestDTO, User.class);
		user.setRole(role);

		user = userRepository.save(user); // Save User first to get its ID

		// Create and save the specific type entity
		switch (roleName) {
		case "ADMIN":
			Admin admin = new Admin();
			admin.setUser(user);
			adminRepository.save(admin);
			break;
		case "LOAN_OFFICER":
			throw new RuntimeException("Use LoanOfficerService to register a Loan Officer");
		case "CUSTOMER":
			Customer customer = new Customer();
			customer.setUser(user);
			customerRepository.save(customer);
			break;
		default:
			throw new RuntimeException("Unsupported role: " + roleName);
		}

		mapper.typeMap(User.class, UserResponseDTO.class).addMapping(src -> src.getRole().getRoleName(),
				UserResponseDTO::setRoleName);
		return mapper.map(user, UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO getUserById(int id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new RuntimeException("User not found with ID: " + id);
		}
		User user = userOpt.get();

		mapper.typeMap(User.class, UserResponseDTO.class).addMapping(src -> src.getRole().getRoleName(),
				UserResponseDTO::setRoleName);
		return mapper.map(user, UserResponseDTO.class);
	}
}