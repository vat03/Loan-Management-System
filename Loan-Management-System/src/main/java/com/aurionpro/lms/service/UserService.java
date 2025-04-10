package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoginRequestDTO;
import com.aurionpro.lms.dto.UserRequestDTO;
import com.aurionpro.lms.dto.UserResponseDTO;

public interface UserService {
	UserResponseDTO registerUser(UserRequestDTO requestDTO, String roleName);

	UserResponseDTO getUserById(int id);
	
	UserResponseDTO login(LoginRequestDTO loginRequestDTO);
}