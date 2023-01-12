package com.qms.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qms.admin.constant.RoleName;
import com.qms.admin.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Long countByRolesRoleName(RoleName roleName);

//	Long countDistinctByScoresUserRolesRoleName(RoleName roleName); // TODO: optimize it

	// TODO: optimize it, find way to write using only JPA method OR use JPQL
	// instead of native, (native vs JPQL ??)
	@Query(value = "SELECT COUNT(DISTINCT s.user_id) FROM user_score s, user_role r WHERE s.user_id = r.user_id AND r.role_id = ?1", nativeQuery = true)
	Long countAttendeeAttemptedQuiz(Long roleId);

}
