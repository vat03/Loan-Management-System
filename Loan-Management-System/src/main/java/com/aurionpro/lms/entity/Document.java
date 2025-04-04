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

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "document_type_id", nullable = false)
	private DocumentType documentType;
}