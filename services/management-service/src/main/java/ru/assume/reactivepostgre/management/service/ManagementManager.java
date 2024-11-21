package ru.assume.reactivepostgre.management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.management.gateway.DomainGateway;
import ru.assume.reactivepostgre.management.model.CategoryDomainManagement;
import ru.assume.reactivepostgre.management.model.ParameterDomainManagement;
import ru.assume.reactivepostgre.management.model.RubricDomainManagement;
import ru.assume.reactivepostgre.management.model.TestDomainManagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagementManager {

    private final ObjectMapper mapper;
    private final DomainGateway gateway;

    public Mono<Void> init() {
        return gateway.createEntities(getObjectsFromResources("categoriesDomainInit", CategoryDomainManagement[].class),
                        "http://category-service:8080/categoriesAdd",
                        CategoryDomainManagement.class)
                .collectList()
                .doOnNext(categories -> log.info("{} categories were persisted successfully", categories.size()))
                .flatMap(categories -> {
                    Map<String, String> categoryMap = new HashMap<>();
                    categories.forEach(category -> categoryMap.put(category.getName(), category.getId()));

                    List<RubricDomainManagement> rubrics = getObjectsFromResources("rubricDomainInit", RubricDomainManagement[].class);
                    for (RubricDomainManagement rubric : rubrics) {
                        rubric.setCategoryId(categoryMap.get(rubric.getCategoryName()));
                    }

                    return gateway.createEntities(rubrics,
                                    "http://rubric-service:8080/rubricsAdd",
                                    RubricDomainManagement.class)
                            .collectList()
                            .doOnNext(createdRubrics -> log.info("{} rubrics were persisted successfully", createdRubrics.size()))
                            .flatMap(createdRubrics -> {
                                Map<String, String> rubricMap = new HashMap<>();
                                createdRubrics.forEach(rubric -> rubricMap.put(rubric.getName(), rubric.getId()));

                                List<ParameterDomainManagement> parameters = getObjectsFromResources("parametersDomainInit", ParameterDomainManagement[].class);

                                for (ParameterDomainManagement parameter : parameters) {
                                    if (Objects.nonNull(parameter.getCategoryName())) {
                                        parameter.setCategoryId(categoryMap.get(parameter.getCategoryName()));
                                    }
                                    if (Objects.nonNull(parameter.getRubricName())) {
                                        parameter.setRubricId(rubricMap.get(parameter.getRubricName()));
                                    }
                                }
                                return gateway.createEntities(parameters,
                                                "http://parameter-service:8080/parametersAdd",
                                                ParameterDomainManagement.class)
                                        .collectList()
                                        .doOnNext(createdParameters -> log.info("{} parameters were persisted successfully", createdParameters.size())).then(Mono.defer(() -> {
                                            List<TestDomainManagement> tests = getObjectsFromResources("testDomainInit", TestDomainManagement[].class);
                                            log.info("{} tests were parsed correctly", tests.size());

                                            tests.forEach(test -> test.setRubricId(rubricMap.get(test.getRubricName())));

                                            return gateway.createEntities(tests,
                                                            "http://test-service:8080/testsAdd",
                                                            TestDomainManagement.class)
                                                    .collectList()
                                                    .doOnNext(createdTests -> log.info("{} tests were persisted successfully", createdTests.size()));
                                        }));
                            })
                            .doOnError(e -> log.error("Error during rubric creation", e));
                })
                .doOnError(e -> log.error("Error during category creation", e))
                .then();
    }

    private <T> List<T> getObjectsFromResources(String fileName, Class<T[]> clazz) {
        try {
            InputStream objectsStream = getClass().getResourceAsStream("/" + fileName + ".json");
            T[] objectsArray = mapper.readValue(objectsStream, clazz);
            return asList(objectsArray);
        } catch (IOException e) {
            log.error("Error occurs", e);
        }
        return emptyList();
    }
}
