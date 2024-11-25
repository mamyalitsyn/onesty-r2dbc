package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.assume.reactivepostgre.test.persistence.entity.TestEntity;

public interface TestRepository extends ReactiveCrudRepository<TestEntity, String> {
}
