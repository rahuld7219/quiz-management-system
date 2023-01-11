package com.qms.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qms.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailId(String emailId);

	Boolean existsByEmailId(String emailId);

//	  Boolean existsByUsername(String username);
}
