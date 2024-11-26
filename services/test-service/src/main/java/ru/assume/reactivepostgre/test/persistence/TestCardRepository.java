package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.test.persistence.entity.TestCardEntity;

public interface TestCardRepository extends ReactiveCrudRepository<TestCardEntity, String> {

    Flux<TestCardEntity> findAllByTestId(String testId);
}
