package ru.assume.reactivepostgre.user_test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RubricDomain {
    private String id;
    private String name;
    private String imageUrl;
    private Integer order;
    List<Parameter> parameters;
    private List<TestDomain> tests;
}
