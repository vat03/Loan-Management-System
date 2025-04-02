package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.LoanScheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanSchemeRepository extends JpaRepository<LoanScheme, Integer> {
	List<LoanScheme> findByAdminId(Integer adminId);
}