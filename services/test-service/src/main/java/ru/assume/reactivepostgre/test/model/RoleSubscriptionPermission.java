package ru.assume.reactivepostgre.test.model;

import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleSubscriptionPermission {

    @EqualsAndHashCode.Include
    private RoleType role;
    private Set<SubscriptionType> subscriptions;
}
