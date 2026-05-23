package com.eseo.academic.controller;

import java.io.IOException;

import org.openapitools.api.ReportsEvaluationsApi;
import org.openapitools.model.EvaluationDTO;
import org.openapitools.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eseo.academic.service.ReportService;

import jakarta.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "http://localhost:5173") 
public class ReportsController implements ReportsEvaluationsApi {

    @Autowired
    private ReportService reportService;

    @Override
    public ResponseEntity<ReportDTO> getReportByInternshipId(@NotNull Integer id) {
        ReportDTO report = reportService.getReportByInternship(id.longValue());
        return ResponseEntity.ok(report);
    }

    @Override
    public ResponseEntity<Void> uploadReport(@NotNull Integer id, MultipartFile file) {
        try {
            reportService.saveReport(id.longValue(), file);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> evaluateReport(String reportFileName, EvaluationDTO evaluationDTO) {
        evaluationDTO.setReportFileName(reportFileName);
        String teacherEmail = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();
        reportService.gradeReport(reportFileName, evaluationDTO, teacherEmail);
        return ResponseEntity.ok().build();
    }

    // --- NOUVELLE ROUTE : Permet de télécharger le PDF ---
    @GetMapping("/reports/{fileName}/download")
    public ResponseEntity<Resource> downloadReport(@PathVariable String fileName) {
        Resource resource = reportService.loadReportAsResource(fileName);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}