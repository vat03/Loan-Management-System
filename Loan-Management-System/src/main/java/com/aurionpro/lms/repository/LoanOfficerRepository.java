//package com.aurionpro.lms.repository;
//
//import com.aurionpro.lms.entity.LoanOfficer;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface LoanOfficerRepository extends JpaRepository<LoanOfficer, Integer> {
//	List<LoanOfficer> findByAdminId(Integer adminId);
//}

package com.aurionpro.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.lms.entity.LoanOfficer;

public interface LoanOfficerRepository extends JpaRepository<LoanOfficer, Integer> {
    List<LoanOfficer> findByAdminId(int adminId);
    Optional<LoanOfficer> findByUserId(int userId);
}