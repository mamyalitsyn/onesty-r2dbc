package ru.assume.reactivepostgre.management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TestParameter {

    private String id;
    private String name;
    private String about;
}
