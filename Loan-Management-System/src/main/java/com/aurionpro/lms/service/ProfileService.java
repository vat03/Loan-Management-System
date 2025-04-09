package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.ProfileUpdateRequestDTO;
import com.aurionpro.lms.dto.ProfileResponseDTO;

public interface ProfileService {
	ProfileResponseDTO updateProfile(int userId, ProfileUpdateRequestDTO requestDTO);

	ProfileResponseDTO updateCustomerProfile(int customerId, ProfileUpdateRequestDTO requestDTO);

	ProfileResponseDTO updateLoanOfficerProfile(int loanOfficerId, ProfileUpdateRequestDTO requestDTO);

	ProfileResponseDTO updateAdminProfile(int adminId, ProfileUpdateRequestDTO requestDTO);

	ProfileResponseDTO getProfileByUserId(int userId);

	ProfileResponseDTO getProfileByCustomerId(int customerId);

	ProfileResponseDTO getProfileByLoanOfficerId(int loanOfficerId);

	ProfileResponseDTO getProfileByAdminId(int adminId);
}