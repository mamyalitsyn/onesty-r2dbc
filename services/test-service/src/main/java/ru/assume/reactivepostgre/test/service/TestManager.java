package ru.assume.reactivepostgre.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.assume.reactivepostgre.test.mapper.TestMapper;
import ru.assume.reactivepostgre.test.mapper.TestParameterMapper;
import ru.assume.reactivepostgre.test.model.QuestionDomainManagement;
import ru.assume.reactivepostgre.test.model.TestDomainManagement;
import ru.assume.reactivepostgre.test.persistence.*;
import ru.assume.reactivepostgre.test.persistence.entity.*;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestManager {

    private final TestMapper testMapper;
    private final TestRepository testRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final TestCardRepository testCardRepository;
    private final TestParameterMapper testParameterMapper;
    private final TestParameterRepository testParameterRepository;
    private final AnswerParameterRepository answerParameterRepository;
    private final TestParameterValueRepository testParameterValueRepository;

    public Flux<Void> createTests(List<TestDomainManagement> tests) {
        return Flux.fromIterable(tests)
                .flatMap(test ->
                        testRepository.save(testMapper.apiToEntity(test))
                                .flatMap(savedTest -> {
                                    List<TestParameterEntity> parameters = test.getParameters().stream()
                                            .map(param -> testParameterMapper.apiToEntity(param, savedTest.getId()))
                                            .toList();

                                    Mono<List<TestParameterEntity>> savedParameters = testParameterRepository.saveAll(parameters)
                                            .collectList();

                                    Mono<Void> processedParameters = savedParameters
                                            .flatMapMany(Flux::fromIterable)
                                            .flatMap(savedParam -> {
                                                List<TestParameterValueEntity> parameterValues = test.getParameters().stream()
                                                        .filter(param -> param.getName().equals(savedParam.getName()))
                                                        .flatMap(param -> param.getValue().stream()
                                                                .map(value -> testParameterMapper.apiToValueEntity(value, savedParam.getId())))
                                                        .toList();

                                                return testParameterValueRepository.saveAll(parameterValues).then();
                                            })
                                            .then();

                                    List<TestCardEntity> cards = test.getCards().stream()
                                            .map(card -> testMapper.apiToCardEntity(card, savedTest.getId()))
                                            .toList();

                                    Mono<Void> processedCards = testCardRepository.saveAll(cards).then();

                                    Mono<List<QuestionEntity>> processedQuestions = savedParameters
                                            .map(savedParamList -> Optional.ofNullable(test.getQuestions()).orElse(List.of())
                                                    .stream()
                                                    .map(question -> testMapper.apiToQuestionEntity(
                                                            question, savedTest.getId(),
                                                            findParameterIdByName(savedParamList, question.getParameterName())))
                                                    .toList())
                                            .flatMapMany(questionRepository::saveAll)
                                            .collectList();

                                    Mono<Void> processedAnswers = processedQuestions
                                            .flatMapMany(Flux::fromIterable)
                                            .flatMap(savedQuestion -> {
                                                List<AnswerEntity> answers = Optional.ofNullable(test.getQuestions()).orElse(List.of())
                                                        .stream()
                                                        .filter(q -> q.getText().equals(savedQuestion.getText()))
                                                        .flatMap(q -> Optional.ofNullable(q.getAnswers()).orElse(List.of()).stream()
                                                                .map(answer -> testMapper.apiToAnswerEntity(answer, savedQuestion.getId())))
                                                        .toList();

                                                return answerRepository.saveAll(answers).then();
                                            })
                                            .then();

                                    return processedParameters
                                            .then(processedCards)
                                            .then(processedAnswers);
                                })
                )
                .thenMany(Flux.empty());
    }

    public Mono<Void> updateTestParameters(Map<String, List<QuestionDomainManagement.Parameter>> parameters) {
        return Flux.fromIterable(parameters.entrySet())
                .flatMap(entry -> {
                    String answerId = entry.getKey();
                    List<QuestionDomainManagement.Parameter> params = entry.getValue();

                    return answerRepository.existsById(answerId)
                            .flatMapMany(exists -> {
                                if (!exists) {
                                    return Flux.error(new IllegalArgumentException("Answer with id " + answerId + " does not exist"));
                                }

                                return Flux.fromIterable(params)
                                        .map(param -> {
                                            AnswerParameterEntity entity = new AnswerParameterEntity();
                                            entity.setId(null);
                                            entity.setParameterId(param.getId());
                                            entity.setAnswerId(answerId);
                                            entity.setScore(param.getScore());
                                            return entity;
                                        })
                                        .flatMap(answerParameterRepository::save);
                            });
                })
                .then();
    }

    private String findParameterIdByName(List<TestParameterEntity> savedParameters, String parameterName) {
        return savedParameters.stream()
                .filter(param -> param.getName().equals(parameterName))
                .findFirst()
                .map(TestParameterEntity::getId)
                .orElse(null);
    }
}
