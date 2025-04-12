package com.aurionpro.lms.dto;

import lombok.Data;

@Data
public class JwtResponseDTO {
	private String token;
	private Integer userId;
	private String username;
	private String role;
}