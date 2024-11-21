package ru.assume.reactivepostgre.category.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("category")
public class CategoryEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private String name;
    private String imageUrl;
    private Integer orderNumber;
}
