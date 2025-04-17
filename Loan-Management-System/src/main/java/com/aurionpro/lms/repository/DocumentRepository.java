package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
	List<Document> findByCustomerId(Integer customerId);
	List<Document> findByLoanLoanId(Integer loanId);
	List<Document> findByLoanLoanId(int loanId);
    List<Document> findByStatus(Document.DocumentStatus status);
    List<Document> findByLoanLoanIdAndStatus(int loanId, Document.DocumentStatus status);
}