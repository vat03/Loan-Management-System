//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoginRequestDTO;
//import com.aurionpro.lms.dto.UserRequestDTO;
//import com.aurionpro.lms.dto.UserResponseDTO;
//
//import java.util.List;
//
//public interface UserService {
//	UserResponseDTO registerUser(UserRequestDTO requestDTO, String roleName);
//
//	UserResponseDTO getUserById(int id);
//
//	UserResponseDTO login(LoginRequestDTO loginRequestDTO);
//
//	List<UserResponseDTO> getAllUsers(boolean includeDeleted);
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.JwtResponseDTO;
import com.aurionpro.lms.dto.LoginRequestDTO;
import com.aurionpro.lms.dto.UserRequestDTO;
import com.aurionpro.lms.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
	UserResponseDTO registerUser(UserRequestDTO requestDTO, String roleName);

	UserResponseDTO getUserById(int id);

	JwtResponseDTO login(LoginRequestDTO loginRequestDTO);

	List<UserResponseDTO> getAllUsers(boolean includeDeleted);
	
	JwtResponseDTO generateTestToken(int userId, String username, String roleName);
}