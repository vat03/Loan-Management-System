//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.UserRepository;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	private ModelMapper mapper;
//
//	private CustomerServiceImpl() {
//		this.mapper = new ModelMapper();
//	}
//
//	@Override
//	public CustomerResponseDTO getCustomerById(int id) {
//		Optional<User> userOpt = userRepository.findById(id);
//		if (userOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + id);
//		}
//		Optional<Customer> customerOpt = customerRepository.findByUserId(id);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + id);
//		}
//		User user = userOpt.get();
//		Customer customer = customerOpt.get();
//
//		mapper.typeMap(User.class, CustomerResponseDTO.class).addMapping(src -> customer.getLoanOfficer().getId(),
//				CustomerResponseDTO::setLoanOfficerId);
//		return mapper.map(user, CustomerResponseDTO.class);
//	}
//
//	@Override
//	public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
//		Optional<LoanOfficer> loOpt = loanOfficerRepository.findById(loanOfficerId);
//		if (loOpt.isEmpty()) {
//			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
//		}
//
//		List<Customer> customers = customerRepository.findAll().stream()
//				.filter(customer -> customer.getLoanOfficer().getId() == loanOfficerId).collect(Collectors.toList());
//
//		return customers.stream().map(customer -> {
//			User user = customer.getUser();
//			mapper.typeMap(User.class, CustomerResponseDTO.class).addMapping(src -> customer.getLoanOfficer().getId(),
//					CustomerResponseDTO::setLoanOfficerId);
//			return mapper.map(user, CustomerResponseDTO.class);
//		}).collect(Collectors.toList());
//	}
//
//	@Override
//	public void assignLoanOfficer(int customerId, int loanOfficerId) {
//		Optional<User> customerOpt = userRepository.findById(customerId);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + customerId);
//		}
//		Optional<Customer> customerEntityOpt = customerRepository.findByUserId(customerId);
//		if (customerEntityOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + customerId);
//		}
//		Customer customer = customerEntityOpt.get();
//
//		Optional<LoanOfficer> loOpt = loanOfficerRepository.findById(loanOfficerId);
//		if (loOpt.isEmpty()) {
//			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
//		}
//		LoanOfficer loanOfficer = loOpt.get();
//
//		customer.setLoanOfficer(loanOfficer);
//		customerRepository.save(customer);
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
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
//public class CustomerServiceImpl implements CustomerService {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private LoanOfficerRepository loanOfficerRepository;
//
//    private ModelMapper mapper;
//
//    private CustomerServiceImpl() {
//        this.mapper = new ModelMapper();
//    }
//
//    @Override
//    public CustomerResponseDTO getCustomerById(int id) {
//        Optional<Customer> customerOpt = customerRepository.findById(id);
//        if (customerOpt.isEmpty()) {
//            throw new RuntimeException("Customer not found with ID: " + id);
//        }
//        Customer customer = customerOpt.get();
//
//        mapper.typeMap(Customer.class, CustomerResponseDTO.class)
//              .addMapping(src -> src.getUser().getEmail(), CustomerResponseDTO::setEmail)
//              .addMapping(src -> src.getUser().getUsername(), CustomerResponseDTO::setUsername)
//              .addMapping(src -> src.getLoanOfficer() != null ? src.getLoanOfficer().getId() : null, CustomerResponseDTO::setLoanOfficerId);
//        return mapper.map(customer, CustomerResponseDTO.class);
//    }
//
//    @Override
//    public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
//        List<Customer> customers = customerRepository.findByLoanOfficerId(loanOfficerId); // Now defined
//        mapper.typeMap(Customer.class, CustomerResponseDTO.class)
//              .addMapping(src -> src.getUser().getEmail(), CustomerResponseDTO::setEmail)
//              .addMapping(src -> src.getUser().getUsername(), CustomerResponseDTO::setUsername)
//              .addMapping(src -> src.getLoanOfficer() != null ? src.getLoanOfficer().getId() : null, CustomerResponseDTO::setLoanOfficerId);
//        return customers.stream()
//                .map(customer -> mapper.map(customer, CustomerResponseDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void assignLoanOfficer(int customerId, int loanOfficerId) {
//        Optional<Customer> customerOpt = customerRepository.findById(customerId);
//        if (customerOpt.isEmpty()) {
//            throw new RuntimeException("Customer not found with ID: " + customerId);
//        }
//        Customer customer = customerOpt.get();
//
//        Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(loanOfficerId);
//        if (loanOfficerOpt.isEmpty()) {
//            throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
//        }
//        LoanOfficer loanOfficer = loanOfficerOpt.get();
//
//        customer.setLoanOfficer(loanOfficer);
//        customerRepository.save(customer);
//    }
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
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
//public class CustomerServiceImpl implements CustomerService {
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	private final ModelMapper mapper;
//
//	public CustomerServiceImpl() {
//		this.mapper = new ModelMapper();
//		// Configure mapping once in constructor
//		mapper.typeMap(Customer.class, CustomerResponseDTO.class).addMappings(mapping -> {
//			mapping.map(Customer::getId, CustomerResponseDTO::setId); // Explicit ID mapping
//			mapping.map(src -> src.getUser() != null ? src.getUser().getEmail() : null, CustomerResponseDTO::setEmail);
//			mapping.map(src -> src.getUser() != null ? src.getUser().getUsername() : null,
//					CustomerResponseDTO::setUsername);
//			mapping.map(src -> src.getLoanOfficer() != null ? src.getLoanOfficer().getId() : 0,
//					CustomerResponseDTO::setLoanOfficerId);
//		});
//	}
//
//	@Override
//	public CustomerResponseDTO getCustomerById(int id) {
//		Optional<Customer> customerOpt = customerRepository.findById(id);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + id);
//		}
//		Customer customer = customerOpt.get();
//		return mapper.map(customer, CustomerResponseDTO.class);
//	}
//
//	@Override
//	public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
//		List<Customer> customers = customerRepository.findByLoanOfficerId(loanOfficerId);
//		return customers.stream().map(customer -> mapper.map(customer, CustomerResponseDTO.class))
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public void assignLoanOfficer(int customerId, int loanOfficerId) {
//		Optional<Customer> customerOpt = customerRepository.findById(customerId);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + customerId);
//		}
//		Customer customer = customerOpt.get();
//
//		Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(loanOfficerId);
//		if (loanOfficerOpt.isEmpty()) {
//			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
//		}
//		LoanOfficer loanOfficer = loanOfficerOpt.get();
//
//		customer.setLoanOfficer(loanOfficer);
//		customerRepository.save(customer);
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import org.hibernate.Hibernate;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//	@Autowired
//	private CustomerRepository customerRepository;
//	
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	private final ModelMapper mapper;
//
//	public CustomerServiceImpl() {
//		this.mapper = new ModelMapper();
//		// No custom typeMap; rely on default mapping with proxy handling
//	}
//
//	@Override
//	public CustomerResponseDTO getCustomerById(int id) {
//		Optional<Customer> customerOpt = customerRepository.findById(id);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + id);
//		}
//		// Unproxy to ensure ModelMapper works with the real Customer instance
//		Customer customer = (Customer) Hibernate.unproxy(customerOpt.get());
//		return mapper.map(customer, CustomerResponseDTO.class);
//	}
//
//	@Override
//	public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
//		List<Customer> customers = customerRepository.findByLoanOfficerId(loanOfficerId);
//		return customers.stream().map(customer -> mapper.map(Hibernate.unproxy(customer), CustomerResponseDTO.class))
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public void assignLoanOfficer(int customerId, int loanOfficerId) {
//		Optional<Customer> customerOpt = customerRepository.findById(customerId);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + customerId);
//		}
//		Customer customer = customerOpt.get();
//
//		Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(loanOfficerId);
//		if (loanOfficerOpt.isEmpty()) {
//			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
//		}
//		LoanOfficer loanOfficer = loanOfficerOpt.get();
//
//		customer.setLoanOfficer(loanOfficer);
//		customerRepository.save(customer);
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import org.hibernate.Hibernate;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	private final ModelMapper mapper;
//
//	public CustomerServiceImpl() {
//		this.mapper = new ModelMapper();
//		// Configure mapping once in constructor
//		mapper.typeMap(Customer.class, CustomerResponseDTO.class).addMappings(mapping -> {
//			mapping.map(Customer::getId, CustomerResponseDTO::setId); // Explicit ID mapping
//			mapping.map(src -> src.getUser() != null ? src.getUser().getEmail() : null, CustomerResponseDTO::setEmail);
//			mapping.map(src -> src.getUser() != null ? src.getUser().getUsername() : null,
//					CustomerResponseDTO::setUsername);
//			mapping.map(src -> src.getLoanOfficer() != null ? src.getLoanOfficer().getId() : 0,
//					CustomerResponseDTO::setLoanOfficerId);
//		});
//	}
//
//	@Override
//	public CustomerResponseDTO getCustomerById(int id) {
//		Optional<Customer> customerOpt = customerRepository.findById(id);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + id);
//		}
//		// Unproxy the Customer object to avoid Hibernate proxy issues
//		Customer customer = (Customer) Hibernate.unproxy(customerOpt.get());
//		return mapper.map(customer, CustomerResponseDTO.class);
//	}
//
//	@Override
//	public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
//		List<Customer> customers = customerRepository.findByLoanOfficerId(loanOfficerId);
//		return customers.stream().map(customer -> mapper.map(Hibernate.unproxy(customer), CustomerResponseDTO.class))
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public void assignLoanOfficer(int customerId, int loanOfficerId) {
//		Optional<Customer> customerOpt = customerRepository.findById(customerId);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + customerId);
//		}
//		Customer customer = customerOpt.get();
//
//		Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(loanOfficerId);
//		if (loanOfficerOpt.isEmpty()) {
//			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
//		}
//		LoanOfficer loanOfficer = loanOfficerOpt.get();
//
//		customer.setLoanOfficer(loanOfficer);
//		customerRepository.save(customer);
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	@Override
//	public CustomerResponseDTO getCustomerById(int id) {
//		Optional<Customer> customerOpt = customerRepository.findById(id);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + id);
//		}
//		Customer customer = customerOpt.get();
//
//		CustomerResponseDTO dto = new CustomerResponseDTO();
//		dto.setId(customer.getId());
//		dto.setEmail(customer.getUser() != null ? customer.getUser().getEmail() : null);
//		dto.setUsername(customer.getUser() != null ? customer.getUser().getUsername() : null);
//		dto.setLoanOfficerId(customer.getLoanOfficer() != null ? customer.getLoanOfficer().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
//		List<Customer> customers = customerRepository.findByLoanOfficerId(loanOfficerId);
//		return customers.stream().map(customer -> {
//			CustomerResponseDTO dto = new CustomerResponseDTO();
//			dto.setId(customer.getId());
//			dto.setEmail(customer.getUser() != null ? customer.getUser().getEmail() : null);
//			dto.setUsername(customer.getUser() != null ? customer.getUser().getUsername() : null);
//			dto.setLoanOfficerId(customer.getLoanOfficer() != null ? customer.getLoanOfficer().getId() : 0);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//
//	@Override
//	public void assignLoanOfficer(int customerId, int loanOfficerId) {
//		Optional<Customer> customerOpt = customerRepository.findById(customerId);
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + customerId);
//		}
//		Customer customer = customerOpt.get();
//
//		Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(loanOfficerId);
//		if (loanOfficerOpt.isEmpty()) {
//			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
//		}
//		LoanOfficer loanOfficer = loanOfficerOpt.get();
//
//		customer.setLoanOfficer(loanOfficer);
//		customerRepository.save(customer);
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.CustomerResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.LoanOfficerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Autowired
//	private LoanOfficerRepository loanOfficerRepository;
//
//	@Override
//	public CustomerResponseDTO getCustomerById(int id) {
//		Optional<Customer> customerOpt = customerRepository.findById(id);
//		if (customerOpt.isEmpty()) {
//			throw new ResourceNotFoundException("Customer not found with ID: " + id);
//		}
//		Customer customer = customerOpt.get();
//
//		CustomerResponseDTO dto = new CustomerResponseDTO();
//		dto.setId(customer.getId());
//		dto.setEmail(customer.getUser() != null ? customer.getUser().getEmail() : null);
//		dto.setUsername(customer.getUser() != null ? customer.getUser().getUsername() : null);
//		dto.setLoanOfficerId(customer.getLoanOfficer() != null ? customer.getLoanOfficer().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
//		List<Customer> customers = customerRepository.findByLoanOfficerId(loanOfficerId);
//		if (customers.isEmpty()) {
//			throw new ResourceNotFoundException("No customers found for Loan Officer ID: " + loanOfficerId);
//		}
//		return customers.stream().map(customer -> {
//			CustomerResponseDTO dto = new CustomerResponseDTO();
//			dto.setId(customer.getId());
//			dto.setEmail(customer.getUser() != null ? customer.getUser().getEmail() : null);
//			dto.setUsername(customer.getUser() != null ? customer.getUser().getUsername() : null);
//			dto.setLoanOfficerId(customer.getLoanOfficer() != null ? customer.getLoanOfficer().getId() : 0);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//
//	@Override
//	public void assignLoanOfficer(int customerId, int loanOfficerId) {
//		Optional<Customer> customerOpt = customerRepository.findById(customerId);
//		if (customerOpt.isEmpty()) {
//			throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
//		}
//		Customer customer = customerOpt.get();
//
//		Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(loanOfficerId);
//		if (loanOfficerOpt.isEmpty()) {
//			throw new ResourceNotFoundException("Loan Officer not found with ID: " + loanOfficerId);
//		}
//		LoanOfficer loanOfficer = loanOfficerOpt.get();
//
//		customer.setLoanOfficer(loanOfficer);
//		customerRepository.save(customer);
//	}
//}

package com.aurionpro.lms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurionpro.lms.dto.CustomerResponseDTO;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import com.aurionpro.lms.repository.LoanRepository;
import com.aurionpro.lms.repository.UserRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private LoanOfficerRepository loanOfficerRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public CustomerResponseDTO getCustomerById(int id) {
		Customer customer = customerRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
		return toCustomerResponseDTO(customer);
	}

	@Override
	public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
		List<Customer> customers = customerRepository.findByLoanOfficerIdAndIsDeletedFalse(loanOfficerId);
		if (customers.isEmpty()) {
			throw new ResourceNotFoundException("No customers found for Loan Officer ID: " + loanOfficerId);
		}
		return customers.stream().map(this::toCustomerResponseDTO).collect(Collectors.toList());
	}

	@Override
	public void assignLoanOfficer(int customerId, int loanOfficerId) {
		Customer customer = customerRepository.findByIdAndIsDeletedFalse(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

		LoanOfficer loanOfficer = loanOfficerRepository.findById(loanOfficerId)
				.orElseThrow(() -> new ResourceNotFoundException("Loan Officer not found with ID: " + loanOfficerId));

		customer.setLoanOfficer(loanOfficer);
		customerRepository.save(customer);
	}

	@Override
	@Transactional
	public void softDeleteCustomer(int customerId, int loanOfficerId) {
		Customer customer = customerRepository.findByIdAndIsDeletedFalse(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

		// Validate loan officer assignment
		if (customer.getLoanOfficer() == null || customer.getLoanOfficer().getId() != loanOfficerId) {
			throw new IllegalStateException("Loan officer is not assigned to this customer");
		}

		// Check for active loans
		List<Loan> loans = loanRepository.findByCustomerId(customerId);
		boolean hasActiveLoans = loans.stream().anyMatch(loan -> loan.getStatus().getId() != 4); // 4 = Completed
		if (hasActiveLoans) {
			throw new IllegalStateException("Cannot soft-delete customer with active loans");
		}

		customer.setDeleted(true);
		customer.getUser().setDeleted(true);
		userRepository.save(customer.getUser());
		customerRepository.save(customer);
	}

	@Override
	@Transactional
	public void selfDeleteCustomer(int customerId) {
		Customer customer = customerRepository.findByIdAndIsDeletedFalse(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

		// Check for active loans
		List<Loan> loans = loanRepository.findByCustomerId(customerId);
		boolean hasActiveLoans = loans.stream().anyMatch(loan -> loan.getStatus().getId() != 4); // 4 = Completed
		if (hasActiveLoans) {
			throw new IllegalStateException("Cannot self-delete account with active loans");
		}

		customer.setDeleted(true);
		customer.getUser().setDeleted(true);
		userRepository.save(customer.getUser());
		customerRepository.save(customer);
	}

	@Override
	public List<CustomerResponseDTO> getAllCustomers(boolean includeDeleted) {
		List<Customer> customers = customerRepository.findAllCustomers(includeDeleted);
		return customers.stream().map(this::toCustomerResponseDTO).collect(Collectors.toList());
	}

	private CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
		CustomerResponseDTO dto = new CustomerResponseDTO();
		dto.setId(customer.getId());
		dto.setEmail(customer.getUser() != null ? customer.getUser().getEmail() : null);
		dto.setUsername(customer.getUser() != null ? customer.getUser().getUsername() : null);
		dto.setLoanOfficerId(customer.getLoanOfficer() != null ? customer.getLoanOfficer().getId() : 0);
		dto.setDeleted(customer.isDeleted());
		return dto;
	}
}