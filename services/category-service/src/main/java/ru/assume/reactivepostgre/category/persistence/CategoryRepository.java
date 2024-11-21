package ru.assume.reactivepostgre.category.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoryRepository extends ReactiveCrudRepository<CategoryEntity, String> {
}
