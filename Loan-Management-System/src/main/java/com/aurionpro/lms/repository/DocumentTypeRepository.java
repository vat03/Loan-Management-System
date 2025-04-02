package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {
	Optional<DocumentType> findByTypeName(String typeName);
}