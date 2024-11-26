package ru.assume.reactivepostgre.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.assume.reactivepostgre.test.model.*;
import ru.assume.reactivepostgre.test.persistence.entity.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "orderNumber", source = "order")
    TestEntity apiToEntity(TestDomainManagement api);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    TestCardEntity apiToCardEntity(TestCard card, String testId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    QuestionEntity apiToQuestionEntity(QuestionDomainManagement question, String testId, String parameterId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    AnswerEntity apiToAnswerEntity(QuestionDomainManagement.Answer answer, String questionId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    TestPermissionEntity permissionApiToEntity(RoleSubscriptionPermission permission, String testId);
}