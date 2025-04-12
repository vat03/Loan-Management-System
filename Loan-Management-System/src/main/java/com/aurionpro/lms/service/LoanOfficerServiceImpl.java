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
//		User adminUser = userOpt.get(); // User with id=1
//		Admin admin = (Admin) adminUser.getUserType(); // Admin with id=2
//
//		Optional<Role> roleOpt = roleRepository.findByRoleName("LOAN_OFFICER");
//		if (roleOpt.isEmpty()) {
//			throw new RuntimeException("Role not found: LOAN_OFFICER");
//		}
//		Role role = roleOpt.get();
//
//		LoanOfficer loanOfficer = new LoanOfficer();
//		loanOfficer.setAdmin(admin); // Sets admin_id=2 in loan_officer table
//
//		User user = mapper.map(requestDTO, User.class);
//		user.setRole(role);
//		user.setUserType(loanOfficer);
//
//		user = userRepository.save(user);
//
//		mapper.typeMap(User.class, LoanOfficerResponseDTO.class).addMapping(User::getId, LoanOfficerResponseDTO::setId) // Loan
//																														// officer
//																														// User
//																														// ID
//																														// (e.g.,
//																														// 2)
//				.addMapping(src -> adminUser.getId(), LoanOfficerResponseDTO::setAdminId) // User ID of admin (e.g., 1)
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
//		// Since we can't modify Admin to add getUser(), we need to fetch the admin's
//		// User ID separately
//		int adminId = ((LoanOfficer) user.getUserType()).getAdmin().getId(); // Admin entity ID (e.g., 2)
//		Optional<User> adminUserOpt = userRepository.findAll().stream()
//				.filter(u -> u.getUserType() instanceof Admin && ((Admin) u.getUserType()).getId() == adminId)
//				.findFirst();
//		if (adminUserOpt.isEmpty()) {
//			throw new RuntimeException("Admin user not found for LoanOfficer with ID: " + id);
//		}
//		int adminUserId = adminUserOpt.get().getId(); // User ID of admin (e.g., 1)
//
//		mapper.typeMap(User.class, LoanOfficerResponseDTO.class).addMapping(User::getId, LoanOfficerResponseDTO::setId) // Loan
//																														// officer
//																														// User
//																														// ID
//				.addMapping(src -> adminUserId, LoanOfficerResponseDTO::setAdminId) // User ID of admin
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
//		User adminUser = userOpt.get();
//		Admin admin = (Admin) adminUser.getUserType();
//
//		List<User> loanOfficers = userRepository.findAll().stream()
//				.filter(user -> user.getUserType() instanceof LoanOfficer
//						&& ((LoanOfficer) user.getUserType()).getAdmin().getId() == admin.getId())
//				.collect(Collectors.toList());
//
//		mapper.typeMap(User.class, LoanOfficerResponseDTO.class).addMapping(User::getId, LoanOfficerResponseDTO::setId)
//				.addMapping(src -> adminUser.getId(), LoanOfficerResponseDTO::setAdminId)
//				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
//						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
//		return loanOfficers.stream().map(user -> mapper.map(user, LoanOfficerResponseDTO.class))
//				.collect(Collectors.toList());
//	}
//}

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
//import java.util.Collections;
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
//		User adminUser = userOpt.get(); // User with id=1
//		Admin admin = (Admin) adminUser.getUserType(); // Admin with id=2
//
//		Optional<Role> roleOpt = roleRepository.findByRoleName("LOAN_OFFICER");
//		if (roleOpt.isEmpty()) {
//			throw new RuntimeException("Role not found: LOAN_OFFICER");
//		}
//		Role role = roleOpt.get();
//
//		LoanOfficer loanOfficer = new LoanOfficer();
//		loanOfficer.setAdmin(admin); // Sets admin_id=2 in loan_officer table
//
//		User user = mapper.map(requestDTO, User.class);
//		user.setRole(role);
//		user.setUserType(loanOfficer);
//
//		user = userRepository.save(user);
//
//		mapper.typeMap(User.class, LoanOfficerResponseDTO.class).addMapping(User::getId, LoanOfficerResponseDTO::setId) // Loan
//																														// officer
//																														// User
//																														// ID
//																														// (e.g.,
//																														// 2)
//				.addMapping(src -> adminUser.getId(), LoanOfficerResponseDTO::setAdminId) // User ID of admin (e.g., 1)
//				.addMapping(src -> Collections.emptyList(), LoanOfficerResponseDTO::setCustomerIds); // New loan officer
//																										// has no
//																										// customers yet
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
//		// Fetch admin's User ID
//		int adminId = ((LoanOfficer) user.getUserType()).getAdmin().getId(); // Admin entity ID (e.g., 2)
//		Optional<User> adminUserOpt = userRepository.findAll().stream()
//				.filter(u -> u.getUserType() instanceof Admin && ((Admin) u.getUserType()).getId() == adminId)
//				.findFirst();
//		if (adminUserOpt.isEmpty()) {
//			throw new RuntimeException("Admin user not found for LoanOfficer with ID: " + id);
//		}
//		int adminUserId = adminUserOpt.get().getId(); // User ID of admin (e.g., 1)
//
//		mapper.typeMap(User.class, LoanOfficerResponseDTO.class).addMapping(User::getId, LoanOfficerResponseDTO::setId)
//				.addMapping(src -> adminUserId, LoanOfficerResponseDTO::setAdminId)
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
//		User adminUser = userOpt.get();
//		Admin admin = (Admin) adminUser.getUserType();
//
//		List<User> loanOfficers = userRepository.findAll().stream()
//				.filter(user -> user.getUserType() instanceof LoanOfficer
//						&& ((LoanOfficer) user.getUserType()).getAdmin().getId() == admin.getId())
//				.collect(Collectors.toList());
//
//		mapper.typeMap(User.class, LoanOfficerResponseDTO.class).addMapping(User::getId, LoanOfficerResponseDTO::setId)
//				.addMapping(src -> adminUser.getId(), LoanOfficerResponseDTO::setAdminId)
//				.addMapping(src -> ((LoanOfficer) src.getUserType()).getCustomers().stream().map(Customer::getId)
//						.collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
//
//		return loanOfficers.stream().map(user -> mapper.map(user, LoanOfficerResponseDTO.class))
//				.collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
//import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import com.aurionpro.lms.repository.RoleRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanOfficerServiceImpl implements LoanOfficerService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private LoanOfficerRepository loanOfficerRepository;
//
//    private ModelMapper mapper;
//
//    private LoanOfficerServiceImpl() {
//        this.mapper = new ModelMapper();
//    }
//
//    @Override
//    public LoanOfficerResponseDTO addLoanOfficer(int adminId, LoanOfficerRequestDTO requestDTO) {
//        Optional<Admin> adminOpt = adminRepository.findById(adminId);
//        if (adminOpt.isEmpty()) {
//            throw new RuntimeException("Admin not found with ID: " + adminId);
//        }
//        Admin admin = adminOpt.get();
//
//        Optional<Role> roleOpt = roleRepository.findByRoleName("LOAN_OFFICER");
//        if (roleOpt.isEmpty()) {
//            throw new RuntimeException("Role not found: LOAN_OFFICER");
//        }
//        Role role = roleOpt.get();
//
//        User user = mapper.map(requestDTO, User.class);
//        user.setRole(role);
//        user = userRepository.save(user);
//
//        LoanOfficer loanOfficer = new LoanOfficer();
//        loanOfficer.setUser(user);
//        loanOfficer.setAdmin(admin);
//        loanOfficer = loanOfficerRepository.save(loanOfficer);
//
//        mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
//              .addMapping(User::getId, LoanOfficerResponseDTO::setId)
//              .addMapping(src -> admin.getId(), LoanOfficerResponseDTO::setAdminId)
//              .addMapping(src -> Collections.emptyList(), LoanOfficerResponseDTO::setCustomerIds);
//        return mapper.map(user, LoanOfficerResponseDTO.class);
//    }
//
//    @Override
//    public LoanOfficerResponseDTO getLoanOfficerById(int id) {
//        Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(id);
//        if (loanOfficerOpt.isEmpty()) {
//            throw new RuntimeException("Loan Officer not found with ID: " + id);
//        }
//        LoanOfficer loanOfficer = loanOfficerOpt.get();
//        User user = loanOfficer.getUser();
//
//        mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
//              .addMapping(User::getId, LoanOfficerResponseDTO::setId)
//              .addMapping(src -> loanOfficer.getAdmin().getId(), LoanOfficerResponseDTO::setAdminId)
//              .addMapping(src -> loanOfficer.getCustomers().stream().map(Customer::getId)
//                      .collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
//        return mapper.map(user, LoanOfficerResponseDTO.class);
//    }
//
//    @Override
//    public List<LoanOfficerResponseDTO> getLoanOfficersByAdminId(int adminId) {
//        Optional<Admin> adminOpt = adminRepository.findById(adminId);
//        if (adminOpt.isEmpty()) {
//            throw new RuntimeException("Admin not found with ID: " + adminId);
//        }
//        Admin admin = adminOpt.get();
//
//        List<LoanOfficer> loanOfficers = loanOfficerRepository.findByAdminId(adminId);
//
//        return loanOfficers.stream().map(loanOfficer -> {
//            User user = loanOfficer.getUser();
//            mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
//                  .addMapping(User::getId, LoanOfficerResponseDTO::setId)
//                  .addMapping(src -> loanOfficer.getAdmin().getId(), LoanOfficerResponseDTO::setAdminId)
//                  .addMapping(src -> loanOfficer.getCustomers().stream().map(Customer::getId)
//                          .collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
//            return mapper.map(user, LoanOfficerResponseDTO.class);
//        }).collect(Collectors.toList());
//    }
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
//import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import com.aurionpro.lms.repository.RoleRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanOfficerServiceImpl implements LoanOfficerService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private LoanOfficerRepository loanOfficerRepository;
//
//    private ModelMapper mapper;
//
//    private LoanOfficerServiceImpl() {
//        this.mapper = new ModelMapper();
//    }
//
//    @Override
//    public LoanOfficerResponseDTO addLoanOfficer(int adminId, LoanOfficerRequestDTO requestDTO) {
//        Optional<Admin> adminOpt = adminRepository.findById(adminId);
//        if (adminOpt.isEmpty()) {
//            throw new RuntimeException("Admin not found with ID: " + adminId);
//        }
//        Admin admin = adminOpt.get();
//
//        Optional<Role> roleOpt = roleRepository.findByRoleName("LOAN_OFFICER");
//        if (roleOpt.isEmpty()) {
//            throw new RuntimeException("Role not found: LOAN_OFFICER");
//        }
//        Role role = roleOpt.get();
//
//        User user = mapper.map(requestDTO, User.class);
//        user.setRole(role);
//        user = userRepository.save(user);
//
//        LoanOfficer loanOfficer = new LoanOfficer();
//        loanOfficer.setUser(user);
//        loanOfficer.setAdmin(admin);
//        loanOfficer = loanOfficerRepository.save(loanOfficer);
//
//        mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
//              .addMapping(User::getId, LoanOfficerResponseDTO::setId)
//              .addMapping(src -> admin.getId(), LoanOfficerResponseDTO::setAdminId)
//              .addMapping(src -> Collections.emptyList(), LoanOfficerResponseDTO::setCustomerIds);
//        return mapper.map(user, LoanOfficerResponseDTO.class);
//    }
//
//    @Override
//    public LoanOfficerResponseDTO getLoanOfficerById(int id) {
//        Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(id);
//        if (loanOfficerOpt.isEmpty()) {
//            throw new RuntimeException("Loan Officer not found with ID: " + id);
//        }
//        LoanOfficer loanOfficer = loanOfficerOpt.get();
//        User user = loanOfficer.getUser();
//
//        mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
//              .addMapping(User::getId, LoanOfficerResponseDTO::setId)
//              .addMapping(src -> loanOfficer.getAdmin().getId(), LoanOfficerResponseDTO::setAdminId)
//              .addMapping(src -> loanOfficer.getCustomers().stream().map(Customer::getId)
//                      .collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
//        return mapper.map(user, LoanOfficerResponseDTO.class);
//    }
//
//    @Override
//    public List<LoanOfficerResponseDTO> getLoanOfficersByAdminId(int adminId) {
//        Optional<Admin> adminOpt = adminRepository.findById(adminId);
//        if (adminOpt.isEmpty()) {
//            throw new RuntimeException("Admin not found with ID: " + adminId);
//        }
//        Admin admin = adminOpt.get();
//
//        List<LoanOfficer> loanOfficers = loanOfficerRepository.findByAdminId(adminId);
//
//        return loanOfficers.stream().map(loanOfficer -> {
//            User user = loanOfficer.getUser();
//            mapper.typeMap(User.class, LoanOfficerResponseDTO.class)
//                  .addMapping(User::getId, LoanOfficerResponseDTO::setId)
//                  .addMapping(src -> loanOfficer.getAdmin().getId(), LoanOfficerResponseDTO::setAdminId)
//                  .addMapping(src -> loanOfficer.getCustomers().stream().map(Customer::getId)
//                          .collect(Collectors.toList()), LoanOfficerResponseDTO::setCustomerIds);
//            return mapper.map(user, LoanOfficerResponseDTO.class);
//        }).collect(Collectors.toList());
//    }
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
//import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import com.aurionpro.lms.repository.RoleRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
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
//	@Autowired
//	private AdminRepository adminRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	@Override
//	public LoanOfficerResponseDTO addLoanOfficer(int adminId, LoanOfficerRequestDTO requestDTO) {
//		Optional<Admin> adminOpt = adminRepository.findById(adminId);
//		if (adminOpt.isEmpty()) {
//			throw new RuntimeException("Admin not found with ID: " + adminId);
//		}
//		Admin admin = adminOpt.get();
//
//		Optional<Role> roleOpt = roleRepository.findByRoleName("LOAN_OFFICER");
//		if (roleOpt.isEmpty()) {
//			throw new RuntimeException("Role not found: LOAN_OFFICER");
//		}
//		Role role = roleOpt.get();
//
//		User user = new User();
//		user.setUsername(requestDTO.getUsername());
//		user.setEmail(requestDTO.getEmail());
//		user.setPassword(requestDTO.getPassword());
//		user.setRole(role);
//		user = userRepository.save(user);
//
//		LoanOfficer loanOfficer = new LoanOfficer();
//		loanOfficer.setUser(user);
//		loanOfficer.setAdmin(admin);
//		loanOfficer = loanOfficerRepository.save(loanOfficer);
//
//		LoanOfficerResponseDTO dto = new LoanOfficerResponseDTO();
//		dto.setId(user.getId());
//		dto.setUsername(user.getUsername());
//		dto.setEmail(user.getEmail());
//		dto.setAdminId(admin.getId());
//		dto.setCustomerIds(Collections.emptyList());
//		return dto;
//	}
//
//	@Override
//	public LoanOfficerResponseDTO getLoanOfficerById(int id) {
//		Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(id);
//		if (loanOfficerOpt.isEmpty()) {
//			throw new RuntimeException("Loan Officer not found with ID: " + id);
//		}
//		LoanOfficer loanOfficer = loanOfficerOpt.get();
//		User user = loanOfficer.getUser();
//
//		LoanOfficerResponseDTO dto = new LoanOfficerResponseDTO();
//		dto.setId(user.getId());
//		dto.setUsername(user.getUsername());
//		dto.setEmail(user.getEmail());
//		dto.setAdminId(loanOfficer.getAdmin() != null ? loanOfficer.getAdmin().getId() : 0);
//		dto.setCustomerIds(loanOfficer.getCustomers() != null
//				? loanOfficer.getCustomers().stream().map(Customer::getId).collect(Collectors.toList())
//				: Collections.emptyList());
//		return dto;
//	}
//
//	@Override
//	public List<LoanOfficerResponseDTO> getLoanOfficersByAdminId(int adminId) {
//		Optional<Admin> adminOpt = adminRepository.findById(adminId);
//		if (adminOpt.isEmpty()) {
//			throw new RuntimeException("Admin not found with ID: " + adminId);
//		}
//
//		List<LoanOfficer> loanOfficers = loanOfficerRepository.findByAdminId(adminId);
//		return loanOfficers.stream().map(loanOfficer -> {
//			User user = loanOfficer.getUser();
//			LoanOfficerResponseDTO dto = new LoanOfficerResponseDTO();
//			dto.setId(user.getId());
//			dto.setUsername(user.getUsername());
//			dto.setEmail(user.getEmail());
//			dto.setAdminId(loanOfficer.getAdmin() != null ? loanOfficer.getAdmin().getId() : 0);
//			dto.setCustomerIds(loanOfficer.getCustomers() != null
//					? loanOfficer.getCustomers().stream().map(Customer::getId).collect(Collectors.toList())
//					: Collections.emptyList());
//			return dto;
//		}).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
//import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.entity.Role;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import com.aurionpro.lms.repository.RoleRepository;
//import com.aurionpro.lms.repository.UserRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanOfficerServiceImpl implements LoanOfficerService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private LoanOfficerRepository loanOfficerRepository;
//
//    @Override
//    public LoanOfficerResponseDTO addLoanOfficer(int adminId, LoanOfficerRequestDTO requestDTO) {
//        Optional<Admin> adminOpt = adminRepository.findById(adminId);
//        if (adminOpt.isEmpty()) {
//            throw new RuntimeException("Admin not found with ID: " + adminId);
//        }
//        Admin admin = adminOpt.get();
//
//        Optional<Role> roleOpt = roleRepository.findByRoleName("LOAN_OFFICER");
//        if (roleOpt.isEmpty()) {
//            throw new RuntimeException("Role not found: LOAN_OFFICER");
//        }
//        Role role = roleOpt.get();
//
//        User user = new User();
//        user.setUsername(requestDTO.getUsername());
//        user.setEmail(requestDTO.getEmail());
//        user.setPassword(requestDTO.getPassword());
//        user.setRole(role);
//        user = userRepository.save(user);
//
//        LoanOfficer loanOfficer = new LoanOfficer();
//        loanOfficer.setUser(user);
//        loanOfficer.setAdmin(admin);
//        loanOfficer = loanOfficerRepository.save(loanOfficer);
//
//        LoanOfficerResponseDTO dto = new LoanOfficerResponseDTO();
//        dto.setId(user.getId());
//        dto.setUsername(user.getUsername());
//        dto.setEmail(user.getEmail());
//        dto.setAdminId(admin.getId());
//        dto.setCustomerIds(Collections.emptyList());
//        return dto;
//    }
//
//    @Override
//    public LoanOfficerResponseDTO getLoanOfficerById(int id) {
//        Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(id);
//        if (loanOfficerOpt.isEmpty()) {
//            throw new RuntimeException("Loan Officer not found with ID: " + id);
//        }
//        LoanOfficer loanOfficer = loanOfficerOpt.get();
//        User user = loanOfficer.getUser();
//
//        LoanOfficerResponseDTO dto = new LoanOfficerResponseDTO();
//        dto.setId(user.getId());
//        dto.setUsername(user.getUsername());
//        dto.setEmail(user.getEmail());
//        dto.setAdminId(loanOfficer.getAdmin() != null ? loanOfficer.getAdmin().getId() : 0);
//        dto.setCustomerIds(loanOfficer.getCustomers() != null
//                ? loanOfficer.getCustomers().stream().map(Customer::getId).collect(Collectors.toList())
//                : Collections.emptyList());
//        return dto;
//    }
//
//    @Override
//    public List<LoanOfficerResponseDTO> getLoanOfficersByAdminId(int adminId) {
//        Optional<Admin> adminOpt = adminRepository.findById(adminId);
//        if (adminOpt.isEmpty()) {
//            throw new RuntimeException("Admin not found with ID: " + adminId);
//        }
//
//        List<LoanOfficer> loanOfficers = loanOfficerRepository.findByAdminId(adminId);
//        return loanOfficers.stream().map(loanOfficer -> {
//            User user = loanOfficer.getUser();
//            LoanOfficerResponseDTO dto = new LoanOfficerResponseDTO();
//            dto.setId(user.getId());
//            dto.setUsername(user.getUsername());
//            dto.setEmail(user.getEmail());
//            dto.setAdminId(loanOfficer.getAdmin() != null ? loanOfficer.getAdmin().getId() : 0);
//            dto.setCustomerIds(loanOfficer.getCustomers() != null
//                    ? loanOfficer.getCustomers().stream().map(Customer::getId).collect(Collectors.toList())
//                    : Collections.emptyList());
//            return dto;
//        }).collect(Collectors.toList());
//    }
//}

