package com.eseo.academic.service;

import com.eseo.academic.entity.Company;
import com.eseo.academic.repository.CompanyRepository;
import org.openapitools.model.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void saveCompany(CompanyDTO dto) {
        Company company = new Company();
        company.setSiret(dto.getSiret());
        company.setCorporateName(dto.getCorporateName());
        company.setAddress(dto.getAddress());
        company.setContactEmail(dto.getContactEmail());
        company.setContactPhone(dto.getContactPhone());

        companyRepository.save(company);
    }

    public CompanyDTO getCompanyBySiret(String siret) {
        Company company = companyRepository.findById(siret)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with SIRET: " + siret));
        return convertToDTO(company);
    }

    /**
     * Met à jour une entreprise et retourne le DTO mis à jour.
     */
    public CompanyDTO updateCompany(String siret, CompanyDTO dto) {
        Company company = companyRepository.findById(siret)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        company.setCorporateName(dto.getCorporateName());
        company.setAddress(dto.getAddress());
        company.setContactEmail(dto.getContactEmail());
        company.setContactPhone(dto.getContactPhone());

        // Sauvegarde de l'entité mise à jour
        Company savedCompany = companyRepository.save(company);
        
        // Retour du DTO converti
        return convertToDTO(savedCompany);
    }

    public void deleteCompany(String siret) {
        if (!companyRepository.existsById(siret)) {
            throw new EntityNotFoundException("Cannot delete: Company not found");
        }
        companyRepository.deleteById(siret);
    }

    private CompanyDTO convertToDTO(Company entity) {
        CompanyDTO dto = new CompanyDTO();
        dto.setSiret(entity.getSiret());
        dto.setCorporateName(entity.getCorporateName());
        dto.setAddress(entity.getAddress());
        dto.setContactEmail(entity.getContactEmail());
        dto.setContactPhone(entity.getContactPhone());
        return dto;
    }
}