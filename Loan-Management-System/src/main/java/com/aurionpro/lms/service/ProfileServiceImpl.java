package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.ProfileUpdateRequestDTO;
import com.aurionpro.lms.dto.ProfileResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.repository.AdminRepository;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.LoanOfficerRepository;
import com.aurionpro.lms.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private LoanOfficerRepository loanOfficerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public ProfileResponseDTO updateProfile(int userId, ProfileUpdateRequestDTO requestDTO) {
		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isEmpty()) {
			throw new RuntimeException("User not found with ID: " + userId);
		}
		User user = userOpt.get();

		// Verify password
		if (!(requestDTO.getPassword().equals(user.getPassword()))) {
			throw new RuntimeException("Incorrect password");
		}
		
		// Update user fields
		user.setFirstName(requestDTO.getFirstName());
		user.setLastName(requestDTO.getLastName());
		user.setDateOfBirth(requestDTO.getDateOfBirth());
		user.setMobileNumber(requestDTO.getMobileNumber());
		user.setGender(requestDTO.getGender());

		userRepository.save(user);

		return toProfileResponseDTO(user);
	}

	@Override
	public ProfileResponseDTO getProfileByUserId(int userId) {
		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isEmpty()) {
			throw new RuntimeException("User not found with ID: " + userId);
		}
		return toProfileResponseDTO(userOpt.get());
	}

	@Override
	public ProfileResponseDTO getProfileByCustomerId(int customerId) {
		Optional<Customer> customerOpt = customerRepository.findById(customerId);
		if (customerOpt.isEmpty()) {
			throw new RuntimeException("Customer not found with ID: " + customerId);
		}
		return toProfileResponseDTO(customerOpt.get().getUser());
	}

	@Override
	public ProfileResponseDTO getProfileByLoanOfficerId(int loanOfficerId) {
		Optional<LoanOfficer> officerOpt = loanOfficerRepository.findById(loanOfficerId);
		if (officerOpt.isEmpty()) {
			throw new RuntimeException("Loan Officer not found with ID: " + loanOfficerId);
		}
		return toProfileResponseDTO(officerOpt.get().getUser());
	}

	@Override
	public ProfileResponseDTO getProfileByAdminId(int adminId) {
		Optional<Admin> adminOpt = adminRepository.findById(adminId);
		if (adminOpt.isEmpty()) {
			throw new RuntimeException("Admin not found with ID: " + adminId);
		}
		return toProfileResponseDTO(adminOpt.get().getUser());
	}

	private ProfileResponseDTO toProfileResponseDTO(User user) {
		ProfileResponseDTO dto = new ProfileResponseDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setRoleName(user.getRole().getRoleName());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setDateOfBirth(user.getDateOfBirth());
		dto.setMobileNumber(user.getMobileNumber());
		dto.setGender(user.getGender());
		return dto;
	}
}