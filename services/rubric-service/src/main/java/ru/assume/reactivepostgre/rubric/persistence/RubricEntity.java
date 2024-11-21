package ru.assume.reactivepostgre.rubric.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("rubric")
public class RubricEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private String name;
    private String imageUrl;
    private Integer orderNumber;
    private String categoryId;
}
