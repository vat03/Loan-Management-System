package com.aurionpro.lms.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;

	@NotBlank(message = "Role name is required")
	@Column(unique = true)
	private String roleName; // "ADMIN", "LOAN_OFFICER", "CUSTOMER"

	@OneToMany(mappedBy = "role", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<User> users;
}
