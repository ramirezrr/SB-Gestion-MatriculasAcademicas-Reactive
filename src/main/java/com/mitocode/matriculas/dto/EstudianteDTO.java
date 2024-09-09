package com.mitocode.matriculas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {

    private String idEstudiante;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String dniEstudiante;
    private String edadEstudiante;


}
