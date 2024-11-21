package ru.assume.reactivepostgre.user_test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserTestController {

    @GetMapping("/kek")
    public String kek() {
        return "kek";
    }
}
