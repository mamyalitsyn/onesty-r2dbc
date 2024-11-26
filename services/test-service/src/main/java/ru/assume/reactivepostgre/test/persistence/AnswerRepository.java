package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.test.persistence.entity.AnswerEntity;

public interface AnswerRepository extends ReactiveCrudRepository<AnswerEntity, String> {

    Flux<AnswerEntity> findAllByQuestionId(String questionId);
}
