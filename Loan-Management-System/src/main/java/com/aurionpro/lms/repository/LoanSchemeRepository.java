package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.LoanScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanSchemeRepository extends JpaRepository<LoanScheme, Integer> {
	List<LoanScheme> findByAdminIdAndIsDeletedFalse(Integer adminId);

	Optional<LoanScheme> findByIdAndIsDeletedFalse(Integer id);

	List<LoanScheme> findAllByIsDeletedFalse();

	@Query("SELECT s FROM LoanScheme s WHERE (:includeDeleted = true OR s.isDeleted = false)")
	List<LoanScheme> findAllLoanSchemes(@Param("includeDeleted") boolean includeDeleted);

	@Modifying
	@Query("UPDATE LoanScheme s SET s.isDeleted = :isDeleted WHERE s.id = :id")
	int updateIsDeletedById(@Param("id") Integer id, @Param("isDeleted") boolean isDeleted);
}