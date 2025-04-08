package com.aurionpro.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aurionpro.lms.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
	List<Loan> findByCustomerId(Integer customerId);

	List<Loan> findByLoanOfficerId(Integer loanOfficerId);

	@Query("SELECT l FROM Loan l WHERE l.status.statusName NOT IN ('PAID_OFF', 'CLOSED')")
	List<Loan> findAllActiveLoans();
}