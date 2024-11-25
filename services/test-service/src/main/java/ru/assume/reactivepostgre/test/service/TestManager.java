package ru.assume.reactivepostgre.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.test.mapper.TestMapper;
import ru.assume.reactivepostgre.test.mapper.TestParameterMapper;
import ru.assume.reactivepostgre.test.model.TestCard;
import ru.assume.reactivepostgre.test.model.TestDomainManagement;
import ru.assume.reactivepostgre.test.model.TestParameter;
import ru.assume.reactivepostgre.test.persistence.*;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestManager {

    private final TestMapper testMapper;
    private final TestRepository testRepository;
    private final TestCardRepository testCardRepository;
    private final TestParameterMapper testParameterMapper;
    private final TestParameterRepository testParameterRepository;
    private final TestParameterValueRepository testParameterValueRepository;

    public Flux<TestDomainManagement> createTests(List<TestDomainManagement> tests) {
        return Flux.fromIterable(tests)
                .flatMap(test ->
                        testRepository.save(testMapper.apiToEntity(test))
                                .flatMap(savedTest -> {
                                    List<TestParameterEntity> parameters = test.getParameters().stream()
                                            .map(param -> testParameterMapper.apiToEntity(param, savedTest.getId()))
                                            .toList();

                                    log.info("Mapped {} parameters for test {}", parameters.size(), savedTest.getId());

                                    Mono<List<TestParameter>> processedParameters = testParameterRepository.saveAll(parameters)
                                            .flatMap(savedParam -> {
                                                List<TestParameterValueEntity> parameterValues = test.getParameters().stream()
                                                        .filter(param -> param.getName().equals(savedParam.getName()))
                                                        .flatMap(param -> param.getValue().stream()
                                                                .map(value -> testParameterMapper.apiToValueEntity(value, savedParam.getId())))
                                                        .toList();

                                                return testParameterValueRepository.saveAll(parameterValues)
                                                        .thenMany(testParameterValueRepository.findByTestParameterId(savedParam.getId())
                                                                .collectList()
                                                                .map(savedValues -> {
                                                                    TestParameter mappedParam = testParameterMapper.entityToApi(savedParam);
                                                                    mappedParam.setValue(savedValues.stream()
                                                                            .map(testParameterMapper::valueEntityToApi)
                                                                            .toList());
                                                                    return mappedParam;
                                                                }));
                                            })
                                            .collectList()
                                            .doOnNext(savedParams -> {
                                                test.setId(savedTest.getId());
                                                test.setParameters(savedParams);
                                            });

                                    List<TestCardEntity> cards = test.getCards().stream()
                                            .map(card -> testMapper.apiToCardEntity(card, savedTest.getId()))
                                            .toList();

                                    log.info("Mapped {} cards for test {}", cards.size(), savedTest.getId());

                                    Mono<List<TestCard>> processedCards = testCardRepository.saveAll(cards)
                                            .map(testMapper::cardEntityToApi)
                                            .collectList()
                                            .doOnNext(test::setCards);

                                    return processedParameters
                                            .then(processedCards)
                                            .then(Mono.just(test));
                                })
                );
    }

}
