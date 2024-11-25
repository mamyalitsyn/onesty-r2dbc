package ru.assume.reactivepostgre.management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonIgnoreProperties
public class TestDomainManagement {

    private String id;
    private String name;
    private String rubricId;
    private String rubricName;
    private Integer order;
    private List<TestParameter> parameters;
    private List<TestCard> cards;
    private List<QuestionDomainManagement> questions;
}
