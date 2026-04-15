package com.eseo.academic.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "defenses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Defense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`date`", nullable = false)
    private LocalDateTime date;

    private String room;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_email", unique = true)
    private User student;
}