package com.aurionpro.lms.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.dto.LoanOfficerRequestDTO;
import com.aurionpro.lms.dto.LoanOfficerResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.entity.Role;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.AdminRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import com.aurionpro.lms.repository.RoleRepository;
import com.aurionpro.lms.repository.UserRepository;

@Service
public class LoanOfficerServiceImpl implements LoanOfficerService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private LoanOfficerRepository loanOfficerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public LoanOfficerResponseDTO addLoanOfficer(int adminId, LoanOfficerRequestDTO requestDTO) {
		Optional<Admin> adminOpt = adminRepository.findById(adminId);
		if (adminOpt.isEmpty()) {
			throw new ResourceNotFoundException("Admin not found with ID: " + adminId);
		}
		Admin admin = adminOpt.get();

		Optional<Role> roleOpt = roleRepository.findByRoleName("ROLE_LOAN_OFFICER");
		if (roleOpt.isEmpty()) {
			throw new ResourceNotFoundException("Role not found: LOAN_OFFICER");
		}
		Role role = roleOpt.get();

		User user = new User();
		user.setUsername(requestDTO.getUsername());
		user.setEmail(requestDTO.getEmail());
		user.setPassword((passwordEncoder.encode(requestDTO.getPassword())));
		user.setRole(role);
		user = userRepository.save(user);

