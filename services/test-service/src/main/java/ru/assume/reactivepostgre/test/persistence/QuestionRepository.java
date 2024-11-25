package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.assume.reactivepostgre.test.persistence.entity.QuestionEntity;

public interface QuestionRepository extends ReactiveCrudRepository<QuestionEntity, String> {
}
