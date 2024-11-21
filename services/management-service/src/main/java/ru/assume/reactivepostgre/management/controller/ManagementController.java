package ru.assume.reactivepostgre.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.management.service.ManagementManager;

@RestController
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementManager manager;

    @PostMapping("/management/init")
    Mono<Void> initDb() {
        return manager.init();
    }

}
