package ru.assume.reactivepostgre.category.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CategoryPermissionRepository extends ReactiveCrudRepository<CategoryPermissionEntity, String> {

    Flux<CategoryPermissionEntity> findByCategoryId(String categoryId);
}
