package ru.assume.reactivepostgre.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.test.model.TestDomainManagement;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    @PostMapping("/testsAdd")
    Flux<TestDomainManagement> createTests(@RequestBody List<TestDomainManagement> tests) {
        log.info("persisting {} new tests", tests.size());
        return Flux.fromIterable(tests);
    }
}
