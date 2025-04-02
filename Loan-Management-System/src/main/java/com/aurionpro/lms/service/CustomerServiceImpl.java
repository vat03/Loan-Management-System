package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.CustomerResponseDTO;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.entity.User;
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
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CustomerResponseDTO getCustomerById(int id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty() || !(userOpt.get().getUserType() instanceof Customer)) {
			throw new RuntimeException("Customer not found with ID: " + id);
		}
		User user = userOpt.get();

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(User.class, CustomerResponseDTO.class).addMapping(
				src -> ((Customer) src.getUserType()).getLoanOfficer().getId(), CustomerResponseDTO::setLoanOfficerId);
		return mapper.map(user, CustomerResponseDTO.class);
	}

	@Override
	public List<CustomerResponseDTO> getCustomersByLoanOfficerId(int loanOfficerId) {
		Optional<User> loOpt = userRepository.findById(loanOfficerId);
		if (loOpt.isEmpty() || !(loOpt.get().getUserType() instanceof LoanOfficer)) {
			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
		}

		List<User> customers = userRepository.findAll().stream()
				.filter(user -> user.getUserType() instanceof Customer
						&& ((Customer) user.getUserType()).getLoanOfficer().getId() == loanOfficerId) // Fixed: use ==
																										// instead of
																										// equals()
				.collect(Collectors.toList());

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(User.class, CustomerResponseDTO.class).addMapping(
				src -> ((Customer) src.getUserType()).getLoanOfficer().getId(), CustomerResponseDTO::setLoanOfficerId);
		return customers.stream().map(user -> mapper.map(user, CustomerResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public void assignLoanOfficer(int customerId, int loanOfficerId) {
		Optional<User> customerOpt = userRepository.findById(customerId);
		if (customerOpt.isEmpty() || !(customerOpt.get().getUserType() instanceof Customer)) {
			throw new RuntimeException("Customer not found with ID: " + customerId);
		}
		User customerUser = customerOpt.get();
		Customer customer = (Customer) customerUser.getUserType();

		Optional<User> loOpt = userRepository.findById(loanOfficerId);
		if (loOpt.isEmpty() || !(loOpt.get().getUserType() instanceof LoanOfficer)) {
			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
		}
		User loUser = loOpt.get();
		LoanOfficer loanOfficer = (LoanOfficer) loUser.getUserType();

		customer.setLoanOfficer(loanOfficer);
		userRepository.save(customerUser);
	}
}