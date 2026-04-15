package com.academic.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Represents an internship assignment.
 * Links a student, a supervising teacher, and a company.
 */
@Entity
@Table(name = "internships")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer nbHour;

    /**
     * Status of the internship (e.g., IN_PROGRESS, COMPLETED, VALIDATED, REFUSED).
     * Recommended to use an Enum in a real scenario.
     */
    @Column(nullable = false)
    private String status = "IN_PROGRESS";

    /**
     * The student assigned to this internship.
     */
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /**
     * The teacher supervising this internship.
     */
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    /**
     * The company hosting the internship.
     */
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}