package com.eseo.academic.service;

import java.util.List;
import java.util.stream.Collectors;

import org.openapitools.model.UserDTO;
import org.openapitools.model.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eseo.academic.entity.Role;
import com.eseo.academic.entity.User; // Ton entité JPA
import com.eseo.academic.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    /**
     * Crée un nouvel utilisateur (Register)
     */
    public UserDTO registerNewUser(UserRegistration reg) {
        User user = new User();
        user.setEmail(reg.getEmail()); // C'est notre @Id
        user.setFirstName(reg.getFirstName());
        user.setLastName(reg.getLastName());
        user.setRole(Role.valueOf(reg.getRole().name()));
        user.setMajor(reg.getMajor());
        user.setActive(false);

        // Sécurité : hachage obligatoire
        user.setPassword(passwordEncoder.encode(reg.getPassword()));

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    /**
     * Recherche un utilisateur par son Email (anciennement getUserById)
     */
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return convertToDTO(user);
    }

    /**
     * Met à jour un utilisateur via son Email
     */
    public void updateUser(String email, UserDTO dto) {
        User user = userRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        // Note : On évite de changer l'email lui-même car c'est la PK technique
        user.setMajor(dto.getMajor());
        
        userRepository.save(user);
    }

    /**
     * Suppression via Email
     */
    public void removeUser(String email) {
        if (!userRepository.existsById(email)) {
            throw new EntityNotFoundException("Cannot delete: User not found");
        }
        userRepository.deleteById(email);
    }

    /**
     * Helper : Conversion Entity -> DTO
     */
    private UserDTO convertToDTO(User entity) {
        UserDTO dto = new UserDTO();
        // Plus de dto.setId() car l'id est l'email (String)
        dto.setEmail(entity.getEmail()); 
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole().name());
        dto.setMajor(entity.getMajor());
        return dto;
    }

    public List<UserDTO> findUsersByCriteria(String role, String major) {
        return userRepository.findAll().stream()
                .filter(u -> (role == null || u.getRole().name().equalsIgnoreCase(role)))
                .filter(u -> (major == null || u.getMajor().equalsIgnoreCase(major)))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO updateUserPersonal(String email, UserDTO dto) {
        User user = userRepository.findById(email).orElseThrow();
        
        // On ne permet de modifier que les infos de base
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getMajor() != null) user.setMajor(dto.getMajor());
        
        // Le rôle reste inchangé ici !
        
        return convertToDTO(userRepository.save(user));
    }

    public void activateUser(String email) {
        // 1. On cherche l'utilisateur
        User user = userRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'email : " + email));
        
        // 2. On change son statut
        user.setActive(true);
        
        // 3. On sauvegarde
        userRepository.save(user);
    }
}
