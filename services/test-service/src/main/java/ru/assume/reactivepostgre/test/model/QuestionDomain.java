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
public class QuestionDomain {

    private String id;
    private String parameterId; //tests.parameters.[]
    private String text;
    private List<Answer> answers;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Answer {
        private String id;
        private Integer score;//tests.parameters.[]
        private String text;
        private List<Parameter> parameters;//CategoryParameters/rubric
    }

    //CategoryParameters
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Parameter {
        private String id;
        private Double score;
    }
}
