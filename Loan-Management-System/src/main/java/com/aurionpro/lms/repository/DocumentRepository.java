package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
	List<Document> findByCustomerId(Integer customerId);
}