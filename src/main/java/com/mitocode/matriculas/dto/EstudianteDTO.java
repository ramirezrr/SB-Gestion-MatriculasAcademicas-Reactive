package com.mitocode.matriculas.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {

    private String idEstudiante;

    @NotNull(message = "El campo nombreEstudiante es obligatorio.")
    @Size(min = 2, message = "El campo nombreEstudiante acepta mínimo 2 letras.")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ]{2,}( [A-Za-zñÑáéíóúÁÉÍÓÚ]{2,})*$", message = "error en nombreeeee")
    private String nombreEstudiante;

    @NotNull(message = "El campo apellidoEstudiante es obligatorio")
    @Size(min = 2, message = "El campo nombreEstudiante acepta mínimo 2 letras.")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ]{2,}( [A-Za-zñÑáéíóúÁÉÍÓÚ]{2,})*$", message = "El campo nombreEstudiante solo acepta letras.")
    private String apellidoEstudiante;

    @NotNull(message = "El campo dniEstudiante es obligatorio")
    @Min(value = 18, message = "El campo dniEstudiante solo acepta valores de 8 dígitos.")
    @Max(value = 18, message = "El campo dniEstudiante solo acepta valores de 8 dígitos.")
    private Integer dniEstudiante;

    @NotNull(message = "El valor edadEstudiante es obligatorio")
    @Range(min = 5, max = 50, message = "El campo edadEstudiante solo acepta valores de 5 a 50.")
    private Short edadEstudiante;


}
