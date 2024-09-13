package com.mitocode.matriculas.repository;

import com.mitocode.matriculas.model.Estudiante;
import reactor.core.publisher.Flux;

public interface EstudianteRepository extends GenericoRepository<Estudiante, String> {

    Flux<Estudiante> findAllByOrderByEdadEstudianteDesc();
    Flux<Estudiante> findAllByOrderByEdadEstudianteAsc();
}
