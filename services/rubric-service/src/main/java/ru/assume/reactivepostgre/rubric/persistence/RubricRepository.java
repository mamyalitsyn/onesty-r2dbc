package ru.assume.reactivepostgre.rubric.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RubricRepository extends ReactiveCrudRepository<RubricEntity, String> {
}
