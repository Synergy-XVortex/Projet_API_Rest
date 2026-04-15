package com.eseo.academic.repository;

import org.springframework.stereotype.Repository;

import com.eseo.academic.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {}
