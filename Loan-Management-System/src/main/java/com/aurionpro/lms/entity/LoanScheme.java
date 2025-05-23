package com.aurionpro.lms.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loan_schemes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanScheme {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Scheme name is required")
	@Column(nullable = false)
	private String schemeName;

	@NotNull(message = "Interest rate is required")
	@Positive(message = "Interest rate must be positive")
	@Column(nullable = false)
	private BigDecimal interestRate;

	@NotNull(message = "Tenure in months is required")
	@Positive(message = "Tenure must be positive")
	@Column(nullable = false)
	private Integer tenureMonths;

	@NotNull(message = "Admin is required")
	@ManyToOne
	@JoinColumn(name = "admin_id", nullable = false)
	private Admin admin;

	@NotEmpty(message = "At least one document type is required")
	@ManyToMany
	@JoinTable(name = "loan_scheme_document_types", joinColumns = @JoinColumn(name = "loan_scheme_id"), inverseJoinColumns = @JoinColumn(name = "document_type_id"))
	private List<DocumentType> requiredDocumentTypes;
	
	@Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}