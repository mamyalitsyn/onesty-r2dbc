package ru.assume.reactivepostgre.parameter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.parameter.model.ParameterDomain;
import ru.assume.reactivepostgre.parameter.model.ParameterDomainManagement;
import ru.assume.reactivepostgre.parameter.service.ParameterManager;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ParameterController {

    private final ParameterManager manager;

    @GetMapping("/parametersDomain")
    public Flux<ParameterDomain> getParametersDomain(@RequestParam(value = "userId", required = false) String userId) {
        log.info("got new domain request");
        return manager.getParametersDomain(userId);
    }

    @PostMapping("/parametersAdd")
    public Flux<ParameterDomainManagement> createParameters(@RequestBody List<ParameterDomainManagement> parameters) {
        log.info("persisting new parameters");
        return manager.createParameters(parameters);
    }
}
