package br.com.unitechdesafio.infrastructure.service;

import br.com.unitech.infrastructure.model.BackOfficeEntity;
import br.com.unitech.infrastructure.repository.BackOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
public abstract class BackOfficeService<T extends BackOfficeEntity, VO> {

    private final BackOfficeRepository<T> repository;

    private final ModelMapper modelMapper;

    protected final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(readOnly = true)
    public Mono<VO> findById(final String id) {
        return this.repository
                .findById(id)
                .map(this::mapEntityToValueObject);
    }

    @Transactional
    public Mono<String> save(final VO valueObject) {
        return validatePersistVO(valueObject)
                .filter(Objects::nonNull)
                .map(this.repository::insert)
                .flatMap(response -> response.map(BackOfficeEntity::getId));
    }

    @Transactional
    public Mono<Void> edit(final String id, final VO valueObject) {

        return validateUpdateVO(id,valueObject)
                .filter(Objects::nonNull)
                .map(repository::save)
                .map(response -> response.map(this::postAction))
                .then();
    }

    public Mono<Void> delete(final String id) {
        return repository.findById(id)
                .map(this::mapEntityToValueObject)
                .map(this::validateDeleteVO)
                .flatMap(repository::delete);
    }

    public Flux<VO> search() {

        /*var exampleMatcher = ExampleMatcher.matchingAll()

                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)

                .withIgnoreCase();

        var example = Example.of(this.mapValueObjectToEntity(valueObject), exampleMatcher);*/

        return this.repository.findAll().map(entity -> this.modelMapper.map(entity, getValueObjectClass()));
    }

    protected VO mapEntityToValueObject(T entity) {

        return this.modelMapper.map(entity, this.getValueObjectClass());
    }

    protected T mapValueObjectToEntity(VO valueObject) {

        return this.modelMapper.map(valueObject, this.getEntityClass());
    }

    @SuppressWarnings("unchecked")
    protected Class<VO> getValueObjectClass() {

        return (Class<VO>) Objects.requireNonNull(
                GenericTypeResolver
                        .resolveTypeArguments(this.getClass(), BackOfficeService.class))[1];
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {

        return (Class<T>) Objects.
                requireNonNull(GenericTypeResolver.resolveTypeArguments(this.getClass(), BackOfficeService.class))[0];
    }

    protected Mono<T> validatePersistVO(VO valueObject){
        return Mono.just(this.mapValueObjectToEntity(valueObject));
    }

    protected Mono<T> validateUpdateVO(final String id, VO valueObject){
        var customerEntity = this.mapValueObjectToEntity(valueObject);
        customerEntity.setId(id);
        return Mono.just(customerEntity);
    }

    protected T validateDeleteVO(VO valueObject){
        return this.mapValueObjectToEntity(valueObject);
    }

    protected Mono<Void> postAction(T valueObject){
        return Mono.just(valueObject).then();
    }

    protected  <T> Mono<T> monoResponseStatusNotFoundException(String message) {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

}