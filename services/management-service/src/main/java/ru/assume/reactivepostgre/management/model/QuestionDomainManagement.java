package ru.assume.reactivepostgre.management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class QuestionDomainManagement {

    private String parameterName;
    private String text;
}
