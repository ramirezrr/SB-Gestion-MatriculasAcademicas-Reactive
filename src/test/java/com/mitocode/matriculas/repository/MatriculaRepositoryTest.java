package com.mitocode.matriculas.repository;

import com.mitocode.matriculas.dto.CursoDTO;
import com.mitocode.matriculas.model.Curso;
import com.mitocode.matriculas.model.Estudiante;
import com.mitocode.matriculas.model.Matricula;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;


@DataMongoTest
public class MatriculaRepositoryTest {

    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private CursoRepository cursoRepository;


    @Test
    @Disabled
    public void listarTest() {

        Flux<Matricula> matricula = matriculaRepository.findAll();
        matricula.doOnNext(curso -> {
            System.out.println(curso.toString());
        }).blockLast();

    }

    @Test
    public void delete() {
        matriculaRepository.deleteAll().block();
        cursoRepository.deleteAll().block();
        estudianteRepository.deleteAll().block();
    }

    @Test
    @Disabled
    public void insertTest() {
        // Crear un estudiante
//        Estudiante estudiante = new Estudiante();
//        estudiante.setIdEstudiante("66e257841bdf0a536d54d7e8");
//
//        // Crear cursos
//        Curso curso1 = new Curso();
//        curso1.setIdCurso("66e258466005a86c3451a815");
//        List<Curso> cursos = Arrays.asList( curso1);
//
//        // Crear una matrícula
////        Matricula matricula = new Matricula("2024-09-11", estudiante, cursos, true);
//
//        Matricula matriculaGuardada = matriculaRepository.save(matricula).block();
//        System.out.println(matriculaGuardada.toString());

    }
}
