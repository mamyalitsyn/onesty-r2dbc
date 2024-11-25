package ru.assume.reactivepostgre.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TestParameter {

    private String id;
    private String name;
    private String about;
    private List<ParameterValue> value;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class ParameterValue {
        private Integer maxValue;
        private Integer minValue;
        private String text;
    }
}
