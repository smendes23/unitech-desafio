package br.com.unitechdesafio.business.domain.repository;

import br.com.unitechdesafio.business.domain.entity.RegistrationEntity;
import br.com.unitechdesafio.infrastructure.repository.BackOfficeRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RegistrationRepository extends BackOfficeRepository<RegistrationEntity> {

    Mono<RegistrationEntity> findByEmail(String email);


}
