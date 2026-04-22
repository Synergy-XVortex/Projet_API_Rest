package com.eseo.academic.controller;

import java.util.List;
import com.eseo.academic.service.CompanyService;
import org.openapitools.api.CompaniesApi;
import org.openapitools.model.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173") 
public class CompaniesController implements CompaniesApi {

    @Autowired
    private CompanyService companyService;

    @Override
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @Override
    public ResponseEntity<CompanyDTO> updateCompany(
            @PathVariable("siret") String siret, 
            @RequestBody CompanyDTO companyDTO) {
        
        CompanyDTO updatedCompany = companyService.updateCompany(siret, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    // AJOUT DE L'ANNOTATION ICI !
    @PostMapping
    public ResponseEntity<Void> addCompany(@RequestBody CompanyDTO companyDTO) {
        // Cette fois-ci, Spring Boot va bien appeler votre service
        companyService.saveCompany(companyDTO);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    // À ajouter dans CompaniesController.java
    @DeleteMapping("/{siret}")
    public ResponseEntity<Void> deleteCompany(@PathVariable String siret) {
        // La méthode deleteCompany existe déjà dans votre service
        companyService.deleteCompany(siret);
        return ResponseEntity.noContent().build();
    }
}