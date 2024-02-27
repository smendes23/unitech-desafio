package br.com.unitechdesafio.business.service;

import br.com.unitechdesafio.business.domain.dto.request.LessonRequest;
import br.com.unitechdesafio.business.domain.entity.LessonEntity;
import br.com.unitechdesafio.business.domain.entity.UserDetailsEntity;
import br.com.unitechdesafio.business.domain.repository.LessonRepository;
import br.com.unitechdesafio.business.domain.repository.RegistrationRepository;
import br.com.unitechdesafio.infrastructure.service.BackOfficeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.reactivestreams.Publisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.unitechdesafio.business.domain.enums.Role.*;
import static br.com.unitechdesafio.business.domain.enums.Status.APROVADO;

@Slf4j
@Service
public class LessonService extends BackOfficeService<LessonEntity, LessonRequest> {
    private final RegistrationRepository registrationRepository;
    private final LessonRepository repository;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;

    public LessonService(
            LessonRepository repository,
            ModelMapper modelMapper,
            ApplicationEventPublisher applicationEventPublisher,
            RegistrationRepository registrationRepository,
            UserDetailsService userDetailsService
    ){
        super(repository,modelMapper, applicationEventPublisher);
        this.registrationRepository = registrationRepository;
        this.userDetailsService = userDetailsService;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    protected Mono<LessonEntity> validatePersistVO(LessonRequest valueObject) {
        if(Objects.nonNull(valueObject.getTeacher())){
            return registrationRepository
                    .findById(valueObject.getTeacher())
                    .filter(teacher -> Objects.equals(APROVADO.name(), teacher.getStatus()))
                    .map(response -> this.mapValueObjectToEntity(valueObject))
                    .switchIfEmpty(monoResponseStatusNotFoundException("Teacher not available"));
        }
        return Mono.empty();
    }

    @Override
    protected Flux<LessonRequest> getVoWithFilter() {
        return userDetailsService.findDetails(principal.getName())
                .flatMapMany(this::validateRoleLessonQuery)
                .map(entity -> this.modelMapper.map(entity, getValueObjectClass()));
    }

    private Publisher<? extends LessonEntity> validateRoleLessonQuery(UserDetailsEntity principal) {
        if (principal.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals(ROLE_USER.name()))) {
            return repository.findByTeacher(principal.getCode());
        }
        return repository.findAll();
    }
}
