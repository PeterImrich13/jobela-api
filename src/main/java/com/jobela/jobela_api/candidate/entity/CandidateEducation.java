package com.jobela.jobela_api.candidate.entity;


import com.jobela.jobela_api.common.enums.EducationLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_educations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "school_name", nullable = false, length = 150)
    private String schoolName;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", nullable = false, length = 30)
    private EducationLevel level;

    @Column(name = "field_of_study", length = 150)
    private String fieldOfStudy;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "currently_studying", nullable = false)
    @Builder.Default
    private Boolean currentlyStudying = false;

    @Column(length = 3000)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        var now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
