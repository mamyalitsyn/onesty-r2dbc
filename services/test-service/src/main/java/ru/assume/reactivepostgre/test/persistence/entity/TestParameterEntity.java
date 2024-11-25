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
@Table("test_parameter")
public class TestParameterEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private String name;
    private String about;
    private String testId;
}