		LoanOfficer loanOfficer = new LoanOfficer();
		loanOfficer.setUser(user);
		loanOfficer.setAdmin(admin);
		loanOfficer = loanOfficerRepository.save(loanOfficer);

		LoanOfficerResponseDTO dto = new LoanOfficerResponseDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setAdminId(admin.getId());
		dto.setCustomerIds(Collections.emptyList());
		return dto;
	}

	@Override
	public LoanOfficerResponseDTO getLoanOfficerById(int id) {
		Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(id);
		if (loanOfficerOpt.isEmpty()) {
			throw new ResourceNotFoundException("Loan Officer not found with ID: " + id);
		}
		LoanOfficer loanOfficer = loanOfficerOpt.get();
		User user = loanOfficer.getUser();

		LoanOfficerResponseDTO dto = new LoanOfficerResponseDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setAdminId(loanOfficer.getAdmin() != null ? loanOfficer.getAdmin().getId() : 0);
		dto.setCustomerIds(loanOfficer.getCustomers() != null
				? loanOfficer.getCustomers().stream().map(Customer::getId).collect(Collectors.toList())
				: Collections.emptyList());
		return dto;
	}

	@Override
	public List<LoanOfficerResponseDTO> getLoanOfficersByAdminId(int adminId) {
		Optional<Admin> adminOpt = adminRepository.findById(adminId);
		if (adminOpt.isEmpty()) {
			throw new ResourceNotFoundException("Admin not found with ID: " + adminId);
		}

		List<LoanOfficer> loanOfficers = loanOfficerRepository.findByAdminId(adminId);
		if (loanOfficers.isEmpty()) {
			throw new ResourceNotFoundException("No loan officers found for Admin ID: " + adminId);
		}
		return loanOfficers.stream().map(loanOfficer -> {
			User user = loanOfficer.getUser();
			LoanOfficerResponseDTO dto = new LoanOfficerResponseDTO();
			dto.setId(user.getId());
			dto.setUsername(user.getUsername());
			dto.setEmail(user.getEmail());
			dto.setAdminId(loanOfficer.getAdmin() != null ? loanOfficer.getAdmin().getId() : 0);
			dto.setCustomerIds(loanOfficer.getCustomers() != null
					? loanOfficer.getCustomers().stream().map(Customer::getId).collect(Collectors.toList())
					: Collections.emptyList());
			return dto;
		}).collect(Collectors.toList());
	}
}