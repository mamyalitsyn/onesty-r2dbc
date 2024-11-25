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
@Table("test_answer_parameter")
public class AnswerParameterEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private String parameterId;
    private String answerId;
    private Double score;
}
