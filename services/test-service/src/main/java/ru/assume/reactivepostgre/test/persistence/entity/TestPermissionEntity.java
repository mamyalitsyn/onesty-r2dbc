package ru.assume.reactivepostgre.test.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;
import ru.assume.reactivepostgre.test.model.RoleType;
import ru.assume.reactivepostgre.test.model.SubscriptionType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table("test_permission")
public class TestPermissionEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private RoleType role;
    private List<SubscriptionType> subscriptions;
    private String testId;
}
