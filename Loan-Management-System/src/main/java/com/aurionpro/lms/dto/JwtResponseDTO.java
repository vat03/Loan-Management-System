package com.aurionpro.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtResponseDTO {
	private String token;
	private Integer userId;
	private String username;
	private String role;
	private Integer adminId; // Nullable for non-admins
    private Integer customerId; // Nullable for non-customers
    
}