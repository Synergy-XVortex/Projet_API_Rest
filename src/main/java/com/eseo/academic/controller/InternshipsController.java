package com.eseo.academic.controller;

import java.util.List;

import org.openapitools.api.InternshipsApi;
import org.openapitools.model.InternshipDTO;
import org.openapitools.model.UpdateInternshipStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.eseo.academic.service.InternshipService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class InternshipsController implements InternshipsApi {

    @Autowired
    private InternshipService internshipService;

    @Override
    public ResponseEntity<Void> updateInternshipStatus(@NotNull Integer id,
            @Valid UpdateInternshipStatusRequest updateInternshipStatusRequest) {

        // On récupère le statut depuis l'objet de requête généré par OpenAPI
        String newStatus = updateInternshipStatusRequest.getStatus().getValue();

        internshipService.updateStatus(id.longValue(), newStatus);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<InternshipDTO>> getInternships(String studentEmail, String status) {
        List<InternshipDTO> internships = internshipService.getInternships(studentEmail, status);
        return ResponseEntity.ok(internships);
    }
}
