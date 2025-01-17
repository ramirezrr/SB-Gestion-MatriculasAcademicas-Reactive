package com.mitocode.matriculas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoDTO {


    private String id;

    @NotNull(message = "El campo nombreCurso es obligatorio")
    @Size(min = 2, message = "El campo nombreCurso acepta mínimo 2 letras.")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ]{2,}( [A-Za-zñÑáéíóúÁÉÍÓÚ]{2,})*$", message = "El campo nombreCurso solo acepta letras.")
    private String nombre;

    @NotNull(message = "El campo siglasCurso es obligatorio")
    @Size(min = 1, message = "El campo siglasCurso acepta mínimo 1 letra.")
    @Pattern(regexp = "^[\\p{L}ñÑáéíóúÁÉÍÓÚ]+$", message = "El campo siglasCurso solo acepta letras.")
    private String siglas;

    private Boolean estado;


}
