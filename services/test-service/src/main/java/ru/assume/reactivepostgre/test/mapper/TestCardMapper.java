package ru.assume.reactivepostgre.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.assume.reactivepostgre.test.model.TestCard;
import ru.assume.reactivepostgre.test.persistence.entity.TestCardEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestCardMapper {

    TestCard entityToApi(TestCardEntity testCardEntity);
}
