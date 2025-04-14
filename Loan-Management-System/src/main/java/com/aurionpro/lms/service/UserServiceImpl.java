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

//package com.aurionpro.lms.service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.lms.dto.LoginRequestDTO;
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.exception.BusinessRuleViolationException;
//import com.aurionpro.lms.exception.InvalidInputException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.exception.UserNotRegisteredException;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.RoleRepository;
//import com.aurionpro.lms.repository.UserRepository;
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
//			throw new ResourceNotFoundException("Role not found: " + roleName);
//		}
//		Role role = roleOpt.get();
//
//		// Check if email or username already exists
//		if (userRepository.findByEmailAndIsDeletedFalse(userRequestDTO.getEmail()).isPresent()) {
//			throw new BusinessRuleViolationException("Email already registered: " + userRequestDTO.getEmail());
//		}
//		if (userRepository.findByUsernameAndIsDeletedFalse(userRequestDTO.getUsername()).isPresent()) {
//			throw new BusinessRuleViolationException("Username already taken: " + userRequestDTO.getUsername());
//		}
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
//			throw new BusinessRuleViolationException("Use LoanOfficerService to register a Loan Officer");
//		case "CUSTOMER":
//			Customer customer = new Customer();
//			customer.setUser(user);
//			customerRepository.save(customer);
//			break;
//		default:
//			throw new BusinessRuleViolationException("Unsupported role: " + roleName);
//		}
//
//		return toResponseDTO(user);
//	}
//
//	@Override
//	public UserResponseDTO getUserById(int id) {
//		User user = userRepository.findByIdAndIsDeletedFalse(id)
//				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
//		return toResponseDTO(user);
//	}
//
//	@Override
//	public UserResponseDTO login(LoginRequestDTO loginRequestDTO) {
//		User user = userRepository.findByUsernameAndIsDeletedFalse(loginRequestDTO.getUsername())
//				.orElseThrow(() -> new UserNotRegisteredException("User not registered with username: "
//						+ loginRequestDTO.getUsername() + ". Please register first."));
//
//		if (user.isDeleted()) {
//			throw new IllegalStateException("Cannot login: User account is deactivated");
//		}
//
//		if (!user.getPassword().equals(loginRequestDTO.getPassword())) {
//			throw new InvalidInputException("Invalid password for username: " + loginRequestDTO.getUsername());
//		}
//
//		return toResponseDTO(user);
//	}
//
//	@Override
//	public List<UserResponseDTO> getAllUsers(boolean includeDeleted) {
//		List<User> users = userRepository.findAllUsers(includeDeleted);
//		return users.stream().map(this::toResponseDTO).collect(Collectors.toList());
//	}
//
//	private UserResponseDTO toResponseDTO(User user) {
//		UserResponseDTO dto = new UserResponseDTO();
//		dto.setId(user.getId());
//		dto.setUsername(user.getUsername());
//		dto.setEmail(user.getEmail());
//		dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
//		dto.setDeleted(user.isDeleted());
//		return dto;
//	}
//}

package com.aurionpro.lms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.dto.JwtResponseDTO;
import com.aurionpro.lms.dto.LoginRequestDTO;
import com.aurionpro.lms.dto.UserRequestDTO;
import com.aurionpro.lms.dto.UserResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Role;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.exception.BusinessRuleViolationException;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.AdminRepository;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import com.aurionpro.lms.repository.RoleRepository;
import com.aurionpro.lms.repository.UserRepository;
import com.aurionpro.lms.security.JwtUtil;

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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomerService customerService;

