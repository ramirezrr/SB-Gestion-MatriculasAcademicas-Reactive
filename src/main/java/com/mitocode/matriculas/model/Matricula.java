package com.mitocode.matriculas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Document(collection = "Matricula")
public class Matricula {

    private String fechaMatricula;
    private Estudiante estudiante;
    private Curso curso;
    private Boolean estado;


}
