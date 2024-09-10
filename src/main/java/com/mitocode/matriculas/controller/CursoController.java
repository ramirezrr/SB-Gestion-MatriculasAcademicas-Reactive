package com.mitocode.matriculas.controller;

import com.mitocode.matriculas.dto.CursoDTO;
import com.mitocode.matriculas.model.Curso;
import com.mitocode.matriculas.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("cursos")
public class CursoController {

    private final CursoService cursoService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<CursoDTO>>> listar() {
        Flux<CursoDTO> listaCurso = cursoService.listarTodo()
                .map(this::convertToCursoDTO);

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(listaCurso))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> listarPorId(@PathVariable("id") String id) {
        return cursoService.listarPorId(id)
                .map(this::convertToCursoDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)).
                defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Mono<ResponseEntity<CursoDTO>> guardar(@RequestBody @Valid CursoDTO curso, final ServerHttpRequest req) {
        return cursoService.guardar(this.convertToCurso(curso))
                .map(this::convertToCursoDTO)
                .map(e -> ResponseEntity.created(
                                URI.create(req.getURI().toString().concat("/").concat(e.getIdCurso()))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> actualizar(@PathVariable("id") String id, @RequestBody CursoDTO curso) {
        return Mono.just(this.convertToCurso(curso))
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> cursoService.actualizar(id, e))
                .map(this::convertToCursoDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {
        return cursoService.eliminar(id)
                .flatMap(result -> {
                    if (result) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.noContent().build());
                    }
                });
    }

    public CursoDTO convertToCursoDTO(Curso curso) {
        return modelMapper.map(curso, CursoDTO.class);
    }

    public Curso convertToCurso(CursoDTO curso) {
        return modelMapper.map(curso, Curso.class);
    }
}
