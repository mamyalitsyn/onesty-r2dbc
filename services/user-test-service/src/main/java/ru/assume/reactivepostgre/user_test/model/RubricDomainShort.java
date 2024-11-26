package ru.assume.reactivepostgre.user_test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RubricDomainShort {

    private String id;
    private String name;
    private String imageUrl;
    private String categoryId;
    private Integer order;
}