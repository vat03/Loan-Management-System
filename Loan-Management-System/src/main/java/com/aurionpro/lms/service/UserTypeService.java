package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.UserTypeResponseDTO;
import com.aurionpro.lms.dto.UserTypeUpdateDTO;

public interface UserTypeService {
	UserTypeResponseDTO updateUserProfile(int userId, UserTypeUpdateDTO updateDTO);

	UserTypeResponseDTO getUserProfile(int userId);
}