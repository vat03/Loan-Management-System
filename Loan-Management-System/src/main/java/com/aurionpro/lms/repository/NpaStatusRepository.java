package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.NpaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NpaStatusRepository extends JpaRepository<NpaStatus, Integer> {
	Optional<NpaStatus> findByStatusName(String statusName);
}