package ru.assume.reactivepostgre.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.assume.reactivepostgre.test.model.TestCard;
import ru.assume.reactivepostgre.test.model.TestDomainManagement;
import ru.assume.reactivepostgre.test.persistence.TestCardEntity;
import ru.assume.reactivepostgre.test.persistence.TestEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "orderNumber", source = "order")
    TestEntity apiToEntity(TestDomainManagement api);

    @Mapping(target = "order", source = "orderNumber")
    @Mapping(target = "rubricName", ignore = true)
    @Mapping(target = "parameters", ignore = true)
    @Mapping(target = "cards", ignore = true)
    TestDomainManagement entityToApi(TestEntity api);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    TestCardEntity apiToCardEntity(TestCard card, String testId);

    TestCard cardEntityToApi(TestCardEntity entity);
}