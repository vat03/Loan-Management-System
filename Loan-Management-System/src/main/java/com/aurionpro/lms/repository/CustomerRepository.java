//package com.aurionpro.lms.repository;
//
//import com.aurionpro.lms.entity.Customer;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//	List<Customer> findByLoanOfficerId(Integer loanOfficerId);
//}
//-----------------------------------------------------------------------------------------------------
//package com.aurionpro.lms.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.aurionpro.lms.entity.Customer;
//
//public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//	Optional<Customer> findByUserId(int userId);
//}

package com.aurionpro.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.lms.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	//Customer findByUserId(int userId);
	Optional<Customer> findByUserId(int userId);
	
	List<Customer> findByLoanOfficerId(int loanOfficerId);
}