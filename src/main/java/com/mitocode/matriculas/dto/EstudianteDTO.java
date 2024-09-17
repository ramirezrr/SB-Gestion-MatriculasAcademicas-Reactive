package com.mitocode.matriculas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstudianteDTO {

    private String id;

    @NotNull(message = "El campo nombre es obligatorio.")
    @Size(min = 2, message = "El campo nombre acepta mínimo 2 letras.")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ]{2,}( [A-Za-zñÑáéíóúÁÉÍÓÚ]{2,})*$", message = "El campo nombre solo acepta letras.")
    private String nombre;

    @NotNull(message = "El campo apellido es obligatorio")
    @Size(min = 2, message = "El campo apellido acepta mínimo 2 letras.")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ]{2,}( [A-Za-zñÑáéíóúÁÉÍÓÚ]{2,})*$", message = "El campo apellido solo acepta letras.")
    private String apellido;

    @NotNull(message = "El campo dniEstudiante es obligatorio")
    @Min(value = 10000000, message = "El campo dniEstudiante solo acepta valores de 8 dígitos.")
    @Max(value = 99999999, message = "El campo dniEstudiante solo acepta valores de 8 dígitos.")
    private Integer dni;

    @NotNull(message = "El valor edadEstudiante es obligatorio")
    @Range(min = 5, max = 50, message = "El campo edadEstudiante solo acepta valores de 5 a 50.")
    private Short edad;


}
