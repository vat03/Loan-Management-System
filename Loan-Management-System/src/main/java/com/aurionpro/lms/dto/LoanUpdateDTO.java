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
//public class LoanUpdateDTO {
//	@NotBlank(message = "Status name is required")
//	private String statusName; // e.g., "APPROVED", "REJECTED"
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
public class LoanUpdateDTO {
	@NotBlank(message = "Status name is required")
	@Pattern(regexp = "^(PENDING|APPROVED|REJECTED|COMPLETED)$", message = "Status must be PENDING, APPROVED, REJECTED, or COMPLETED")
	private String statusName;
}