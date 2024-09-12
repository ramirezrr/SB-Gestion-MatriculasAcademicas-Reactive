package com.mitocode.matriculas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitocode.matriculas.model.Curso;
import com.mitocode.matriculas.model.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatriculaDTO {

    private String id;
    private String fecha;
    private EstudianteDTO estudiante;
    private List<CursoDTO> cursos;
    private Boolean estado;

}
