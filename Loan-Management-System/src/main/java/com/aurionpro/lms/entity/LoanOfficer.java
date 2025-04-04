//package com.aurionpro.lms.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "loan_officer")
//public class LoanOfficer extends UserType {
//	@ManyToOne
//	@JoinColumn(name = "admin_id", nullable = false)
//	private Admin admin;
//
//	@OneToMany(mappedBy = "loanOfficer", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
//	private List<Customer> customers;
//}

//package com.aurionpro.lms.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "loan_officer")
//public class LoanOfficer {
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//
//	@OneToOne
//	@JoinColumn(name = "user_id", nullable = false, unique = true)
//	private User user;
//
//	@ManyToOne
//	@JoinColumn(name = "admin_id", nullable = false)
//	private Admin admin;
//
//	@OneToMany(mappedBy = "loanOfficer", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
//	private List<Customer> customers;
//}





package com.aurionpro.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "loan_officer")
public class LoanOfficer {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @OneToMany(mappedBy = "loanOfficer", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>(); // Initialized to avoid null
}