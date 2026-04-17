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

import jakarta.validation.constraints.NotNull;

@RestController
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
}
