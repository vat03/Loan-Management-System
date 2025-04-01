package com.aurionpro.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aurionpro.lms.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);  
    
    @Query("SELECT u FROM User u WHERE u.role.name = :roleName")
    List<User> findUsersByRole(@Param("roleName") String roleName);
    
    boolean existsByEmail(String email);
    
}

