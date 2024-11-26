package ru.assume.reactivepostgre.category.persistence;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.category.model.CategoryDomainShort;
import ru.assume.reactivepostgre.category.model.RoleSubscriptionPermission;
import ru.assume.reactivepostgre.category.model.RoleType;
import ru.assume.reactivepostgre.category.model.SubscriptionType;

import java.util.*;

import static java.util.Collections.emptyList;

@Repository
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private final DatabaseClient databaseClient;

    public CategoryRepositoryCustomImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Flux<CategoryDomainShort> findCategoriesWithPermissions() {
        String sql = """
            SELECT 
                c.id AS category_id,
                c.name AS category_name,
                c.image_url AS category_image_url,
                c.order_number AS category_order_number,
                cp.role AS permission_role,
                cp.subscriptions AS permission_subscriptions
            FROM category c
            LEFT JOIN category_permission cp 
                ON c.id = cp.category_id
            """;

        return databaseClient.sql(sql)
                .map(row -> {
                    String categoryId = row.get("category_id", String.class);
                    String categoryName = row.get("category_name", String.class);
                    String imageUrl = row.get("category_image_url", String.class);
                    Integer order = row.get("category_order_number", Integer.class);
                    RoleType role = RoleType.valueOf(row.get("permission_role", String.class));
                    String[] subscriptionsArray = row.get("permission_subscriptions", String[].class);
                    List<SubscriptionType> subscriptions = subscriptionsArray != null
                            ? Arrays.stream(subscriptionsArray)
                            .map(SubscriptionType::valueOf)
                            .toList()
                            : List.of();

                    RoleSubscriptionPermission permission = new RoleSubscriptionPermission(role, Optional.ofNullable(subscriptions)
                            .map(HashSet::new)
                            .orElseGet(HashSet::new));

                    return new CategoryDomainShort(categoryId, categoryName, imageUrl, order, Set.of(permission));
                })
                .all();
    }
}