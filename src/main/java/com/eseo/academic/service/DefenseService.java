package com.eseo.academic.service;

import com.eseo.academic.entity.Defense;
import com.eseo.academic.entity.User;
import com.eseo.academic.repository.DefenseRepository;
import com.eseo.academic.repository.UserRepository;
import org.openapitools.model.DefenseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefenseService {

    @Autowired
    private DefenseRepository defenseRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Planifie une soutenance (Admin)
     */
    public void scheduleDefense(DefenseDTO dto) {
        Defense defense = new Defense();
        
        // Match avec le champ `date` protégé par backticks dans l'entité
        defense.setDate(dto.getDate().toLocalDateTime());
        defense.setRoom(dto.getRoom());

        // On récupère l'étudiant via son email (Clé primaire String)
        User student = userRepository.findById(dto.getStudentEmail())
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé avec l'email : " + dto.getStudentEmail()));
        
        defense.setStudent(student);
        
        defenseRepository.save(defense);
    }

    public List<DefenseDTO> getAllDefenses() {
        return defenseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Helper : Entity -> DTO
     */
    private DefenseDTO convertToDTO(Defense entity) {
        DefenseDTO dto = new DefenseDTO();
        dto.setId(entity.getId()); // Long (int64 dans OpenAPI)
        
        // Conversion LocalDateTime -> OffsetDateTime pour le DTO
        if (entity.getDate() != null) {
            dto.setDate(entity.getDate().atOffset(ZoneOffset.UTC));
        }
        
        dto.setRoom(entity.getRoom());
        
        // On extrait l'email de l'étudiant pour le DTO
        if (entity.getStudent() != null) {
            dto.setStudentEmail(entity.getStudent().getEmail());
        }
        
        return dto;
    }
}
