package ru.assume.reactivepostgre.test.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.assume.reactivepostgre.test.persistence.entity.TestPermissionEntity;

public interface TestPermissionRepository extends ReactiveCrudRepository<TestPermissionEntity, String> {
}