//	@Override
//	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO, String roleName) {
//		String prefixedRoleName = "ROLE_" + roleName.toUpperCase();
//		Role role = roleRepository.findByRoleName(prefixedRoleName)
//				.orElseThrow(() -> new ResourceNotFoundException("Role not found: " + prefixedRoleName));
//
//		if (userRepository.findByEmailAndIsDeletedFalse(userRequestDTO.getEmail()).isPresent()) {
//			throw new BusinessRuleViolationException("Email already registered: " + userRequestDTO.getEmail());
//		}
//		if (userRepository.findByUsernameAndIsDeletedFalse(userRequestDTO.getUsername()).isPresent()) {
//			throw new BusinessRuleViolationException("Username already taken: " + userRequestDTO.getUsername());
//		}
//
//		User user = new User();
//		user.setUsername(userRequestDTO.getUsername());
//		user.setEmail(userRequestDTO.getEmail());
//		user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
//		user.setRole(role);
//
//		user = userRepository.save(user);
//
//		switch (roleName.toUpperCase()) {
//		case "ADMIN":
//			Admin admin = new Admin();
//			admin.setUser(user);
//			adminRepository.save(admin);
//			break;
//		case "LOAN_OFFICER":
//			throw new BusinessRuleViolationException("Use LoanOfficerService to register a Loan Officer");
//		case "CUSTOMER":
//			Customer customer = new Customer();
//			customer.setUser(user);
//			customerRepository.save(customer);
//			break;
//		default:
//			throw new BusinessRuleViolationException("Unsupported role: " + roleName);
//		}
//
//		return toResponseDTO(user);
//	}
	
	
//----------------------------------with admin- -----------------------------------------
//	@Override
//	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO, String roleName) {
//		// Validate roleName doesn't start with ROLE_
//		if (roleName != null && roleName.toUpperCase().startsWith("ROLE_")) {
//			throw new BusinessRuleViolationException(
//					"Role name should be 'ADMIN' or 'CUSTOMER', not prefixed with 'ROLE_'");
//		}
//
//		// Map roleName to database role
//		String prefixedRoleName = "ROLE_" + roleName.toUpperCase();
//		Role role = roleRepository.findByRoleName(prefixedRoleName)
//				.orElseThrow(() -> new ResourceNotFoundException("Role not found: " + prefixedRoleName));
//
//		if (userRepository.findByEmailAndIsDeletedFalse(userRequestDTO.getEmail()).isPresent()) {
//			throw new BusinessRuleViolationException("Email already registered: " + userRequestDTO.getEmail());
//		}
//		if (userRepository.findByUsernameAndIsDeletedFalse(userRequestDTO.getUsername()).isPresent()) {
//			throw new BusinessRuleViolationException("Username already taken: " + userRequestDTO.getUsername());
//		}
//
//		User user = new User();
//		user.setUsername(userRequestDTO.getUsername());
//		user.setEmail(userRequestDTO.getEmail());
//		user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
//		user.setRole(role);
//
//		user = userRepository.save(user);
//
//		switch (roleName.toUpperCase()) {
//		case "ADMIN":
//			Admin admin = new Admin();
//			admin.setUser(user);
//			adminRepository.save(admin);
//			break;
//		case "LOAN_OFFICER":
//			throw new BusinessRuleViolationException("Use LoanOfficerService to register a Loan Officer");
//		case "CUSTOMER":
//			Customer customer = new Customer();
//			customer.setUser(user);
//			customerRepository.save(customer);
//			break;
//		default:
//			throw new BusinessRuleViolationException("Unsupported role: " + roleName);
//		}
//
//		return toResponseDTO(user);
//	}

	@Override
	public UserResponseDTO registerUser(UserRequestDTO userRequestDTO, String roleName) {
	    // Validate role name is only CUSTOMER
	    if (!"CUSTOMER".equalsIgnoreCase(roleName)) {
	        throw new BusinessRuleViolationException("Only customers can register using this method.");
	    }

	    String prefixedRoleName = "ROLE_CUSTOMER";
	    Role role = roleRepository.findByRoleName(prefixedRoleName)
	            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + prefixedRoleName));

	    if (userRepository.findByEmailAndIsDeletedFalse(userRequestDTO.getEmail()).isPresent()) {
	        throw new BusinessRuleViolationException("Email already registered: " + userRequestDTO.getEmail());
	    }

	    if (userRepository.findByUsernameAndIsDeletedFalse(userRequestDTO.getUsername()).isPresent()) {
	        throw new BusinessRuleViolationException("Username already taken: " + userRequestDTO.getUsername());
	    }

	    User user = new User();
	    user.setUsername(userRequestDTO.getUsername());
	    user.setEmail(userRequestDTO.getEmail());
	    user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
	    user.setRole(role);

	    user = userRepository.save(user);

	    Customer customer = new Customer();
	    customer.setUser(user);
	    customer = customerRepository.save(customer);

	    // Auto assign loan officer
	    customerService.assignLoanOfficer(customer.getId());

	    return toResponseDTO(user);
	}

	
	@Override
	public UserResponseDTO getUserById(int id) {
		User user = userRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
		return toResponseDTO(user);
	}

//	@Override
//	public JwtResponseDTO login(LoginRequestDTO loginRequestDTO) {
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		User user = userRepository.findByUsernameAndIsDeletedFalse(loginRequestDTO.getUsername())
//				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//		String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().getRoleName());
//
//		JwtResponseDTO response = new JwtResponseDTO();
//		response.setToken(token);
//		response.setUserId(user.getId());
//		response.setUsername(user.getUsername());
//		response.setRole(user.getRole().getRoleName());
//		return response;
//	}
	
	@Override
    public JwtResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsernameAndIsDeletedFalse(loginRequestDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().getRoleName());

        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().getRoleName());

        // Set adminId or customerId based on role
//        String roleName = user.getRole().getRoleName();
//        if ("ROLE_ADMIN".equals(roleName)) {
//            adminRepository.findByUserId(user.getId())
//                    .ifPresent(admin -> response.setAdminId(admin.getId()));
//        } else if ("ROLE_CUSTOMER".equals(roleName)) {
//            customerRepository.findByUserId(user.getId())
//                    .ifPresent(customer -> response.setCustomerId(customer.getId()));
//        }
        
        String roleName = user.getRole().getRoleName();
        if ("ROLE_ADMIN".equals(roleName)) {
            adminRepository.findByUserId(user.getId())
                    .ifPresent(admin -> response.setAdminId(admin.getId()));
        } else if ("ROLE_CUSTOMER".equals(roleName)) {
            customerRepository.findByUserId(user.getId())
                    .ifPresent(customer -> response.setCustomerId(customer.getId()));
        } else if ("ROLE_LOAN_OFFICER".equals(roleName)) {
            loanOfficerRepository.findByUserId(user.getId())
                    .ifPresent(officer -> response.setLoanOfficerId(officer.getId()));
        }

        return response;
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

//	@Override
//	public JwtResponseDTO generateTestToken(int userId, String username, String roleName) {
//	    String token = jwtUtil.generateToken(userId, username, roleName);
//	    return new JwtResponseDTO(token, userId, username, roleName);
//	}
}