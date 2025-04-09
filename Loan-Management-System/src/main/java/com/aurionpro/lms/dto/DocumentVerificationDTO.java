package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DocumentVerificationDTO {
	@NotBlank(message = "Status is required")
	private String status; // "APPROVED" or "REJECTED"
}