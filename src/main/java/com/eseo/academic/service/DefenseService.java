package com.eseo.academic.service;

import com.eseo.academic.entity.Defense;
import com.eseo.academic.entity.User;
import com.eseo.academic.entity.Notification;
import com.eseo.academic.repository.DefenseRepository;
import com.eseo.academic.repository.UserRepository;
import com.eseo.academic.repository.NotificationRepository;
import org.openapitools.model.DefenseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefenseService {

    @Autowired
    private DefenseRepository defenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository; // <-- NOUVEAU

    public void scheduleDefense(DefenseDTO dto) {
        Defense defense = new Defense();

        defense.setDate(dto.getDate().toLocalDateTime());
        defense.setRoom(dto.getRoom());

        User student = userRepository.findById(dto.getStudentEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student not found with email: " + dto.getStudentEmail()));

        defense.setStudent(student);

        defenseRepository.save(defense);

        // --- NOUVEAU : Notify student ---
        Notification notification = new Notification();
        notification.setRecipientEmail(student.getEmail());
        // Clean formatting for date
        String formattedDate = dto.getDate().toLocalDateTime().toLocalDate().toString() + " at " + dto.getDate().toLocalDateTime().toLocalTime().toString();
        notification.setMessage("Your oral defense has been scheduled on " + formattedDate + " in " + dto.getRoom() + ".");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    public List<DefenseDTO> getAllDefenses() {
        return defenseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DefenseDTO convertToDTO(Defense entity) {
        DefenseDTO dto = new DefenseDTO();
        dto.setId(entity.getId()); 

        if (entity.getDate() != null) {
            dto.setDate(entity.getDate().atOffset(ZoneOffset.UTC));
        }

        dto.setRoom(entity.getRoom());

        if (entity.getStudent() != null) {
            dto.setStudentEmail(entity.getStudent().getEmail());
        }

        return dto;
    }
}