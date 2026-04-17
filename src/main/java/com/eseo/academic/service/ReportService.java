package com.eseo.academic.service;

import com.eseo.academic.entity.Evaluation;
import com.eseo.academic.entity.Internship;
import com.eseo.academic.entity.Report;
import com.eseo.academic.entity.User;
import com.eseo.academic.repository.EvaluationRepository;
import com.eseo.academic.repository.InternshipRepository;
import com.eseo.academic.repository.ReportRepository;
import com.eseo.academic.repository.UserRepository;

import org.openapitools.model.EvaluationDTO;
import org.openapitools.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    public void saveReport(Long internshipId, MultipartFile file) throws IOException {
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new EntityNotFoundException("Internship not found"));

        Report report = reportRepository.findByInternship_Id(internshipId).orElse(new Report());

        report.setInternship(internship);
        report.setFileName(file.getOriginalFilename());
        // Attention : En prod, on utilise souvent un UUID pour éviter les doublons de noms
        report.setStoragePath("/uploads/reports/" + file.getOriginalFilename());
        report.setSubmissionDate(LocalDateTime.now()); // Optionnel : pour tracer la date d'envoi

        reportRepository.save(report);
    }

    @Transactional
    public void gradeReport(String reportFileName, EvaluationDTO dto, String teacherEmail) {
        Report report = reportRepository.findById(reportFileName)
                .orElseThrow(() -> new EntityNotFoundException("Report not found: " + reportFileName));

        User teacher = userRepository.findById(teacherEmail)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with email: " + teacherEmail));

        Evaluation evaluation = evaluationRepository.findById(reportFileName)
                .orElse(new Evaluation());

        evaluation.setReport(report); 
        evaluation.setGrade(dto.getGrade().doubleValue());
        evaluation.setComment(dto.getComment());
        evaluation.setTeacher(teacher);

        evaluationRepository.save(evaluation);
    }

    public ReportDTO getReportByInternship(Long internshipId) {
        Report report = reportRepository.findByInternship_Id(internshipId)
                .orElseThrow(() -> new EntityNotFoundException("No report for internship: " + internshipId));

        return convertToDTO(report);
    }

    private ReportDTO convertToDTO(Report entity) {
        ReportDTO dto = new ReportDTO();
        dto.setFileName(entity.getFileName());

        if (entity.getSubmissionDate() != null)
            dto.setSubmissionDate(entity.getSubmissionDate().atOffset(ZoneOffset.UTC));

        dto.setInternshipId(entity.getInternship().getId());
        return dto;
    }
}
