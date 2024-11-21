package ru.assume.reactivepostgre.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagementController {

    @GetMapping("/pook")
    public String pook() {
        return "pook";
    }

}
