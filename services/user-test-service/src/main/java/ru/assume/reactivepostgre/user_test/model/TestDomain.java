package ru.assume.reactivepostgre.user_test.model;

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
public class TestDomain {

    private String id;
    private String name;
    private Integer order;
    private List<TestParameter> parameters; //test parameters
    private List<TestCard> cards;
    private List<QuestionDomain> questions;
    private Set<RoleSubscriptionPermission> permissions;

}