package ru.assume.reactivepostgre.parameter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.parameter.mapper.ParameterMapper;
import ru.assume.reactivepostgre.parameter.model.ParameterDomain;
import ru.assume.reactivepostgre.parameter.model.ParameterDomainManagement;
import ru.assume.reactivepostgre.parameter.persistence.ParameterEntity;
import ru.assume.reactivepostgre.parameter.persistence.ParameterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParameterManager {

    private final ParameterMapper mapper;
    private final ParameterRepository repository;

    public Flux<ParameterDomain> getParametersDomain(String userId) {
        return repository.findAll()
                .map(parameter -> new ParameterDomain(
                        parameter.getId(),
                        parameter.getName(),
                        parameter.getAbout(),
                        parameter.getOrderNumber(),
                        parameter.getCategoryId(),
                        parameter.getRubricId()
                ))
                .doOnNext(parameterDomain -> log.info("found parameter: {}", parameterDomain))
                .doOnComplete(() -> log.info("All parameters processed"));
    }

    public Flux<ParameterDomainManagement> createParameters(List<ParameterDomainManagement> parameters) {
        return Flux.fromIterable(parameters)
                .flatMap(parameter -> repository.save(mapper.parameterDomainManagementToEntity(parameter))
                        .map(mapper::entityToParameterDomainManagement));
    }

}
