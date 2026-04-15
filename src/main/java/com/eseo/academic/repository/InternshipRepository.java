package com.eseo.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eseo.academic.entity.Internship;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {}
