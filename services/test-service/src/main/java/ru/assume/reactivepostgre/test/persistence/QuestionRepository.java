package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.test.persistence.entity.QuestionEntity;

public interface QuestionRepository extends ReactiveCrudRepository<QuestionEntity, String> {

    Flux<QuestionEntity> findAllByTestId(String testId);
}
