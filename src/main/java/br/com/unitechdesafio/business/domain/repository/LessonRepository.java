package br.com.unitechdesafio.business.domain.repository;

import br.com.unitechdesafio.business.domain.entity.LessonEntity;
import br.com.unitechdesafio.infrastructure.repository.BackOfficeRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface LessonRepository extends BackOfficeRepository<LessonEntity> {

    Flux<LessonEntity> findByTeacher(String idTeacher);
}
