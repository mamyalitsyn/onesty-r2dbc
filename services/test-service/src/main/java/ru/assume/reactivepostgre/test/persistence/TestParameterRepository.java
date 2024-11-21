package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TestParameterRepository extends ReactiveCrudRepository<TestParameterEntity, String> {

    Flux<TestParameterEntity> findByTestId(String testId);
}
