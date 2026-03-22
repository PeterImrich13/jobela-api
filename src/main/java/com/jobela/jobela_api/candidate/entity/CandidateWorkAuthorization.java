package com.jobela.jobela_api.candidate.entity;


import com.jobela.jobela_api.common.enums.AuthorizationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_work_authorizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateWorkAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(nullable = false, length = 100)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "authorization_type", nullable = false, length = 100)
    private AuthorizationType authorizationType;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @Column(name = "sponsorship_required", nullable = false)
    @Builder.Default
    private Boolean sponsorshipRequired = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }




}
