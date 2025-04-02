package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
	List<Loan> findByCustomerId(Integer customerId);

	List<Loan> findByLoanOfficerId(Integer loanOfficerId);
}