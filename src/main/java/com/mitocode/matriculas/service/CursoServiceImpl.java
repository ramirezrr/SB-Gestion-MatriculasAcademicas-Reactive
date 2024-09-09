package com.mitocode.matriculas.service;

import com.mitocode.matriculas.model.Curso;
import com.mitocode.matriculas.repository.CursoRepository;
import com.mitocode.matriculas.repository.GenericoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl extends CRUDImpl<Curso, String> implements CursoService {


    private final CursoRepository cursoRepository;

    @Override
    protected GenericoRepository<Curso, String> getRepo() {
        return cursoRepository;
    }
}
