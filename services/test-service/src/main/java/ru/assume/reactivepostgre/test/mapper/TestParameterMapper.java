package ru.assume.reactivepostgre.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.assume.reactivepostgre.test.model.TestParameter;
import ru.assume.reactivepostgre.test.persistence.entity.TestParameterEntity;
import ru.assume.reactivepostgre.test.persistence.entity.TestParameterValueEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestParameterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "testId", source = "testId")
    TestParameterEntity apiToEntity(TestParameter api, String testId);

    @Mapping(target = "value", ignore = true)
    TestParameter entityToApi(TestParameterEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "testParameterId", source = "parameterId")
    TestParameterValueEntity apiToValueEntity(TestParameter.ParameterValue api, String parameterId);

    TestParameter.ParameterValue valueEntityToApi(TestParameterValueEntity entity);
}
