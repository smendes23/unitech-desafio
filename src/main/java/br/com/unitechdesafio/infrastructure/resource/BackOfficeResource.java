package br.com.unitechdesafio.infrastructure.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

public interface BackOfficeResource<VO> {

    @ResponseBody
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    Mono<VO> findById(@PathVariable String id);

    @ResponseBody
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    Mono<String> save(@RequestBody VO customer);

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    Mono<Void> edit(@PathVariable String id, @RequestBody VO customer);

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    Mono<Void> delete(@PathVariable String id);

    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    Flux<VO> search();
}
