package br.com.unitechdesafio.infrastructure.api;

import br.com.unitechdesafio.infrastructure.model.BackOfficeEntity;
import br.com.unitechdesafio.infrastructure.resource.BackOfficeResource;
import br.com.unitechdesafio.infrastructure.service.BackOfficeService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RequiredArgsConstructor
public class BackOfficeController<E extends BackOfficeEntity, VO> implements BackOfficeResource<VO> {

    private final BackOfficeService<E, VO> service;

    public Mono<VO> findById(final String id) {

        return this.service.findById(id);
    }

    public Mono<String> save(final VO valueObject) {

        return this.service.save(valueObject);
    }

    public Mono<Void> edit(String id, VO valueObject) {

        return this.service.edit(id, valueObject);
    }

    public Mono<Void> delete(String id) {

        return this.service.delete(id);
    }

    public Flux<VO> search(Principal principal) {

        return this.service.search(principal);
    }
}
