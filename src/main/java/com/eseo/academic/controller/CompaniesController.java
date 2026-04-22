package com.eseo.academic.controller;

import java.util.List;
import com.eseo.academic.service.CompanyService;
import org.openapitools.api.CompaniesApi;
import org.openapitools.model.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// Retirez temporairement le @RequestMapping("/companies") ici 
// car l'interface CompaniesApi le gère probablement déjà.
@CrossOrigin(origins = "http://localhost:5173") 
public class CompaniesController implements CompaniesApi {

    @Autowired
    private CompanyService companyService;

    @Override
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    /**
     * Utilisez @Override pour être certain de respecter l'interface Swagger.
     * Assurez-vous que le nom 'updateCompany' est bien celui défini dans CompaniesApi.
     */
    @Override
    public ResponseEntity<CompanyDTO> updateCompany(
            @PathVariable("siret") String siret, 
            @RequestBody CompanyDTO companyDTO) {
        
        CompanyDTO updatedCompany = companyService.updateCompany(siret, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

}