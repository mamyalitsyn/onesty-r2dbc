package ru.assume.reactivepostgre.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TestDomainShort {

    private String id;
    private String name;
    private String rubricId;
    private Integer order;
    private List<TestParameter> parameters;
    private List<TestCard> cards;
    private List<QuestionDomain> questions;
    private Set<RoleSubscriptionPermission> permissions;
}
