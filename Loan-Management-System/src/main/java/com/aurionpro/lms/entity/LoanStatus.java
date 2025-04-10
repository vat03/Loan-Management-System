//package com.aurionpro.lms.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "loan_statuses")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class LoanStatus {
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//
//	@Column(unique = true)
//	private String statusName; // "PENDING", "APPROVED", "REJECTED", "COMPLETED"
//}

package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loan_statuses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanStatus {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Status name is required")
	@Column(unique = true)
	private String statusName;
}