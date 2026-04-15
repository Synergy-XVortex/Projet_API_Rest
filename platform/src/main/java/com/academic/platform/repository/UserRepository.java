package com.academic.platform.repository;

import com.academic.platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides basic CRUD operations and custom query methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by their email address.
     * Useful for the login/authentication process.
     * * @param email the email to search for
     * @return an Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Checks if a user exists with the given email.
     * Useful for the registration process to prevent duplicates.
     * * @param email the email to check
     * @return true if the email is already used, false otherwise
     */
    boolean existsByEmail(String email);
}