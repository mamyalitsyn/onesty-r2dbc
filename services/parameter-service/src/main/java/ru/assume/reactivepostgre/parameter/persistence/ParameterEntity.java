package ru.assume.reactivepostgre.parameter.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Table("parameter")
public class ParameterEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private String name;
    private String about;
    private Integer orderNumber;
    private String categoryId;
    private String rubricId;
}
