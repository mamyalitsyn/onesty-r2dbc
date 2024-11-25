package ru.assume.reactivepostgre.management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class QuestionDomainManagement {

    private String parameterName;
    private String text;
    private List<Answer> answers;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Answer {
        private String id;
        private Integer score;
        private String text;
        private List<Parameter> parameters;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Parameter {
        private String id;
        private Double score;
    }
}
