package com.mitocode.matriculas.service;

import com.mitocode.matriculas.model.Estudiante;
import reactor.core.publisher.Flux;


public interface EstudianteService extends CRUD<Estudiante, String> {

    Flux<Estudiante> ordenarEstudianteEdadDescendente();
    Flux<Estudiante> ordenarEstudianteEdadAscendente();

}
