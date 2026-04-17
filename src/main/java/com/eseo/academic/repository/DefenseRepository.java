package com.eseo.academic.repository;

import com.eseo.academic.entity.Defense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefenseRepository extends JpaRepository<Defense, Integer> {
}
