package ru.assume.reactivepostgre.category.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CategoryPermission {

    private String id;
    private RoleType role;
    private Set<SubscriptionType> subscriptions;
}
