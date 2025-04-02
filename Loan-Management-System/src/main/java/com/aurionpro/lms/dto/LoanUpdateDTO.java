package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoanUpdateDTO {
	@NotBlank(message = "Status name is required")
	private String statusName; // e.g., "APPROVED", "REJECTED"
}