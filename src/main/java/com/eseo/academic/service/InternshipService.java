package com.eseo.academic.service;

import com.eseo.academic.entity.Internship;
import com.eseo.academic.entity.InternshipStatus;
import com.eseo.academic.entity.User;
import com.eseo.academic.entity.Company;
import com.eseo.academic.entity.Notification;
import com.eseo.academic.repository.InternshipRepository;
import com.eseo.academic.repository.UserRepository;
import com.eseo.academic.repository.CompanyRepository;
import com.eseo.academic.repository.NotificationRepository;
import org.openapitools.model.InternshipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternshipService {

    @Autowired private InternshipRepository internshipRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private NotificationRepository notificationRepository; // <-- NOUVEAU

    public void createNewInternship(InternshipDTO dto) {
        Internship internship = new Internship();
        populateInternshipFields(internship, dto);
        internship.setStatus(InternshipStatus.ONGOING);
        internshipRepository.save(internship);
    }

    public void updateInternship(Long id, InternshipDTO dto) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Internship not found"));
        populateInternshipFields(internship, dto);
        internshipRepository.save(internship);
    }

    private void populateInternshipFields(Internship internship, InternshipDTO dto) {
        if (dto.getObjective() != null) internship.setObjective(dto.getObjective());
        if (dto.getStartDate() != null) internship.setStartDate(dto.getStartDate());
        if (dto.getDurationWeeks() != null) internship.setDurationWeeks(dto.getDurationWeeks());

        if (dto.getCompanySiret() != null && !dto.getCompanySiret().isEmpty()) {
            Company company = companyRepository.findById(dto.getCompanySiret())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            internship.setCompany(company);
        }
        if (dto.getStudentEmail() != null && !dto.getStudentEmail().isEmpty()) {
            User student = userRepository.findById(dto.getStudentEmail()).orElse(null);
            internship.setStudent(student);
        }
        if (dto.getTeacherEmail() != null && !dto.getTeacherEmail().isEmpty()) {
            User teacher = userRepository.findById(dto.getTeacherEmail()).orElse(null);
            internship.setTeacher(teacher);
        }
    }

    public void updateStatus(Long id, String status) {
        Internship internship = internshipRepository.findById(id).orElseThrow();
        internship.setStatus(InternshipStatus.valueOf(status.toUpperCase()));
        internshipRepository.save(internship);

        // --- NOUVEAU : Notify student on status change ---
        if (internship.getStudent() != null) {
            Notification notification = new Notification();
            notification.setRecipientEmail(internship.getStudent().getEmail());
            notification.setMessage("Your internship status has been updated to: " + status);
            notification.setCreatedAt(LocalDateTime.now());
            notification.setRead(false);
            notificationRepository.save(notification);
        }
    }

    public List<InternshipDTO> getInternships(String studentEmail, String status) {
        return internshipRepository.findAll().stream()
                .filter(i -> (studentEmail == null || (i.getStudent() != null && i.getStudent().getEmail().equals(studentEmail))))
                .filter(i -> (status == null || i.getStatus().name().equalsIgnoreCase(status)))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InternshipDTO convertToDTO(Internship entity) {
        InternshipDTO dto = new InternshipDTO();
        dto.setId(entity.getId());
        dto.setObjective(entity.getObjective());
        dto.setStartDate(entity.getStartDate());
        dto.setDurationWeeks(entity.getDurationWeeks());

        if (entity.getStatus() != null) dto.setStatus(InternshipDTO.StatusEnum.fromValue(entity.getStatus().name()));
        if (entity.getCompany() != null) dto.setCompanySiret(entity.getCompany().getSiret());
        if (entity.getStudent() != null) dto.setStudentEmail(entity.getStudent().getEmail());
        if (entity.getTeacher() != null) dto.setTeacherEmail(entity.getTeacher().getEmail());

        return dto;
    }

    public void deleteInternship(Long id) {
        internshipRepository.deleteById(id);
    }
}