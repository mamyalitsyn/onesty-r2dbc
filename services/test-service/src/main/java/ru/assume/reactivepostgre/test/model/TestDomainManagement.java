package ru.assume.reactivepostgre.test.model;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TestDomainManagement {

    private String id;
    private String name;
    private String rubricId;
    private String rubricName;
    private Integer order;
    private List<TestParameter> parameters;
    private List<TestCard> cards;
    private List<QuestionDomainManagement> questions;
    private Set<RoleSubscriptionPermission> permissions;
}
