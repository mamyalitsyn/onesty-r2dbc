package ru.assume.reactivepostgre.management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.management.gateway.DomainGateway;
import ru.assume.reactivepostgre.management.model.CategoryDomainManagement;
import ru.assume.reactivepostgre.management.model.RubricDomainManagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagementManager {

    private final ObjectMapper mapper;
    private final DomainGateway gateway;

    public Mono<Void> init() {
        return gateway.createCategories(getObjectsFromResources("categoriesDomainInit", CategoryDomainManagement[].class))
                .collectList()
                .doOnNext(categories -> log.info("{} categories were persisted successfully", categories.size()))
                .flatMap(categories -> {
                    Map<String, String> categoryMap = new HashMap<>();
                    categories.forEach(category -> categoryMap.put(category.getName(), category.getId()));

                    List<RubricDomainManagement> rubrics = getObjectsFromResources("rubricDomainInit", RubricDomainManagement[].class);
                    for (RubricDomainManagement rubric : rubrics) {
                        rubric.setCategoryId(categoryMap.get(rubric.getCategoryName()));
                    }

                    return gateway.createRubrics(rubrics)
                            .collectList()
                            .doOnNext(createdRubrics -> log.info("{} rubrics were persisted successfully", createdRubrics.size()));
                })
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
