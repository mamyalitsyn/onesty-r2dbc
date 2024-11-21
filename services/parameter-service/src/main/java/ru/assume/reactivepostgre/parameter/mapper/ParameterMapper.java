package ru.assume.reactivepostgre.parameter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.assume.reactivepostgre.parameter.model.ParameterDomainManagement;
import ru.assume.reactivepostgre.parameter.persistence.ParameterEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ParameterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "orderNumber", source = "order")
    ParameterEntity parameterDomainManagementToEntity(ParameterDomainManagement dto);

    @Mapping(target = "order", source = "orderNumber")
    ParameterDomainManagement entityToParameterDomainManagement(ParameterEntity entity);
}
