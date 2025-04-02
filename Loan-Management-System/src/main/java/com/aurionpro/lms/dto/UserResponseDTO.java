package com.aurionpro.lms.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
	private int id;
	private String username;
	private String email;
	private String roleName;
}