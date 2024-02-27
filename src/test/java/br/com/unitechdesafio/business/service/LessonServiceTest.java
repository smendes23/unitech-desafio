package br.com.unitechdesafio.business.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.unitechdesafio.BaseTestRunner;
import br.com.unitechdesafio.business.domain.dto.request.LessonRequest;
import br.com.unitechdesafio.business.domain.entity.RegistrationEntity;
import br.com.unitechdesafio.business.domain.repository.LessonRepository;
import br.com.unitechdesafio.business.domain.repository.RegistrationRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {LessonService.class})
class LessonServiceTest extends BaseTestRunner {
    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;

    @MockBean
    private LessonRepository lessonRepository;

    @Autowired
    private LessonService lessonService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private RegistrationRepository registrationRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void testValidatePersistVO() {
        // Arrange
        Mono<RegistrationEntity> justResult = Mono.just(new RegistrationEntity());
        when(registrationRepository.findById(Mockito.<String>any())).thenReturn(justResult);
        LessonRequest.LessonRequestBuilder builderResult = LessonRequest.builder();
        LessonRequest valueObject = builderResult
                .date(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .description("The characteristics of someone or something")
                .id("42")
                .teacher("Teacher")
                .title("Dr")
                .build();

        // Act
        lessonService.validatePersistVO(valueObject);

        // Assert
        verify(registrationRepository).findById(eq("Teacher"));
    }

    @Test
    void testValidatePersistVO2() {
        // Arrange
        RegistrationEntity buildResult = RegistrationEntity.builder()
                .email("jane.doe@example.org")
                .id("42")
                .name("Name")
                .status("Status")
                .build();
        Mono<RegistrationEntity> justResult = Mono.just(buildResult);
        when(registrationRepository.findById(Mockito.<String>any())).thenReturn(justResult);
        LessonRequest.LessonRequestBuilder builderResult = LessonRequest.builder();
        LessonRequest valueObject = builderResult
                .date(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .description("The characteristics of someone or something")
                .id("42")
                .teacher("Teacher")
                .title("Dr")
                .build();

        // Act
        lessonService.validatePersistVO(valueObject);

        // Assert
        verify(registrationRepository).findById(eq("Teacher"));
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testGetVoWithFilter() {

        // Arrange and Act
        lessonService.getVoWithFilter();
    }
}
