package com.mitocode.matriculas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoDTO {


    private String idCurso;
    private String nombreCurso;
    private String siglasCurso;
    private Boolean estadoCurso;


}
