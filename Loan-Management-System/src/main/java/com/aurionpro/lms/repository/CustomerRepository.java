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

//package com.aurionpro.lms.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.aurionpro.lms.entity.Customer;
//
//public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//	Optional<Customer> findByUserId(int userId);
//	
//	List<Customer> findByLoanOfficerId(int loanOfficerId);
//}

package com.aurionpro.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.lms.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Optional<Customer> findByUserIdAndIsDeletedFalse(int userId);

	List<Customer> findByLoanOfficerIdAndIsDeletedFalse(int loanOfficerId);

	Optional<Customer> findByIdAndIsDeletedFalse(Integer id);

	List<Customer> findAllByIsDeletedFalse();

	@Query("SELECT c FROM Customer c WHERE (:includeDeleted = true OR c.isDeleted = false)")
	List<Customer> findAllCustomers(@Param("includeDeleted") boolean includeDeleted);

	@Modifying
	@Query("UPDATE Customer c SET c.isDeleted = :isDeleted WHERE c.id = :id")
	int updateIsDeletedById(@Param("id") Integer id, @Param("isDeleted") boolean isDeleted);
}