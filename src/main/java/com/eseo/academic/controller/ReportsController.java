package com.eseo.academic.controller;

import java.io.IOException;

import org.openapitools.api.ReportsEvaluationsApi;
import org.openapitools.model.EvaluationDTO;
import org.openapitools.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eseo.academic.service.ReportService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class ReportsController implements ReportsEvaluationsApi {

    @Autowired
    private ReportService reportService;

    // GET /internships/{id}/report
    @Override
    public ResponseEntity<ReportDTO> getReportByInternshipId(@NotNull Integer id) {
        // Conversion Integer -> Long pour le service
        ReportDTO report = reportService.getReportByInternship(id.longValue());
        return ResponseEntity.ok(report);
    }

    // POST /internships/{id}/report (Upload du PDF par l'étudiant)
    @Override
    public ResponseEntity<Void> uploadReport(@NotNull Integer id, MultipartFile file) {
        try {
            reportService.saveReport(id.longValue(), file);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            // En cas d'erreur de lecture du fichier
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST /reports/{reportFileName}/evaluation (Notation par le prof)
    @Override
    public ResponseEntity<Void> evaluateReport(@NotNull String reportFileName, @Valid EvaluationDTO evaluationDTO) {
        reportService.gradeReport(reportFileName, evaluationDTO);
        return ResponseEntity.ok().build();
    }
}
