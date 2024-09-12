package com.mitocode.matriculas.controller;

import com.mitocode.matriculas.dto.MatriculaDTO;
import com.mitocode.matriculas.model.Matricula;
import com.mitocode.matriculas.service.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    @Qualifier("matriculasMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<MatriculaDTO>>> listar() {
        Flux<MatriculaDTO> listaMatricula = matriculaService.listarTodo()
                .map(this::convertToMatriculaDTO);

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(listaMatricula))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MatriculaDTO>> listarPorId(@PathVariable("id") String id) {
        return matriculaService.listarPorId(id)
                .map(this::convertToMatriculaDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)).
                defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Mono<ResponseEntity<MatriculaDTO>> guardar(@RequestBody @Valid MatriculaDTO matricula, final ServerHttpRequest req) {
                return matriculaService.guardar(this.convertToMatricula(matricula))
                .map(this::convertToMatriculaDTO)
                .map(e -> ResponseEntity.created(
                                URI.create(req.getURI().toString().concat("/").concat(e.getId()))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<MatriculaDTO>> actualizar(@PathVariable("id") String id, @RequestBody MatriculaDTO matricula) {
        return Mono.just(this.convertToMatricula(matricula))
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> matriculaService.actualizar(id, e))
                .map(this::convertToMatriculaDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {
        return matriculaService.eliminar(id)
                .flatMap(result -> {
                    if (result) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.noContent().build());
                    }
                });
    }


//    implement Mapper
    public MatriculaDTO convertToMatriculaDTO(Matricula matricula) {
        return modelMapper.map(matricula, MatriculaDTO.class);
    }

    public Matricula convertToMatricula(MatriculaDTO matricula) {
        return modelMapper.map(matricula, Matricula.class);
    }
}
