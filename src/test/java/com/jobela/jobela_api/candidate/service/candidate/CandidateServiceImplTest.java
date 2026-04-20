package com.jobela.jobela_api.candidate.service.candidate;

import com.jobela.jobela_api.candidate.dto.request.candidate.CreateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.request.candidate.UpdateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.response.candidate.CandidateResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.mapper.CandidateMapper;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.common.enums.Gender;
import com.jobela.jobela_api.common.enums.UserRole;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.CandidateAlreadyExistsException;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.exception.UserNotFoundException;
import com.jobela.jobela_api.user.entity.User;
import com.jobela.jobela_api.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceImplTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CandidateMapper candidateMapper;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    @Test
    void createCandidate_shouldCreateCandidateSuccessfully() {
        var user = User.builder()
                .id(1L)
                .email("test@mail.com")
                .password("password123")
                .role(UserRole.CANDIDATE)
                .active(true)
                .build();

        var request = new CreateCandidateRequest(null, null, "Peter", "Imrich",
                Gender.MALE, null, null, null, null, null,
                null, null, null
        );

        var candidate = Candidate.builder()
                .firstName("Peter")
                .lastName("Imrich")
                .gender(Gender.MALE)
                .build();

        var savedCandidate = Candidate.builder()
                .id(10L)
                .user(user)
                .firstName("Peter")
                .lastName("Imrich")
                .gender(Gender.MALE)
                .build();

        var response = new CandidateResponse(10L, 1L, null, null, "Peter",
                "Imrich", Gender.MALE, null, null, null, null, null,
                null, null, null, null, null
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(candidateRepository.existsByUserId(1L)).thenReturn(false);
        when(candidateMapper.toEntity(request)).thenReturn(candidate);
        when(candidateRepository.save(candidate)).thenReturn(savedCandidate);
        when(candidateMapper.toResponse(savedCandidate)).thenReturn(response);

        var result = candidateService.createCandidate(1L, request);

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.userId()).isEqualTo(1L);
        assertThat(result.firstName()).isEqualTo("Peter");
        assertThat(result.lastName()).isEqualTo("Imrich");

        assertThat(candidate.getUser()).isEqualTo(user);
        verify(candidateRepository).save(candidate);
    }

    @Test
    void createCandidate_shouldThrowWhenUserNotFound() {
        var request = new CreateCandidateRequest(
                null, null, "Peter", "Imrich", Gender.MALE, null, null,
                null, null, null, null, null, null);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> candidateService.createCandidate(1L, request))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void createCandidate_shouldThrowWhenCandidateAlreadyExistsForUser() {
        var user = User.builder()
                .id(1L)
                .email("test@mail.com")
                .password("password123")
                .role(UserRole.CANDIDATE)
                .active(true)
                .build();

        var request = new CreateCandidateRequest(
                null, null, "Peter", "Imrich", Gender.MALE, null, null,
                null, null, null, null, null, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(candidateRepository.existsByUserId(1L)).thenReturn(true);

        assertThatThrownBy(() -> candidateService.createCandidate(1L, request))
                .isInstanceOf(CandidateAlreadyExistsException.class);
    }

    @Test
    void createCandidate_shouldThrownWhenFirstNameIsBlank() {
        var user = User.builder()
                .id(1L)
                .email("test@mail.com")
                .password("password123")
                .role(UserRole.CANDIDATE)
                .active(true)
                .build();

        var request = new CreateCandidateRequest(
                null, null, "   ", "Imrich", Gender.MALE, null, null,
                null, null, null, null, null, null);

        var candidate = Candidate.builder()
                .firstName("   ")
                .lastName("Imrich")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(candidateRepository.existsByUserId(1L)).thenReturn(false);
        when(candidateMapper.toEntity(request)).thenReturn(candidate);

        assertThatThrownBy(() -> candidateService.createCandidate(1L, request))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void getCandidateById_shouldThrownWhenCandidateNotFound() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> candidateService.getCandidateById(1L))
                .isInstanceOf(CandidateNotFoundException.class);
    }

    @Test
    void getCandidate_shouldThrownWhenCandidateNotFound() {
        when(candidateRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> candidateService.getCandidateByUserId(1L))
                .isInstanceOf(CandidateNotFoundException.class);
    }

    @Test
    void updateCandidate_shouldUpdateCandidateSuccessfully() {
        var candidate = Candidate.builder()
                .id(10L)
                .firstName("Peter")
                .lastName("Imrich")
                .city("Appenzell")
                .build();

        var request = new UpdateCandidateRequest(null, null, "Peter", "Imrich",
                null, null, "Zurich", null, null, null, null,
                null, null);

        var response = new CandidateResponse(10L, 1L, null, null , "Peter",
                "Imrich", null, null, "Zurich", null, null, null,
                null, null, null, null, null);

        when(candidateRepository.findById(10L)).thenReturn(Optional.of(candidate));

        doAnswer(invocation -> {
            var req = invocation.getArgument(0, UpdateCandidateRequest.class);
            var existingCandidate = invocation.getArgument(1, Candidate.class);

            existingCandidate.setFirstName(req.firstName());
            existingCandidate.setLastName(req.lastName());
            existingCandidate.setCity(req.city());
            return null;
        }).when(candidateMapper).updateCandidateFromRequest(request, candidate);

        when(candidateRepository.save(candidate)).thenReturn(candidate);
        when(candidateMapper.toResponse(candidate)).thenReturn(response);

        var result = candidateService.updateCandidate(10L, request);

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.city()).isEqualTo("Zurich");
        assertThat(candidate.getCity()).isEqualTo("Zurich");

        verify(candidateRepository).save(candidate);
    }

    @Test
    void deleteCandidate_shouldDeleteCandidateSuccessfully() {
        var candidate = Candidate.builder()
                .id(10L)
                .firstName("Peter")
                .lastName("Imrich")
                .build();

        when(candidateRepository.findById(10L)).thenReturn(Optional.of(candidate));

        candidateService.deleteCandidate(10L);

        verify(candidateRepository).delete(candidate);

    }
}
