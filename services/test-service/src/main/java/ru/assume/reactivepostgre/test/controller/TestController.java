package ru.assume.reactivepostgre.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.test.model.QuestionDomainManagement;
import ru.assume.reactivepostgre.test.model.TestDomainManagement;
import ru.assume.reactivepostgre.test.model.TestDomainShort;
import ru.assume.reactivepostgre.test.service.TestManager;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestManager manager;

    @PostMapping("/testsAdd")
    Flux<Void> createTests(@RequestBody List<TestDomainManagement> tests) {
        log.info("persisting {} new tests", tests.size());
        return manager.createTests(tests);
    }

    @PostMapping("/updateTestParameters")
    Mono<Void> updateTestParameters(@RequestBody Map<String, List<QuestionDomainManagement.Parameter>> parameters) {
        log.info("Updating test parameters");
        return manager.updateTestParameters(parameters);
    }

    @GetMapping("/testsDomain")
    Flux<TestDomainShort> getTestsDomain(@RequestParam(value = "userId") String userId) {
        log.info("got new request for user id {}", userId);
        return manager.getTestsDomain(userId);
    }
}
