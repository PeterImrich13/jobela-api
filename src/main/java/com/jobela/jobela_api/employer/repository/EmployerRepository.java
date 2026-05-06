package com.jobela.jobela_api.employer.repository;

import com.jobela.jobela_api.employer.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);
    List<Employer> findAllByProfileVisibleTrue();
}
