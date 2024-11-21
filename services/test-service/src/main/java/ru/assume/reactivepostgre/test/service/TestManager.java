package ru.assume.reactivepostgre.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.test.mapper.TestMapper;
import ru.assume.reactivepostgre.test.mapper.TestParameterMapper;
import ru.assume.reactivepostgre.test.model.TestDomainManagement;
import ru.assume.reactivepostgre.test.persistence.TestParameterEntity;
import ru.assume.reactivepostgre.test.persistence.TestParameterRepository;
import ru.assume.reactivepostgre.test.persistence.TestRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestManager {

    private final TestMapper testMapper;
    private final TestRepository testRepository;
    private final TestParameterMapper testParameterMapper;
    private final TestParameterRepository testParameterRepository;

    public Flux<TestDomainManagement> createTests(List<TestDomainManagement> tests) {
        return Flux.fromIterable(tests)
                .flatMap(test ->
                        testRepository.save(testMapper.apiToEntity(test))
                                .flatMap(savedTest -> {
                                    List<TestParameterEntity> parameters = test.getParameters().stream()
                                            .map(param -> testParameterMapper.apiToEntity(param, savedTest.getId()))
                                            .toList();

                                    log.info("mapped {} parameters", parameters.size());

                                    return testParameterRepository.saveAll(parameters)
                                            .collectList()
                                            .map(savedParams -> {
                                                test.setId(savedTest.getId());
                                                test.setParameters(savedParams.stream()
                                                        .map(testParameterMapper::entityToApi)
                                                        .toList());
                                                return test;
                                            });
                                })
                );
    }
}
