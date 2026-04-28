package com.jobela.jobela_api.config;

import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.candidate.repository.CandidateSkillRepository;
import com.jobela.jobela_api.common.enums.UserRole;
import com.jobela.jobela_api.security.jwt.JwtService;
import com.jobela.jobela_api.security.user.CustomUserDetails;
import com.jobela.jobela_api.user.entity.User;
import com.jobela.jobela_api.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public abstract class IntegrationTestBase extends PostgresTestContainerConfig {

    @Autowired
    protected CandidateSkillRepository candidateSkillRepository;

    @Autowired
    protected CandidateRepository candidateRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @BeforeEach
    void cleanDatabase() {
        candidateSkillRepository.deleteAll();
        candidateRepository.deleteAll();
        userRepository.deleteAll();
    }

    protected String bearerTokenForUser(User user) {
        var token = jwtService.generateToken(new CustomUserDetails(user));
        return "Bearer " + token;
    }

    protected User createAuthenticatedUser(UserRole role) {
        return userRepository.save(
                User.builder()
                        .email(UUID.randomUUID() + "@mail.com")
                        .password(passwordEncoder.encode("password123"))
                        .role(role)
                        .active(true)
                        .build()
        );
    }

    protected String bearerToken(UserRole role) {
        var user = createAuthenticatedUser(role);
        return bearerTokenForUser(user);
    }
}
