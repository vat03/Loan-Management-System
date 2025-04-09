//package com.aurionpro.lms.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "documents")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class Document {
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int documentId;
//
//	@Column(nullable = false)
//	private String documentName;
//
//	@Column(nullable = false)
//	private String documentUrl; // Cloudinary URL
//
//	@ManyToOne
//	@JoinColumn(name = "customer_id", nullable = false)
//	private Customer customer;
//
//	@ManyToOne
//	@JoinColumn(name = "document_type_id", nullable = false)
//	private DocumentType documentType;
//}

//package com.aurionpro.lms.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "documents")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class Document {
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int documentId;
//
//	@Column(nullable = false)
//	private String documentName;
//
//	@Column(nullable = false)
//	private String documentUrl; // Cloudinary URL
//
//	@Column(nullable = false)
//	private String publicId; // Cloudinary public_id for reference
//
//	@ManyToOne
//	@JoinColumn(name = "customer_id", nullable = false)
//	private Customer customer;
//
//	@ManyToOne
//	@JoinColumn(name = "loan_id", nullable = true)
//	private Loan loan; // Nullable, as documents might be uploaded before loan assignment
//
//	@ManyToOne
//	@JoinColumn(name = "document_type_id", nullable = false)
//	private DocumentType documentType;
//}

package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documents")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Document {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int documentId;

	@Column(nullable = false)
	private String documentName;

	@Column(nullable = false)
	private String documentUrl; // Cloudinary URL

	@Column(nullable = false)
	private String publicId; // Cloudinary public_id for reference

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "loan_id", nullable = true)
	private Loan loan; // Nullable, as documents might be uploaded before loan assignment

	@ManyToOne
	@JoinColumn(name = "document_type_id", nullable = false)
	private DocumentType documentType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DocumentStatus status = DocumentStatus.PENDING_VERIFICATION; // Default status

	public enum DocumentStatus {
		PENDING_VERIFICATION, APPROVED, REJECTED
	}
}