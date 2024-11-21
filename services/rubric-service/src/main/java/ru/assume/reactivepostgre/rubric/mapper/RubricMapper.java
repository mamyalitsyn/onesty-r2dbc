package ru.assume.reactivepostgre.rubric.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.assume.reactivepostgre.rubric.model.RubricDomainManagement;
import ru.assume.reactivepostgre.rubric.persistence.RubricEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RubricMapper {

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "orderNumber", source = "order")
    RubricEntity rubricDomainManagementToEntity(RubricDomainManagement dto);

    @Mapping(target = "order", source = "orderNumber")
    @Mapping(target = "categoryName", ignore = true)
    RubricDomainManagement entityToRubricDomainManagement(RubricEntity entity);

}
