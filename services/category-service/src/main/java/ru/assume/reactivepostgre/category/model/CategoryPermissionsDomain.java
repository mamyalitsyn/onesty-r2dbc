package ru.assume.reactivepostgre.category.model;

import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CategoryPermissionsDomain {

    private String categoryId;
    private Set<RoleSubscriptionPermission> searchPermissions;
}
