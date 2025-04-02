package com.aurionpro.lms.dto;

import lombok.Data;

@Data
public class CustomerResponseDTO {
	private int id;
	private String username;
	private String email;
	private int loanOfficerId; // The assigned loan officer
}