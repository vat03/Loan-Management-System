package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.UserRequestDTO;
import com.aurionpro.lms.dto.UserResponseDTO;

public interface UserService {
	UserResponseDTO registerUser(UserRequestDTO userRequestDTO, String roleName);

	UserResponseDTO getUserById(int id);
}