package ru.assume.reactivepostgre.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.assume.reactivepostgre.category.model.CategoryDomainManagement;
import ru.assume.reactivepostgre.category.model.CategoryDomainShort;
import ru.assume.reactivepostgre.category.model.CategoryPermissionsDomain;
import ru.assume.reactivepostgre.category.model.RoleSubscriptionPermission;
import ru.assume.reactivepostgre.category.persistence.CategoryEntity;
import ru.assume.reactivepostgre.category.persistence.CategoryPermissionEntity;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "orderNumber", source = "order")
    CategoryEntity categoryDomainManagementToEntity(CategoryDomainManagement dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "role", source = "dto.role")
    @Mapping(target = "subscriptions", source = "dto.subscriptions")
    @Mapping(target = "categoryId", ignore = true)
    CategoryPermissionEntity categoryPermissionToEntity(RoleSubscriptionPermission dto);

    @Mapping(target = "order", source = "entity.orderNumber")
    @Mapping(target = "searchPermissions", source = "permissions")
    CategoryDomainShort entityToCategoryDomainShort(CategoryEntity entity, Set<RoleSubscriptionPermission> permissions);

    @Mapping(target = "tempId", ignore = true)
    @Mapping(target = "order", source = "entity.orderNumber")
    @Mapping(target = "searchPermissions", source = "permissions")
    CategoryDomainManagement entityToCategoryDomainManagement(CategoryEntity entity, Set<RoleSubscriptionPermission> permissions);

    RoleSubscriptionPermission entityToCategoryPermissions(CategoryPermissionEntity entity);

    CategoryPermissionsDomain fullToShort(CategoryDomainShort categoryDomainShort);


}

