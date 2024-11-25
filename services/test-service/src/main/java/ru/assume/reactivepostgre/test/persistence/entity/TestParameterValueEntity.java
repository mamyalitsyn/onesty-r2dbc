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
@Table("test_parameter_value")
public class TestParameterValueEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private String text;
    private Integer maxValue;
    private Integer minValue;
    private String testParameterId;
}

