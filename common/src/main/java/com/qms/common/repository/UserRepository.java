package com.qms.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qms.common.constant.RoleName;
import com.qms.common.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailId(String emailId);

	Boolean existsByEmailId(String emailId);

//	  Boolean existsByUsername(String username);

	Long countByRolesRoleName(RoleName roleName);

//	Long countDistinctByScoresUserRolesRoleName(RoleName roleName); // TODO: optimize it

	// TODO: optimize it, find way to write using only JPA method OR use JPQL
	// instead of native, (native vs JPQL ??)
	@Query(value = "SELECT COUNT(DISTINCT s.user_id) FROM user_score s, user_role r WHERE s.user_id = r.user_id AND r.role_id = ?1", nativeQuery = true)
	Long countAttendeeAttemptedQuiz(Long roleId);

	Long getIdByEmailId(String extractUserFromSecurityContext);

//	  Boolean existsByUsername(String username);
}
