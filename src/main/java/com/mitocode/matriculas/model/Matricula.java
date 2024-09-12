package com.mitocode.matriculas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Matricula")
public class Matricula {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Field
    private String fecha;
    @Field
    private Estudiante estudiante;
    @Field
    private List<Curso> cursos;
    @Field
    private Boolean estado;

}
