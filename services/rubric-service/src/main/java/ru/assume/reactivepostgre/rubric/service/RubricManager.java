package ru.assume.reactivepostgre.rubric.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.rubric.mapper.RubricMapper;
import ru.assume.reactivepostgre.rubric.model.RubricDomainManagement;
import ru.assume.reactivepostgre.rubric.model.RubricDomainShort;
import ru.assume.reactivepostgre.rubric.persistence.RubricEntity;
import ru.assume.reactivepostgre.rubric.persistence.RubricRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RubricManager {

    private final RubricMapper mapper;
    private final RubricRepository repository;

    public Flux<RubricDomainShort> getRubricsDomain(String userId) {
        return repository.findAll().map(mapper::entityToRubricDomainShort);
    }

    public Flux<RubricDomainManagement> createRubrics(List<RubricDomainManagement> rubrics) {
        return Flux.fromIterable(rubrics)
                .map(mapper::rubricDomainManagementToEntity)
                .flatMap(repository::save)
                .zipWith(Flux.fromIterable(rubrics), (savedEntity, originalRubric) -> mapper.entityToRubricDomainManagement(savedEntity));
    }
}