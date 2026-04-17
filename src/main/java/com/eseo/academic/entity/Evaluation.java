package com.eseo.academic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    private String reportFileName;

    private Double grade;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "`date`", updatable = false, insertable = false)
    private LocalDateTime date;

    @OneToOne
    @MapsId
    @JoinColumn(name = "report_file_name")
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_email")
    private User teacher;
}
