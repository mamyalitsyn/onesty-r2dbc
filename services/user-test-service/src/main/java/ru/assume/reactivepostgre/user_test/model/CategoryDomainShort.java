package ru.assume.reactivepostgre.user_test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CategoryDomainShort {
    private String id;
    private String name;
    private String imageUrl;
    private Integer order;
    private Set<RoleSubscriptionPermission> searchPermissions;
}