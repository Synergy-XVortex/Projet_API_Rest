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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class ReportService {

    @Autowired private ReportRepository reportRepository;
    @Autowired private InternshipRepository internshipRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private EvaluationRepository evaluationRepository;

    public void saveReport(Long internshipId, MultipartFile file) throws IOException {
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new EntityNotFoundException("Internship not found"));

        String uploadDir = "/uploads/reports/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        String cleanFileName = originalFileName != null ? originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_") : "report.pdf";
        String safeFileName = System.currentTimeMillis() + "_" + cleanFileName;
        
        Path filePath = uploadPath.resolve(safeFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Report report = reportRepository.findByInternship_Id(internshipId).orElse(new Report());

        report.setInternship(internship);
        report.setFileName(originalFileName); 
        report.setStoragePath(filePath.toString()); 
        report.setSubmissionDate(LocalDateTime.now()); 

        reportRepository.save(report);
    }

    @Transactional
    public void gradeReport(String reportFileName, EvaluationDTO dto, String teacherEmail) {
        Report report = reportRepository.findById(reportFileName)
                .orElseThrow(() -> new EntityNotFoundException("Report not found: " + reportFileName));

        User teacher = userRepository.findById(teacherEmail)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with email: " + teacherEmail));

        Evaluation evaluation = evaluationRepository.findById(reportFileName).orElse(new Evaluation());

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

    // --- NOUVELLE MÉTHODE : Charge le fichier en mémoire ---
    public Resource loadReportAsResource(String fileName) {
        Report report = reportRepository.findById(fileName)
                .orElseThrow(() -> new EntityNotFoundException("Report not found: " + fileName));
        try {
            Path filePath = Paths.get(report.getStoragePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found on disk");
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File path invalid", ex);
        }
    }

    private ReportDTO convertToDTO(Report entity) {
        ReportDTO dto = new ReportDTO();
        dto.setFileName(entity.getFileName());

        if (entity.getSubmissionDate() != null)
            dto.setSubmissionDate(entity.getSubmissionDate().atOffset(ZoneOffset.UTC));

        dto.setInternshipId(entity.getInternship().getId());

        evaluationRepository.findById(entity.getFileName()).ifPresent(eval -> {
            EvaluationDTO evalDTO = new EvaluationDTO();
            if (eval.getGrade() != null) {
                evalDTO.setGrade(eval.getGrade().floatValue());
            }
            evalDTO.setComment(eval.getComment());
            if (eval.getTeacher() != null) {
                evalDTO.setTeacherEmail(eval.getTeacher().getEmail());
            }
            evalDTO.setReportFileName(entity.getFileName());
            dto.setEvaluation(evalDTO);
        });

        return dto;
    }
}