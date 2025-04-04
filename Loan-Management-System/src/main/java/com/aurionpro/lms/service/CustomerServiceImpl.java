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

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.CustomerResponseDTO;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import com.aurionpro.lms.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanOfficerRepository loanOfficerRepository;

    private ModelMapper mapper;

    private CustomerServiceImpl() {
        this.mapper = new ModelMapper();
    }

    @Override
    public CustomerResponseDTO getCustomerById(int id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
        Customer customer = customerOpt.get();

        mapper.typeMap(Customer.class, CustomerResponseDTO.class)
              .addMapping(src -> src.getUser().getEmail(), CustomerResponseDTO::setEmail)
              .addMapping(src -> src.getUser().getUsername(), CustomerResponseDTO::setUsername)
              .addMapping(src -> src.getLoanOfficer() != null ? src.getLoanOfficer().getId() : null, CustomerResponseDTO::setLoanOfficerId);
        return mapper.map(customer, CustomerResponseDTO.class);
    }

    @Override
    public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
        List<Customer> customers = customerRepository.findByLoanOfficerId(loanOfficerId); // Now defined
        mapper.typeMap(Customer.class, CustomerResponseDTO.class)
              .addMapping(src -> src.getUser().getEmail(), CustomerResponseDTO::setEmail)
              .addMapping(src -> src.getUser().getUsername(), CustomerResponseDTO::setUsername)
              .addMapping(src -> src.getLoanOfficer() != null ? src.getLoanOfficer().getId() : null, CustomerResponseDTO::setLoanOfficerId);
        return customers.stream()
                .map(customer -> mapper.map(customer, CustomerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void assignLoanOfficer(int customerId, int loanOfficerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }
        Customer customer = customerOpt.get();

        Optional<LoanOfficer> loanOfficerOpt = loanOfficerRepository.findById(loanOfficerId);
        if (loanOfficerOpt.isEmpty()) {
            throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
        }
        LoanOfficer loanOfficer = loanOfficerOpt.get();

        customer.setLoanOfficer(loanOfficer);
        customerRepository.save(customer);
    }
}