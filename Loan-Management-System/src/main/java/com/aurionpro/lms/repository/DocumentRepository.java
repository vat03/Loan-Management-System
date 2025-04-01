package com.aurionpro.lms.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.lms.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    
    Optional<Document> findByLoanId(int loanId); 
    
    @Query("SELECT d FROM Document d WHERE d.uploadedBy.id = :userId")
    List<Document> findDocumentsUploadedByUser(@Param("userId") int userId);
    
}