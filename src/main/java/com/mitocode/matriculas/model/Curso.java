package com.mitocode.matriculas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Curso")
public class Curso {

    @Id
    private String idCurso;
    @Field
    private String nameCurso;
    @Field
    private String siglasCurso;
    @Field
    private Boolean estadoCurso;


}
