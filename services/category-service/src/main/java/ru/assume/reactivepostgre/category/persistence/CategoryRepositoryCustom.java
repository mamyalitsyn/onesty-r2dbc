package ru.assume.reactivepostgre.category.persistence;

import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.category.model.CategoryDomainShort;

public interface CategoryRepositoryCustom {
    Flux<CategoryDomainShort> findCategoriesWithPermissions();
}
