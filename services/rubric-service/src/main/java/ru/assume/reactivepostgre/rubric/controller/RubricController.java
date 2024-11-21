package ru.assume.reactivepostgre.rubric.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.rubric.model.RubricDomainManagement;
import ru.assume.reactivepostgre.rubric.model.RubricDomainShort;
import ru.assume.reactivepostgre.rubric.service.RubricManager;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RubricController {

    private final RubricManager manager;

    @GetMapping("/rubricsDomain")
    public Flux<RubricDomainShort> getRubricsDomain(@RequestParam(value = "userId") String userId) {
        log.info("getRubricsDomain");
        return manager.getRubricsDomain(userId);
    }

    @PostMapping("/rubricsAdd")
    public Flux<RubricDomainManagement> createRubrics(@RequestBody List<RubricDomainManagement> rubrics) {
        log.info("persisting new rubrics");
        return manager.createRubrics(rubrics);
    }
}


