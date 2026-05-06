package com.jobela.jobela_api.employer.entity;

import com.jobela.jobela_api.common.enums.Industry;
import com.jobela.jobela_api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(name = "company_description", nullable = false, length = 3000)
    private String companyDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Industry industry;

    @Column(length = 100)
    private String website;

    @Column(length = 50)
    private String phone;

    @Column(name = "contact_email", length = 255)
    private String contactEmail;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String street;

    @Column(name = "street_number", length = 10)
    private String streetNumber;

    @Column(name = "profile_photo", length = 500)
    private String profilePhoto;

    @Column(name = "profile_visible", nullable = false)
    @Builder.Default
    private boolean profileVisible = true;

    @Column
    @Builder.Default
    private boolean verified = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        var now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
