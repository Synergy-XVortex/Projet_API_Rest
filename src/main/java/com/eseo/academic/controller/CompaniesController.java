package com.eseo.academic.controller;

import java.util.List;
import com.eseo.academic.service.CompanyService;
import org.openapitools.api.CompaniesApi;
import org.openapitools.model.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

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
    public ResponseEntity<CompanyDTO> updateCompany(String siret, CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(siret, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    // La route de suppression générée par Swagger est proprement interceptée ici
    @Override
    public ResponseEntity<Void> deleteCompany(String siret) {
        companyService.deleteCompany(siret);
        return ResponseEntity.noContent().build();
    }
    
    // Note : Plus besoin d'ajouter @PostMapping pour la création ici, 
    // car votre AdministratorOnlyController le gère déjà parfaitement !
}