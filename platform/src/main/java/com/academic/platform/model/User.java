package com.academic.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents a generic user in the academic management platform.
 * Mapped to the "users" table in the database.
 */
@Entity
@Table(name = "users")
@Data // Lombok annotation to automatically generate Getters, Setters, toString, etc.
@NoArgsConstructor // Generates an empty constructor required by Hibernate
@AllArgsConstructor // Generates a constructor with all properties
public class User {

    /**
     * The unique identifier for the user. 
     * Auto-incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    /**
     * The encrypted password. 
     * Kept as String here, but will store a hashed byte array or BCrypt string later.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Flag to determine if the user has administrative privileges.
     */
    @Column(nullable = false)
    private boolean isAdmin;
}