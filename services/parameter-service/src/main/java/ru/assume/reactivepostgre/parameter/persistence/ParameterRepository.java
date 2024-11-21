package ru.assume.reactivepostgre.parameter.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ParameterRepository extends ReactiveCrudRepository<ParameterEntity, String> {
}
