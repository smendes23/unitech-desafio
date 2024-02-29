package br.com.unitechdesafio.business.service;

import br.com.unitechdesafio.business.domain.dto.request.RegistrationRequest;
import br.com.unitechdesafio.business.domain.entity.RegistrationEntity;
import br.com.unitechdesafio.business.domain.entity.UserDetailsEntity;
import br.com.unitechdesafio.business.domain.enums.Status;
import br.com.unitechdesafio.business.domain.repository.RegistrationRepository;
import br.com.unitechdesafio.business.domain.repository.UserDetailsRepository;
import br.com.unitechdesafio.infrastructure.event.BackOfficeEvent;
import br.com.unitechdesafio.infrastructure.service.BackOfficeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

import static br.com.unitechdesafio.business.domain.enums.Role.ROLE_USER;
import static br.com.unitechdesafio.business.domain.enums.Status.PENDENTE;

@Slf4j
@Service
public class RegistrationService extends BackOfficeService<RegistrationEntity, RegistrationRequest> {
    private final UserDetailsRepository userDetailsRepository;

    public RegistrationService(
            RegistrationRepository repository,
            ModelMapper modelMapper,
            ApplicationEventPublisher applicationEventPublisher,
            UserDetailsRepository userDetailsRepository
    ){
        super(repository,modelMapper, applicationEventPublisher);
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    protected Mono<RegistrationEntity> validatePersistVO(final RegistrationRequest valueObject) {
        RegistrationEntity entity = this.mapValueObjectToEntity(valueObject);
        entity.setStatus(PENDENTE.name());
        return Mono.just(entity);
    }

    @Override
    protected RegistrationEntity validateDeleteVO(final RegistrationRequest valueObject) {
        log.info("Validando a exclusão do objeto");
        return this.mapValueObjectToEntity(valueObject);
    }

    @Override
    protected Mono<RegistrationEntity> validateUpdateVO(final String id, final RegistrationRequest valueObject) {
        var customerEntity = this.mapValueObjectToEntity(valueObject);
        customerEntity.setId(id);
        return Mono.just(customerEntity).map(customer -> {
            if(Objects.equals(Status.APROVADO.name(), customer.getStatus())){
                UserDetailsEntity user = UserDetailsEntity.builder()
                        .code(id)
                        .authorities(ROLE_USER.name())
                        .username(valueObject.getEmail())
                        .password(passwordEncrypt("lerolero"))
                        .enabled(true)
                        .build();

                userDetailsRepository
                        .save(user)
                        .subscribeOn(Schedulers.boundedElastic())
                        .doOnNext(res ->
                                applicationEventPublisher
                                        .publishEvent(new BackOfficeEvent(res)))
                        .subscribe();
            }
            return customer;
        });
    }

    private String passwordEncrypt( final String password ) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
