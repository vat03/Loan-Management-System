//package com.aurionpro.lms.repository;
//
//import com.aurionpro.lms.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<User, Integer> {
//    Optional<User> findByEmail(String email);
//
//    Optional<User> findByUsername(String username);
//
//    Optional<User> findByIdAndIsDeletedFalse(Integer id);
//
//    Optional<User> findByEmailAndIsDeletedFalse(String email);
//
//    Optional<User> findByUsernameAndIsDeletedFalse(String username);
//
//    @Query("SELECT u FROM User u WHERE (:includeDeleted = true OR u.isDeleted = false)")
//    List<User> findAllUsers(@Param("includeDeleted") boolean includeDeleted);
//
//    @Modifying
//    @Query("UPDATE User u SET u.isDeleted = :isDeleted WHERE u.id = :id")
//    int updateIsDeletedById(@Param("id") Integer id, @Param("isDeleted") boolean isDeleted);
//}

package com.aurionpro.lms.repository;

import com.aurionpro.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmailAndIsDeletedFalse(String email);

	Optional<User> findByUsernameAndIsDeletedFalse(String username);

	Optional<User> findByIdAndIsDeletedFalse(Integer id);

	@Query("SELECT u FROM User u WHERE (:includeDeleted = true OR u.isDeleted = false)")
	List<User> findAllUsers(boolean includeDeleted);

	@Modifying
	@Query("UPDATE User u SET u.isDeleted = :isDeleted WHERE u.id = :id")
	void updateIsDeletedById(Integer id, boolean isDeleted);
}