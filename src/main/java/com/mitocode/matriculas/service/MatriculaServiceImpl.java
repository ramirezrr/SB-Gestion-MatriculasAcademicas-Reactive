package com.mitocode.matriculas.service;

import com.mitocode.matriculas.model.Matricula;
import com.mitocode.matriculas.repository.GenericoRepository;
import com.mitocode.matriculas.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl extends CRUDImpl<Matricula, String> implements MatriculaService {


    private final MatriculaRepository cursoRepository;

    @Override
    protected GenericoRepository<Matricula, String> getRepo() {
        return cursoRepository;
    }
}
