package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.LoanOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanOfficerRepository extends JpaRepository<LoanOfficer, Integer> {
	List<LoanOfficer> findByAdminId(Integer adminId);
}