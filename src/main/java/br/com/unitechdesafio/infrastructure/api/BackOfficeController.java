package br.com.unitechdesafio.infrastructure.api;

import br.com.unitech.infrastructure.model.BackOfficeEntity;
import br.com.unitech.infrastructure.resource.BackOfficeResource;
import br.com.unitech.infrastructure.service.BackOfficeService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Flux<VO> search() {

        return this.service.search();
    }
}
