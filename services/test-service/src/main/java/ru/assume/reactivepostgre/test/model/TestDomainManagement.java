package ru.assume.reactivepostgre.test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TestDomainManagement {

    private String id;
    private String tempId;
    private String name;
    private String categoryId;
    private String rubricId;
    private Integer order;
}
