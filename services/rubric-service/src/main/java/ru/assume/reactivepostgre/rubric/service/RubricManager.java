package ru.assume.reactivepostgre.rubric.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.rubric.mapper.RubricMapper;
import ru.assume.reactivepostgre.rubric.model.RubricDomainManagement;
import ru.assume.reactivepostgre.rubric.model.RubricDomainShort;
import ru.assume.reactivepostgre.rubric.persistence.RubricRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RubricManager {

    private final RubricMapper mapper;
    private final RubricRepository repository;

    public Flux<RubricDomainShort> getRubricsDomain(String userId) {
        return null;
    }

    public Flux<RubricDomainManagement> createRubrics(List<RubricDomainManagement> rubrics) {
        return Flux.fromIterable(rubrics)
                .map(rubric -> {
                    rubric.setId(UUID.randomUUID().toString());
                    return mapper.rubricDomainManagementToEntity(rubric);
                })
                .flatMap(repository::save)
                .zipWith(Flux.fromIterable(rubrics), (savedEntity, originalRubric) -> {
                    RubricDomainManagement result = mapper.entityToRubricDomainManagement(savedEntity);
                    result.setTempId(originalRubric.getTempId());
                    return result;
                });
    }
}
