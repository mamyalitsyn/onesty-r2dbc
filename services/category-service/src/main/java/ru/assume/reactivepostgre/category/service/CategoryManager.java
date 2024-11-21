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
import ru.assume.reactivepostgre.category.persistence.CategoryEntity;
import ru.assume.reactivepostgre.category.persistence.CategoryPermissionEntity;
import ru.assume.reactivepostgre.category.persistence.CategoryPermissionRepository;
import ru.assume.reactivepostgre.category.persistence.CategoryRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryManager {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryPermissionRepository categoryPermissionRepository;

    public Flux<CategoryDomainShort> getCategoriesDomain() {
        return categoryRepository.findAll()
                .flatMap(this::loadCategoryWithPermissions);
    }

    public Flux<CategoryDomainManagement> createCategories(List<CategoryDomainManagement> categories) {
        return Flux.fromIterable(categories)
                .flatMap(this::saveCategoryWithPermissions);

    }

    public Flux<CategoryPermissionsDomain> getCategoriesPermissions() {
        return categoryPermissionRepository.findAll()
                .collectList()
                .flatMapMany(this::groupPermissionsByCategory);
    }

    private Mono<CategoryDomainManagement> saveCategoryWithPermissions(CategoryDomainManagement categoryDomain) {
        CategoryEntity categoryEntity = categoryMapper.categoryDomainManagementToEntity(categoryDomain);
        categoryEntity.setId(UUID.randomUUID().toString());

        return categoryRepository.save(categoryEntity)
                .flatMap(savedCategory -> {
                    Set<CategoryPermissionEntity> permissionEntities = categoryDomain.getSearchPermissions()
                            .stream().map(categoryMapper::categoryPermissionToEntity).collect(Collectors.toSet());
                    permissionEntities.forEach(permission -> {
                        permission.setId(UUID.randomUUID().toString());
                        permission.setCategoryId(savedCategory.getId());
                    });

                    return categoryPermissionRepository.saveAll(permissionEntities).collectList()
                            .flatMap(savedPermissions -> {
                                Set<RoleSubscriptionPermission> permissions = savedPermissions.stream().map(categoryMapper::entityToCategoryPermissions).collect(Collectors.toSet());
                                CategoryDomainManagement savedModel = categoryMapper.entityToCategoryDomainManagement(savedCategory, permissions);
                                savedModel.setTempId(categoryDomain.getTempId());
                                return Mono.just(savedModel);
                            });
                });
    }

    private Mono<CategoryDomainShort> loadCategoryWithPermissions(CategoryEntity categoryEntity) {
        return categoryPermissionRepository.findByCategoryId(categoryEntity.getId())
                .map(categoryMapper::entityToCategoryPermissions)
                .collect(Collectors.toSet())
                .map(permissions -> categoryMapper.entityToCategoryDomainShort(categoryEntity, permissions));
    }

    private Flux<CategoryPermissionsDomain> groupPermissionsByCategory(List<CategoryPermissionEntity> permissions) {
        return Flux.fromIterable(
                permissions.stream()
                        .collect(Collectors.groupingBy(CategoryPermissionEntity::getCategoryId))
                        .entrySet()
                        .stream()
                        .map(entry -> {
                            String categoryId = entry.getKey();
                            Set<RoleSubscriptionPermission> searchPermissions = entry.getValue().stream()
                                    .map(categoryMapper::entityToCategoryPermissions)
                                    .collect(Collectors.toSet());
                            return new CategoryPermissionsDomain(categoryId, searchPermissions);
                        })
                        .toList()
        );
    }
}
