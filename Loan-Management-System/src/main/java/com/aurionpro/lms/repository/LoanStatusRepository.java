package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanStatusRepository extends JpaRepository<LoanStatus, Integer> {
	Optional<LoanStatus> findByStatusName(String statusName);
}