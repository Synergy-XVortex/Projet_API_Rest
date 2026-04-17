package com.eseo.academic.controller;

import java.util.List;
import com.eseo.academic.service.CompanyService;
import org.openapitools.api.CompaniesApi;
import org.openapitools.model.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompaniesController implements CompaniesApi {

    @Autowired
    private CompanyService companyService;

    @Override
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
}
