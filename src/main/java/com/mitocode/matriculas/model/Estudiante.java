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
@Document(collection = "Estudiante")
public class Estudiante {

    @Id
    private String idEstudiante;
    @Field
    private String nombreEstudiante;
    @Field
    private String apellidoEstudiante;
    @Field
    private Integer dniEstudiante;
    @Field
    private Short edadEstudiante;


}
