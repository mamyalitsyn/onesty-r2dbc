package ru.assume.reactivepostgre.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.category.mapper.CategoryMapper;
import ru.assume.reactivepostgre.category.model.CategoryDomainManagement;
import ru.assume.reactivepostgre.category.model.CategoryDomainShort;
import ru.assume.reactivepostgre.category.model.CategoryPermissionsDomain;
import ru.assume.reactivepostgre.category.model.RoleSubscriptionPermission;
import ru.assume.reactivepostgre.category.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryManager {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryRepositoryCustom categoryRepositoryCustom;
    private final CategoryPermissionRepository categoryPermissionRepository;

    public Flux<CategoryDomainShort> getCategoriesDomain() {
        return categoryRepositoryCustom.findCategoriesWithPermissions();
    }

    public Flux<CategoryDomainManagement> createCategories(List<CategoryDomainManagement> categories) {
        return Flux.fromIterable(categories)
                .flatMap(this::saveCategoryWithPermissions);
    }

    public Flux<CategoryPermissionsDomain> getCategoriesPermissions() {
        return categoryRepositoryCustom.findCategoriesWithPermissions().map(categoryMapper::fullToShort);
    }

    private Mono<CategoryDomainManagement> saveCategoryWithPermissions(CategoryDomainManagement categoryDomain) {
        CategoryEntity categoryEntity = categoryMapper.categoryDomainManagementToEntity(categoryDomain);

        return categoryRepository.save(categoryEntity)
                .flatMap(savedCategory -> {
                    Set<CategoryPermissionEntity> permissionEntities = categoryDomain.getSearchPermissions()
                            .stream().map(categoryMapper::categoryPermissionToEntity).collect(Collectors.toSet());
                    permissionEntities.forEach(permission -> permission.setCategoryId(savedCategory.getId()));

                    return categoryPermissionRepository.saveAll(permissionEntities).collectList()
                            .flatMap(savedPermissions -> {
                                Set<RoleSubscriptionPermission> permissions = savedPermissions.stream().map(categoryMapper::entityToCategoryPermissions).collect(Collectors.toSet());
                                CategoryDomainManagement savedModel = categoryMapper.entityToCategoryDomainManagement(savedCategory, permissions);
                                savedModel.setTempId(categoryDomain.getTempId());
                                return Mono.just(savedModel);
                            });
                });
    }
}
