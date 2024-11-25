package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TestCardRepository extends ReactiveCrudRepository<TestCardEntity, String> {
}
