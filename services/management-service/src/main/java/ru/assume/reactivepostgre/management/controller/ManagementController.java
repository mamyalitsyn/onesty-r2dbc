package ru.assume.reactivepostgre.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.management.model.QuestionDomainManagement;
import ru.assume.reactivepostgre.management.service.ManagementManager;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementManager manager;

    @PostMapping("/management/init")
    Mono<Void> initDb() {
        return manager.init();
    }

    @PostMapping("/management/parameter")
    Mono<Void> addParameterForSearch(@RequestBody Map<String, List<QuestionDomainManagement.Parameter>> parameters) {
        return manager.addParameterForSearch(parameters);
    }

}
