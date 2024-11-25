package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface QuestionRepository extends ReactiveCrudRepository<QuestionEntity, String> {
}
