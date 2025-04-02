package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}