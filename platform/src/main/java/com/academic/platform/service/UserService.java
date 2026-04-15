package com.academic.platform.service;

import com.academic.platform.model.User;
import com.academic.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class handling the business logic for User operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor-based dependency injection.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user in the system.
     * Verifies if the email is already taken before saving.
     * * @param user the user data to save
     * @return the saved user entity
     * @throws RuntimeException if the email already exists
     */
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("This email is already in use. Please choose another one.");
        }
        
        // TODO: Password encryption should be implemented here later (e.g., using BCrypt)
        
        return userRepository.save(user);
    }

    /**
     * Finds a user by their email address.
     * * @param email the email to search for
     * @return an Optional containing the user if found
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}