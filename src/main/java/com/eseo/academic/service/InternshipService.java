package com.eseo.academic.service;

import com.eseo.academic.entity.Internship;
import com.eseo.academic.entity.InternshipStatus;
import com.eseo.academic.entity.User;
import com.eseo.academic.entity.Company;
import com.eseo.academic.repository.InternshipRepository;
import com.eseo.academic.repository.UserRepository;
import com.eseo.academic.repository.CompanyRepository;
import org.openapitools.model.InternshipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternshipService {

    @Autowired
    private InternshipRepository internshipRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Crée un nouveau stage (Admin)
     */
    public void createNewInternship(InternshipDTO dto) {
        Internship internship = new Internship();
        internship.setObjective(dto.getObjective());
        internship.setStartDate(dto.getStartDate());
        internship.setDurationWeeks(dto.getDurationWeeks());
        internship.setStatus(InternshipStatus.ONGOING);

        // Liaison avec l'entreprise par SIRET (String)
        Company company = companyRepository.findById(dto.getCompanySiret())
                .orElseThrow(() -> new EntityNotFoundException("Entreprise non trouvée avec le SIRET : " + dto.getCompanySiret()));
        internship.setCompany(company);
        
        internshipRepository.save(internship);
    }

    /**
     * Assigne un étudiant et un enseignant à un stage (Admin)
     */
    public void assignParticipants(Long id, InternshipDTO dto) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stage non trouvé"));

        // Liaison avec l'étudiant par Email (String)
        if (dto.getStudentEmail() != null) {
            User student = userRepository.findById(dto.getStudentEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));
            internship.setStudent(student);
        }

        // Liaison avec l'enseignant par Email (String)
        if (dto.getTeacherEmail() != null) {
            User teacher = userRepository.findById(dto.getTeacherEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé"));
            internship.setTeacher(teacher);
        }
        
        internshipRepository.save(internship);
    }

    /**
     * Met à jour le statut du stage (Enseignant)
     */
    public void updateStatus(Long id, String status) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stage non trouvé"));
        
        internship.setStatus(InternshipStatus.valueOf(status.toUpperCase()));
        internshipRepository.save(internship);
    }

    /**
     * Liste les stages avec filtres (Query Parameters de l'OpenAPI)
     */
    public List<InternshipDTO> getInternships(String studentEmail, String status) {
        return internshipRepository.findAll().stream()
                .filter(i -> (studentEmail == null || (i.getStudent() != null && i.getStudent().getEmail().equals(studentEmail))))
                .filter(i -> (status == null || i.getStatus().name().equalsIgnoreCase(status)))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Helper : Conversion Entity -> DTO
     */
    private InternshipDTO convertToDTO(Internship entity) {
        InternshipDTO dto = new InternshipDTO();
        dto.setId(entity.getId()); // C'est un Long (int64 dans OpenAPI)
        dto.setObjective(entity.getObjective());
        dto.setStartDate(entity.getStartDate());
        dto.setDurationWeeks(entity.getDurationWeeks());
        
        if (entity.getStatus() != null) {
            dto.setStatus(InternshipDTO.StatusEnum.fromValue(entity.getStatus().name()));
        }
        
        // On récupère les clés métier (Emails et SIRET) au lieu des IDs techniques
        if (entity.getCompany() != null) {
            dto.setCompanySiret(entity.getCompany().getSiret());
        }
        if (entity.getStudent() != null) {
            dto.setStudentEmail(entity.getStudent().getEmail());
        }
        if (entity.getTeacher() != null) {
            dto.setTeacherEmail(entity.getTeacher().getEmail());
        }
        
        return dto;
    }
}
