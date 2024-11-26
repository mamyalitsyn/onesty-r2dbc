package ru.assume.reactivepostgre.user_test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.user_test.gateway.TestServiceGateway;
import ru.assume.reactivepostgre.user_test.mapper.TestMapper;
import ru.assume.reactivepostgre.user_test.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.logging.Level.FINE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTestManager {

    private final TestMapper testMapper;
    private final TestServiceGateway testServiceGateway;

    public Mono<Domain> getTestsDomainByUserId(String userId) {
        log.info("Getting domain for user {}", userId);
        return Mono.zip(
                        values -> createDomain(
                                (List<TestDomainShort>) values[0],
                                (List<RubricDomainShort>) values[1],
                                (List<CategoryDomainShort>) values[2],
                                (List<ParameterDomain>) values[3]),
                        testServiceGateway.getDomain(TestDomainShort.class, "http://test-service:8080/testsDomain?userId={userId}", userId).collectList(),
                        testServiceGateway.getDomain(RubricDomainShort.class, "http://rubric-service:8080/rubricsDomain?userId={userId}", userId).collectList(),
                        testServiceGateway.getDomain(CategoryDomainShort.class, "http://category-service:8080/categoriesDomain?userId={userId}", userId).collectList(),
                        testServiceGateway.getDomain(ParameterDomain.class, "http://parameter-service:8080/parametersDomain?userId={userId}", userId).collectList())
                .doOnError(ex -> log.warn("Getting domain failed: {}", ex.toString()))
                .log(log.getName(), FINE);
    }

    private Domain createDomain(List<TestDomainShort> tests,
                                List<RubricDomainShort> rubrics,
                                List<CategoryDomainShort> categories,
                                List<ParameterDomain> parameters) {
        Domain result = new Domain();
        List<CategoryDomain> categoryDomains = new ArrayList<>();
        categories.forEach(category -> {
            String categoryId = category.getId();
            CategoryDomain categoryDomain = new CategoryDomain(categoryId,
                    category.getName(),
                    category.getImageUrl(),
                    category.getOrder(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    category.getSearchPermissions());

            var categoryParameters = parameters
                    .stream()
                    .filter(parameter -> parameter.getCategoryId() != null && parameter.getCategoryId().equals(categoryId))
                    .map(parameter -> new Parameter(parameter.getId(),
                            parameter.getName(),
                            parameter.getAbout(),
                            parameter.getOrder()
                    ))
                    .toList();
            categoryDomain.setParameters(categoryParameters);

            var categoryRubrics = rubrics
                    .stream()
                    .filter(rubric -> rubric.getCategoryId().equals(categoryId))
                    .map(rubric -> {
                        String rubricId = rubric.getId();

                        var rubricParameters = parameters
                                .stream()
                                .filter(parameter -> parameter.getRubricId() != null && parameter.getRubricId().equals(rubricId))
                                .map(parameter -> new Parameter(parameter.getId(),
                                        parameter.getName(),
                                        parameter.getAbout(),
                                        parameter.getOrder()
                                ))
                                .toList();

                        return new RubricDomain(
                                rubricId,
                                rubric.getName(),
                                rubric.getImageUrl(),
                                rubric.getOrder(),
                                rubricParameters,
                                tests.stream().filter(test -> test.getRubricId().equals(rubricId)).map(testMapper::domainShortToDomain).toList()
                        );
                    })
                    .toList();

            categoryDomain.setRubrics(categoryRubrics);
            categoryDomains.add(categoryDomain);
        });
        result.setCategories(categoryDomains);
        return result;
    }
}
