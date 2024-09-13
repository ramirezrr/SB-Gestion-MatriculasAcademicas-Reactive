package com.mitocode.matriculas.service;

import com.mitocode.matriculas.model.Estudiante;
import com.mitocode.matriculas.repository.EstudianteRepository;
import com.mitocode.matriculas.repository.GenericoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl extends CRUDImpl<Estudiante, String> implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Override
    protected GenericoRepository<Estudiante, String> getRepo() {
        return estudianteRepository;
    }

    @Override
    public Flux<Estudiante> ordenarEstudianteEdadDescendente() {
        return estudianteRepository.findAllByOrderByEdadEstudianteDesc();
    }

    @Override
    public Flux<Estudiante> ordenarEstudianteEdadAscendente() {
        return estudianteRepository.findAllByOrderByEdadEstudianteAsc();
    }
}
