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

    /**
     * Crée une nouvelle entreprise (Admin)
     */
    public void saveCompany(CompanyDTO dto) {
        Company company = new Company();
        // Le SIRET est fourni par le DTO, ce n'est pas un auto-incrément
        company.setSiret(dto.getSiret()); 
        company.setCorporateName(dto.getCorporateName());
        company.setAddress(dto.getAddress());
        company.setContactEmail(dto.getContactEmail());
        company.setContactPhone(dto.getContactPhone());

        companyRepository.save(company);
    }

    /**
     * Récupère une entreprise par son SIRET (String)
     */
    public CompanyDTO getCompanyBySiret(String siret) {
        Company company = companyRepository.findById(siret)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with SIRET: " + siret));
        return convertToDTO(company);
    }

    /**
     * Met à jour une entreprise via son SIRET
     */
    public void updateCompany(String siret, CompanyDTO dto) {
        Company company = companyRepository.findById(siret)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        company.setCorporateName(dto.getCorporateName());
        company.setAddress(dto.getAddress());
        company.setContactEmail(dto.getContactEmail());
        company.setContactPhone(dto.getContactPhone());

        companyRepository.save(company);
    }

    /**
     * Supprime une entreprise par son SIRET
     */
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
