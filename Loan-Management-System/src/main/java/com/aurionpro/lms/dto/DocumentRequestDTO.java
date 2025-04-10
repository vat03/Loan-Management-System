//package com.aurionpro.lms.dto;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//@Data
//public class DocumentRequestDTO {
//	@NotBlank(message = "Document name is required")
//	private String documentName;
//
//	@NotBlank(message = "Document URL is required")
//	private String documentUrl; // Cloudinary URL
//
//	@NotNull(message = "Customer ID is required")
//	private Integer customerId;
//
//	@NotNull(message = "Document type ID is required")
//	private Integer documentTypeId;
//}

//package com.aurionpro.lms.dto;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import org.springframework.web.multipart.MultipartFile;
//
//import lombok.Data;
//
//@Data
//public class DocumentRequestDTO {
//	@NotBlank(message = "Document name is required")
//	private String documentName;
//
//	@NotNull(message = "Document file is required")
//	private MultipartFile documentFile; // File to upload to Cloudinary
//
//	@NotNull(message = "Customer ID is required")
//	private Integer customerId;
//
//	@NotNull(message = "Document type ID is required")
//	private Integer documentTypeId;
//
//	private Integer loanId;
//}

package com.aurionpro.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentRequestDTO {
	@NotBlank(message = "Document name is required")
	private String documentName;

	@NotNull(message = "Document file is required")
	private MultipartFile documentFile;

	@NotNull(message = "Customer ID is required")
	@Positive(message = "Customer ID must be positive")
	private Integer customerId;

	@NotNull(message = "Document type ID is required")
	@Positive(message = "Document type ID must be positive")
	private Integer documentTypeId;

	@Positive(message = "Loan ID must be positive")
	private Integer loanId;
}