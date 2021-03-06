package com.example.semm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.semm.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	Optional<User> getByUsername(String username);

	boolean existsByEmail(String email);
}
