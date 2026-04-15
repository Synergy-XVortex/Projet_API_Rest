package com.eseo.academic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eseo.academic.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    Optional<Report> findByInternship_Id(Long internshipId);
}
