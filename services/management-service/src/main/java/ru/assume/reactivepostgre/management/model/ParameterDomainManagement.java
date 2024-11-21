package ru.assume.reactivepostgre.management.model;

import lombok.*;

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
    private String categoryName;
    private String rubricName;
}
