package com.eseo.academic.service;

import com.eseo.academic.entity.Internship;
import com.eseo.academic.entity.Report;
import com.eseo.academic.repository.InternshipRepository;
import com.eseo.academic.repository.ReportRepository;
import org.openapitools.model.EvaluationDTO;
import org.openapitools.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.ZoneOffset;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    
    @Autowired 
    private InternshipRepository internshipRepository;

    /**
     * Enregistre le rapport PDF (Étudiant)
     */
    public void saveReport(Long internshipId, MultipartFile file) throws IOException {
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new EntityNotFoundException("Internship not found"));

        // On vérifie si un rapport existe déjà pour ce stage (1:1)
        Report report = reportRepository.findByInternship_Id(internshipId).orElse(new Report());
        
        report.setInternship(internship);
        report.setFileName(file.getOriginalFilename());
        // Simulation d'un chemin de stockage
        report.setStoragePath("/uploads/reports/" + file.getOriginalFilename());

        reportRepository.save(report);
    }

    /**
     * Note le rapport via le nom du fichier (qui est l'ID)
     */
    public void gradeReport(String reportFileName, EvaluationDTO dto) {
        // On utilise findById car fileName est la clé primaire (@Id)
        Report report = reportRepository.findById(reportFileName)
                .orElseThrow(() -> new EntityNotFoundException("Report not found: " + reportFileName));

        // ... reste du code identique ...
        reportRepository.save(report);
    }

    /**
     * Récupère les infos du rapport pour un stage
     */
    public ReportDTO getReportByInternship(Long internshipId) {
        // Utilisation de la méthode de jointure définie dans le repo
        Report report = reportRepository.findByInternship_Id(internshipId)
                .orElseThrow(() -> new EntityNotFoundException("No report for internship: " + internshipId));
        
        return convertToDTO(report);
    }

    private ReportDTO convertToDTO(Report entity) {
        ReportDTO dto = new ReportDTO();
        dto.setFileName(entity.getFileName());
        
        if (entity.getSubmissionDate() != null) {
            dto.setSubmissionDate(entity.getSubmissionDate().atOffset(ZoneOffset.UTC));
        }
        
        dto.setInternshipId(entity.getInternship().getId());
        return dto;
    }
}
