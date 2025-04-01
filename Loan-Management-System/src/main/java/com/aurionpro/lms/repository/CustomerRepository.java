package com.aurionpro.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.lms.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    Optional<Customer> findByUserId(Long userId);
    
    @Query("SELECT c FROM Customer c WHERE c.loanOfficer.id = :loanOfficerId")
    List<Customer> findCustomersByLoanOfficer(@Param("loanOfficerId") Long loanOfficerId);
    
}