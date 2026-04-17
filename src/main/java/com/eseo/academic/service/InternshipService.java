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

    public void createNewInternship(InternshipDTO dto) {
        Internship internship = new Internship();
        internship.setObjective(dto.getObjective());
        internship.setStartDate(dto.getStartDate());
        internship.setDurationWeeks(dto.getDurationWeeks());
        internship.setStatus(InternshipStatus.ONGOING);

        Company company = companyRepository.findById(dto.getCompanySiret())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Entreprise non trouvée avec le SIRET : " + dto.getCompanySiret()));
        internship.setCompany(company);

        internshipRepository.save(internship);
    }

    public void assignParticipants(Long id, InternshipDTO dto) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stage non trouvé"));

        if (dto.getStudentEmail() != null) {
            User student = userRepository.findById(dto.getStudentEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));
            internship.setStudent(student);
        }

        if (dto.getTeacherEmail() != null) {
            User teacher = userRepository.findById(dto.getTeacherEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé"));
            internship.setTeacher(teacher);
        }

        internshipRepository.save(internship);
    }

    public void updateStatus(Long id, String status) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stage non trouvé"));

        internship.setStatus(InternshipStatus.valueOf(status.toUpperCase()));
        internshipRepository.save(internship);
    }

    public List<InternshipDTO> getInternships(String studentEmail, String status) {
        return internshipRepository.findAll().stream()
                .filter(i -> (studentEmail == null
                        || (i.getStudent() != null && i.getStudent().getEmail().equals(studentEmail))))
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

        if (entity.getStatus() != null)
            dto.setStatus(InternshipDTO.StatusEnum.fromValue(entity.getStatus().name()));
        if (entity.getCompany() != null)
            dto.setCompanySiret(entity.getCompany().getSiret());
        if (entity.getStudent() != null)
            dto.setStudentEmail(entity.getStudent().getEmail());
        if (entity.getTeacher() != null)
            dto.setTeacherEmail(entity.getTeacher().getEmail());

        return dto;
    }
}
