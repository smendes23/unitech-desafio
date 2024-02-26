package br.com.unitechdesafio.business.service;

import br.com.unitechdesafio.business.domain.dto.request.LessonRequest;
import br.com.unitechdesafio.business.domain.entity.LessonEntity;
import br.com.unitechdesafio.business.domain.repository.LessonRepository;
import br.com.unitechdesafio.business.domain.repository.RegistrationRepository;
import br.com.unitechdesafio.infrastructure.service.BackOfficeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.unitechdesafio.business.domain.enums.Status.APROVADO;

@Slf4j
@Service
public class LessonService extends BackOfficeService<LessonEntity, LessonRequest> {
    private final RegistrationRepository registrationRepository;

    public LessonService(
            LessonRepository repository,
            ModelMapper modelMapper,
            ApplicationEventPublisher applicationEventPublisher,
            RegistrationRepository registrationRepository
    ){
        super(repository,modelMapper, applicationEventPublisher);
        this.registrationRepository = registrationRepository;
    }

    @Override
    protected Mono<LessonEntity> validatePersistVO(LessonRequest valueObject) {
        if(Objects.nonNull(valueObject.getTeacher())){
            return registrationRepository
                    .findById(valueObject.getTeacher().getId())
                    .filter(teacher -> Objects.equals(APROVADO.name(), teacher.getStatus()))
                    .map(response -> this.mapValueObjectToEntity(valueObject))
                    .switchIfEmpty(monoResponseStatusNotFoundException("Teacher not available"));
        }
        return Mono.empty();
    }


}
