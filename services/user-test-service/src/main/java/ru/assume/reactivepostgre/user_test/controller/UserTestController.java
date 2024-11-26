package ru.assume.reactivepostgre.user_test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.assume.reactivepostgre.user_test.model.Domain;
import ru.assume.reactivepostgre.user_test.service.UserTestManager;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserTestController {

    private final UserTestManager manager;

    @GetMapping("/{userId}/testsdomain")
    Mono<Domain> getTestsDomainByUserId(@PathVariable String userId) {
        return manager.getTestsDomainByUserId(userId);
    }
}
