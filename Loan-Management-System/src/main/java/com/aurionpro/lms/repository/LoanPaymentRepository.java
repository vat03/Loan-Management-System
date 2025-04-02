package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Integer> {
	List<LoanPayment> findByLoanId(Integer loanId);
}