package ru.assume.reactivepostgre.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.assume.reactivepostgre.test.model.QuestionDomainManagement;
import ru.assume.reactivepostgre.test.model.TestCard;
import ru.assume.reactivepostgre.test.model.TestDomainManagement;
import ru.assume.reactivepostgre.test.persistence.entity.AnswerEntity;
import ru.assume.reactivepostgre.test.persistence.entity.QuestionEntity;
import ru.assume.reactivepostgre.test.persistence.entity.TestCardEntity;
import ru.assume.reactivepostgre.test.persistence.entity.TestEntity;

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
    @Mapping(target = "questions", ignore = true)
    TestDomainManagement entityToApi(TestEntity api);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    TestCardEntity apiToCardEntity(TestCard card, String testId);

    TestCard cardEntityToApi(TestCardEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    QuestionEntity apiToQuestionEntity(QuestionDomainManagement question, String testId, String parameterId);

    @Mapping(target = "parameterName", ignore = true)
    @Mapping(target = "answers", ignore = true)
    QuestionDomainManagement questionEntityToApi(QuestionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    AnswerEntity apiToAnswerEntity(QuestionDomainManagement.Answer answer, String questionId);

    @Mapping(target = "parameters", ignore = true)
    QuestionDomainManagement.Answer answerEntityToApi(AnswerEntity entity);
}