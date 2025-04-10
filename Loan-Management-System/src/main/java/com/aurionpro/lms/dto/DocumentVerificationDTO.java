//package com.aurionpro.lms.dto;
//
//import jakarta.validation.constraints.NotBlank;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@RequiredArgsConstructor
//public class DocumentVerificationDTO {
//	@NotBlank(message = "Status is required")
//	private String status; // "APPROVED" or "REJECTED"
//}

package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DocumentVerificationDTO {
	@NotBlank(message = "Status is required")
	@Pattern(regexp = "^(APPROVED|REJECTED)$", message = "Status must be APPROVED or REJECTED")
	private String status;
}