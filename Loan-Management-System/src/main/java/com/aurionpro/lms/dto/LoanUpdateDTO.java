package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanUpdateDTO {
	@NotBlank(message = "Status name is required")
	private String statusName; // e.g., "APPROVED", "REJECTED"
}