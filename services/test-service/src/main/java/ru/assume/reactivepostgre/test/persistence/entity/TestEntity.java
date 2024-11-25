package ru.assume.reactivepostgre.test.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("test")
public class TestEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private String name;
    private String rubricId;
    private Integer orderNumber;
}
