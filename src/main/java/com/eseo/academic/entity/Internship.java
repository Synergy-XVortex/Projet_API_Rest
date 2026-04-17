package com.eseo.academic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "internships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String objective;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "duration_weeks")
    private Integer durationWeeks;

    @Enumerated(EnumType.STRING)
    private InternshipStatus status = InternshipStatus.ONGOING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_email")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_email")
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_siret")
    private Company company;
}
