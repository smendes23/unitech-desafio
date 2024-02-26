package br.com.unitechdesafio.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BackOfficeRepository<T> extends ReactiveMongoRepository<T, String> {

}
