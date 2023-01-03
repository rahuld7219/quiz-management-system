package com.qms.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qms.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailId(String emailId);

	Boolean existsByEmailId(String emailId);

//	  Boolean existsByUsername(String username);
}
