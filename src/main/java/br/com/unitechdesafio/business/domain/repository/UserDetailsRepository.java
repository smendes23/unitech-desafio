package br.com.unitechdesafio.business.domain.repository;

import br.com.unitechdesafio.business.domain.entity.UserDetailsEntity;
import br.com.unitechdesafio.infrastructure.repository.BackOfficeRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserDetailsRepository extends BackOfficeRepository<UserDetailsEntity> {
    Mono<UserDetailsEntity> findByUsername(String username);
}
