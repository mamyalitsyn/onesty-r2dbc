package ru.assume.reactivepostgre.test.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("test_card")
public class TestCardEntity {

    @Id
    private String id;
    @Version
    private Long version;
    private String back;
    private String front;
    private String gimmy;
    private String imageUrl;
    private Integer maxValue;
    private Integer minValue;
    private String cardName;
    private String opportunities;
    private String strengths;
    private String testId;
}
