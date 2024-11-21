package ru.assume.reactivepostgre.category.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;
import ru.assume.reactivepostgre.category.model.RoleType;
import ru.assume.reactivepostgre.category.model.SubscriptionType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table("category_permission")
public class CategoryPermissionEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private RoleType role;
    private List<SubscriptionType> subscriptions;
    private String categoryId;
}
