package com.jobela.jobela_api.config;

import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.candidate.repository.CandidateSkillRepository;
import com.jobela.jobela_api.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class IntegrationTestBase extends PostgresTestContainerConfig {

    @Autowired
    protected CandidateSkillRepository candidateSkillRepository;

    @Autowired
    protected CandidateRepository candidateRepository;

    @Autowired
    protected UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        candidateSkillRepository.deleteAll();
        candidateRepository.deleteAll();
        userRepository.deleteAll();
    }
}
