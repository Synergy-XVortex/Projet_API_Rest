package com.eseo.academic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "submission_date", updatable = false, insertable = false)
    private LocalDateTime submissionDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id", unique = true)
    private Internship internship;
}
