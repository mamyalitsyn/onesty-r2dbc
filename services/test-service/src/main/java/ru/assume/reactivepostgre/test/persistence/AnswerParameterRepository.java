package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.assume.reactivepostgre.test.persistence.entity.AnswerParameterEntity;

public interface AnswerParameterRepository extends ReactiveCrudRepository<AnswerParameterEntity, String> {
}
