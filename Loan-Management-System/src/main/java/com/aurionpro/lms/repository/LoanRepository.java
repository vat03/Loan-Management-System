package com.aurionpro.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.lms.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    
    List<Loan> findByCustomerId(int customerId);  
    
    List<Loan> findByLoanOfficerId(int loanOfficerId); 
    
    @Query("SELECT l FROM Loan l WHERE l.status = :status")
    List<Loan> findLoansByStatus(@Param("status") String status); 
}