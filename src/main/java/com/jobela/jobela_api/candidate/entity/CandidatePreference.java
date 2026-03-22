package com.jobela.jobela_api.candidate.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidatePreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false, unique = true)
    private Candidate candidate;

    @Column(name = "desired_salary_min")
    private Integer desiredSalaryMin;

    @Column(name = "desired_salary_max")
    private Integer desiredSalaryMax;

    @Column(name = "salary_currency", length = 10)
    private String salaryCurrency;

    @Column(name = "open_to_work", nullable = false)
    @Builder.Default
    private Boolean openToWork = false;

    @Column(name = "availability_date")
    private LocalDate availabilityDate;

    @Column(name = "relocation_preference", nullable = false)
    @Builder.Default
    private Boolean relocationPreference = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updateAt;

    @PrePersist
    public void PrePersist() {
        var now = LocalDateTime.now();
        this.createdAt = now;
        this.updateAt = now;
    }

    @PreUpdate
    public void PreUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}
