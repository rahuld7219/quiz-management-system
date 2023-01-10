package com.qms.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qms.admin.constant.RoleName;
import com.qms.admin.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Long countByRoles_RoleName(RoleName admin);

}
