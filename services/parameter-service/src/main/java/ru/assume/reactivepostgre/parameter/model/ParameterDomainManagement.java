package ru.assume.reactivepostgre.parameter.model;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ParameterDomainManagement {

    private String id;
    private String name;
    private String about;
    private Integer order;
    private String categoryId;
    private String rubricId;
}
