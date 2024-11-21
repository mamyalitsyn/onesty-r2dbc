package ru.assume.reactivepostgre.management.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RoleSubscriptionPermission {

    @EqualsAndHashCode.Include
    private RoleType role;
    private Set<SubscriptionType> subscriptions;
}
