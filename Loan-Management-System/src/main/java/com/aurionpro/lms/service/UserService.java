package com.aurionpro.lms.service;

import com.aurionpro.lms.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
	User saveUser(User user);

	Optional<User> getUserById(int id);

	Optional<User> getUserByEmail(String email);

	List<User> getAllUsers();

	User updateUser(User user);

	void deleteUser(int id);
}