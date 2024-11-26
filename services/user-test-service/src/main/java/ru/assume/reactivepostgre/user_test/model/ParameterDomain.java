package ru.assume.reactivepostgre.user_test.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class ParameterDomain {

    private String id;
    private String name;
    private String about;
    private Integer order;
    private String categoryId;
    private String rubricId;
}
