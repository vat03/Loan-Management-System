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

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
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
//	@Autowired
//	private AdminRepository adminRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
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
//		user = userRepository.save(user); // Save User first to get its ID
//
//		// Create and save the specific type entity
//		switch (roleName) {
//		case "ADMIN":
//			Admin admin = new Admin();
//			admin.setUser(user);
//			adminRepository.save(admin);
//			break;
//		case "LOAN_OFFICER":
//			throw new RuntimeException("Use LoanOfficerService to register a Loan Officer");
//		case "CUSTOMER":
//			Customer customer = new Customer();
//			customer.setUser(user);
//			customerRepository.save(customer);
//			break;
//		default:
//			throw new RuntimeException("Unsupported role: " + roleName);
//		}
//
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
//		mapper.typeMap(User.class, UserResponseDTO.class).addMapping(src -> src.getRole().getRoleName(),
//				UserResponseDTO::setRoleName);
//		return mapper.map(user, UserResponseDTO.class);
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.RoleRepository;
//import com.aurionpro.lms.repository.UserRepository;
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
//	@Autowired
//	private AdminRepository adminRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Override
//	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO, String roleName) {
//		Optional<Role> roleOpt = roleRepository.findByRoleName(roleName);
//		if (roleOpt.isEmpty()) {
//			throw new RuntimeException("Role not found: " + roleName);
//		}
//		Role role = roleOpt.get();
//
//		User user = new User();
//		user.setUsername(userRequestDTO.getUsername());
//		user.setEmail(userRequestDTO.getEmail());
//		user.setPassword(userRequestDTO.getPassword());
//		user.setRole(role);
//
//		user = userRepository.save(user);
//
//		switch (roleName) {
//		case "ADMIN":
//			Admin admin = new Admin();
//			admin.setUser(user);
//			adminRepository.save(admin);
//			break;
//		case "LOAN_OFFICER":
//			throw new RuntimeException("Use LoanOfficerService to register a Loan Officer");
//		case "CUSTOMER":
//			Customer customer = new Customer();
//			customer.setUser(user);
//			customerRepository.save(customer);
//			break;
//		default:
//			throw new RuntimeException("Unsupported role: " + roleName);
//		}
//
//		UserResponseDTO dto = new UserResponseDTO();
//		dto.setId(user.getId());
//		dto.setUsername(user.getUsername());
//		dto.setEmail(user.getEmail());
//		dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
//		return dto;
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
//		UserResponseDTO dto = new UserResponseDTO();
//		dto.setId(user.getId());
//		dto.setUsername(user.getUsername());
//		dto.setEmail(user.getEmail());
//		dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
//		return dto;
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.RoleRepository;
//import com.aurionpro.lms.repository.UserRepository;
//
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
//	@Autowired
//	private AdminRepository adminRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Override
//	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO, String roleName) {
//		Optional<Role> roleOpt = roleRepository.findByRoleName(roleName);
//		if (roleOpt.isEmpty()) {
//			throw new RuntimeException("Role not found: " + roleName);
//		}
//		Role role = roleOpt.get();
//
//		User user = new User();
//		user.setUsername(userRequestDTO.getUsername());
//		user.setEmail(userRequestDTO.getEmail());
//		user.setPassword(userRequestDTO.getPassword());
//		user.setRole(role);
//
//		user = userRepository.save(user);
//
//		switch (roleName) {
//		case "ADMIN":
//			Admin admin = new Admin();
//			admin.setUser(user);
//			adminRepository.save(admin);
//			break;
//		case "LOAN_OFFICER":
//			throw new RuntimeException("Use LoanOfficerService to register a Loan Officer");
//		case "CUSTOMER":
//			Customer customer = new Customer();
//			customer.setUser(user);
//			customerRepository.save(customer);
//			break;
//		default:
//			throw new RuntimeException("Unsupported role: " + roleName);
//		}
//
//		UserResponseDTO dto = new UserResponseDTO();
//		dto.setId(user.getId());
//		dto.setUsername(user.getUsername());
//		dto.setEmail(user.getEmail());
//		dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
//		return dto;
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
//		UserResponseDTO dto = new UserResponseDTO();
//		dto.setId(user.getId());
//		dto.setUsername(user.getUsername());
//		dto.setEmail(user.getEmail());
//		dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
//		return dto;
//	}
//}

package com.aurionpro.lms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.dto.LoginRequestDTO;
import com.aurionpro.lms.dto.UserRequestDTO;
import com.aurionpro.lms.dto.UserResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Role;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.exception.BusinessRuleViolationException;
import com.aurionpro.lms.exception.InvalidInputException;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.exception.UserNotRegisteredException;
import com.aurionpro.lms.repository.AdminRepository;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.RoleRepository;
import com.aurionpro.lms.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO, String roleName) {
		Optional<Role> roleOpt = roleRepository.findByRoleName(roleName);
		if (roleOpt.isEmpty()) {
			throw new ResourceNotFoundException("Role not found: " + roleName);
		}
		Role role = roleOpt.get();

		// Check if email or username already exists
		if (userRepository.findByEmailAndIsDeletedFalse(userRequestDTO.getEmail()).isPresent()) {
			throw new BusinessRuleViolationException("Email already registered: " + userRequestDTO.getEmail());
		}
		if (userRepository.findByUsernameAndIsDeletedFalse(userRequestDTO.getUsername()).isPresent()) {
			throw new BusinessRuleViolationException("Username already taken: " + userRequestDTO.getUsername());
		}

		User user = new User();
		user.setUsername(userRequestDTO.getUsername());
		user.setEmail(userRequestDTO.getEmail());
		user.setPassword(userRequestDTO.getPassword());
		user.setRole(role);

		user = userRepository.save(user);

		switch (roleName) {
		case "ADMIN":
			Admin admin = new Admin();
			admin.setUser(user);
			adminRepository.save(admin);
			break;
		case "LOAN_OFFICER":
			throw new BusinessRuleViolationException("Use LoanOfficerService to register a Loan Officer");
		case "CUSTOMER":
			Customer customer = new Customer();
			customer.setUser(user);
			customerRepository.save(customer);
			break;
		default:
			throw new BusinessRuleViolationException("Unsupported role: " + roleName);
		}

		return toResponseDTO(user);
	}

	@Override
	public UserResponseDTO getUserById(int id) {
		User user = userRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
		return toResponseDTO(user);
	}

	@Override
	public UserResponseDTO login(LoginRequestDTO loginRequestDTO) {
		User user = userRepository.findByUsernameAndIsDeletedFalse(loginRequestDTO.getUsername())
				.orElseThrow(() -> new UserNotRegisteredException("User not registered with username: "
						+ loginRequestDTO.getUsername() + ". Please register first."));

		if (user.isDeleted()) {
			throw new IllegalStateException("Cannot login: User account is deactivated");
		}

		if (!user.getPassword().equals(loginRequestDTO.getPassword())) {
			throw new InvalidInputException("Invalid password for username: " + loginRequestDTO.getUsername());
		}

		return toResponseDTO(user);
	}

	@Override
	public List<UserResponseDTO> getAllUsers(boolean includeDeleted) {
		List<User> users = userRepository.findAllUsers(includeDeleted);
		return users.stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	private UserResponseDTO toResponseDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
		dto.setDeleted(user.isDeleted());
		return dto;
	}
}