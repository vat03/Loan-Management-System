package com.aurionpro.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanOfficerResponseDTO {
	private int id;
	private String username;
	private String email;
	private int adminId; // The admin who added this loan officer
	private List<Integer> customerIds; // List of customer IDs managed by this loan officer
}