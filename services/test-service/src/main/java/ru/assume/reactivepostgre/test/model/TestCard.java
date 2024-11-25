package ru.assume.reactivepostgre.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TestCard {

    private String id;
    private String back;
    private String front;
    private String gimmy;
    private String imageUrl;
    private Integer maxValue;
    private Integer minValue;
    private String cardName;
    private String opportunities;
    private String strengths;
}
