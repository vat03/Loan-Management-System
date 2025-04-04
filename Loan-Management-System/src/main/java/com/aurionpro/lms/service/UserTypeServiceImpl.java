//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.UserTypeResponseDTO;
//import com.aurionpro.lms.dto.UserTypeUpdateDTO;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.entity.UserType;
//import com.aurionpro.lms.repository.UserRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserTypeServiceImpl implements UserTypeService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	private ModelMapper mapper;
//
//	private UserTypeServiceImpl() {
//		this.mapper = new ModelMapper();
//	}
//	
//	@Override
//	public UserTypeResponseDTO updateUserProfile(int userId, UserTypeUpdateDTO updateDTO) {
//		Optional<User> userOpt = userRepository.findById(userId);
//		if (userOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + userId);
//		}
//		User user = userOpt.get();
//		UserType userType = user.getUserType();
//
//		mapper.map(updateDTO, userType); // Maps non-null fields only
//
//		userRepository.save(user);
//
//		return mapper.map(userType, UserTypeResponseDTO.class);
//	}
//
//	@Override
//	public UserTypeResponseDTO getUserProfile(int userId) {
//		Optional<User> userOpt = userRepository.findById(userId);
//		if (userOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + userId);
//		}
//		User user = userOpt.get();
//		return mapper.map(user.getUserType(), UserTypeResponseDTO.class);
//	}
//}