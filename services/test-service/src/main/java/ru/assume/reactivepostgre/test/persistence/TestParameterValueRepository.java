package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.test.persistence.entity.TestParameterValueEntity;

public interface TestParameterValueRepository extends ReactiveCrudRepository<TestParameterValueEntity, String> {

    Flux<TestParameterValueEntity> findByTestParameterId(String testParameterId);
}
