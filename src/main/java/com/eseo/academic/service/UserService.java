package com.eseo.academic.service;

import java.util.List;
import java.util.stream.Collectors;

import org.openapitools.model.UserDTO;
import org.openapitools.model.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eseo.academic.entity.Role;
import com.eseo.academic.entity.User;
import com.eseo.academic.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO registerNewUser(UserRegistration reg) {
        User user = new User();
        user.setEmail(reg.getEmail());
        user.setFirstName(reg.getFirstName());
        user.setLastName(reg.getLastName());
        user.setRole(Role.valueOf(reg.getRole().name()));
        user.setMajor(reg.getMajor());
        user.setActive(false);

        user.setPassword(passwordEncoder.encode(reg.getPassword()));

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return convertToDTO(user);
    }

    public void updateUser(String email, UserDTO dto) {
        User user = userRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Mise à jour des champs qui fonctionnent
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getMajor() != null) user.setMajor(dto.getMajor());
        if (dto.getActive() != null) user.setActive(dto.getActive());

        // --- NOUVELLE GESTION DU RÔLE ---
        if (dto.getRole() != null && !dto.getRole().trim().isEmpty()) {
            
            // 1. On nettoie la chaîne reçue (enlève les espaces et met en majuscules)
            String roleStr = dto.getRole().trim().toUpperCase();
            System.out.println("--- TENTATIVE DE CHANGEMENT DE ROLE VERS : '" + roleStr + "' ---");
            
            try {
                // 2. On essaie de l'affecter normalement
                user.setRole(Role.valueOf(roleStr));
                System.out.println("SUCCES : Le rôle a été modifié en " + roleStr);
                
            } catch (IllegalArgumentException e) {
                // 3. Si ça plante, on essaie avec le préfixe "ROLE_" (très courant en Spring Security)
                try {
                    user.setRole(Role.valueOf("ROLE_" + roleStr));
                    System.out.println("SUCCES : Le rôle a été modifié avec le préfixe ROLE_" + roleStr);
                } catch (IllegalArgumentException ex) {
                    // 4. Si ça plante toujours, on l'affiche EN ROUGE dans votre console au lieu de le cacher !
                    System.err.println("ERREUR FATALE : Le rôle '" + roleStr + "' n'existe pas dans votre Enum Role.java !");
                    throw new IllegalArgumentException("Rôle invalide : " + roleStr);
                }
            }
        }

        userRepository.save(user);
    }

    public void removeUser(String email) {
        if (!userRepository.existsById(email)) {
            throw new EntityNotFoundException("Cannot delete: User not found");
        }
        userRepository.deleteById(email);
    }

    private UserDTO convertToDTO(User entity) {
        UserDTO dto = new UserDTO();
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole().name());
        dto.setMajor(entity.getMajor());
        dto.setActive(entity.isActive());
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

        if (dto.getFirstName() != null)
            user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            user.setLastName(dto.getLastName());
        if (dto.getMajor() != null)
            user.setMajor(dto.getMajor());

        return convertToDTO(userRepository.save(user));
    }

    public void activateUser(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));

        user.setActive(true);
        userRepository.save(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}