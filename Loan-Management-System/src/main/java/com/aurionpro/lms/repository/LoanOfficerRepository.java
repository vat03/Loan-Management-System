package com.aurionpro.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.lms.entity.LoanOfficer;

public interface LoanOfficerRepository extends JpaRepository<LoanOfficer, Integer> {
    
    Optional<LoanOfficer> findByUserId(Long userId); 
    
    @Query("SELECT lo FROM LoanOfficer lo JOIN lo.loans l WHERE l.status = :status")
    List<LoanOfficer> findLoanOfficersByLoanStatus(@Param("status") String status);
}