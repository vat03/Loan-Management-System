//package com.aurionpro.lms.repository;
//
//import com.aurionpro.lms.entity.Admin;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface AdminRepository extends JpaRepository<Admin, Integer> {
//}


package com.aurionpro.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.lms.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Optional<Admin> findByUserId(Integer userId);
}