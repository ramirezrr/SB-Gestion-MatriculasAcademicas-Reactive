package com.mitocode.matriculas.controller;

import com.mitocode.matriculas.dto.EstudianteDTO;
import com.mitocode.matriculas.model.Estudiante;
import com.mitocode.matriculas.service.EstudianteService;
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
@RequestMapping("estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> listar() {
        Flux<EstudianteDTO> listaEstudiante = estudianteService.listarTodo()
                .map(this::convertToEstudianteDTO);
        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(listaEstudiante))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> listarPorId(@PathVariable("id") String id) {
        return estudianteService.listarPorId(id)
                .map(this::convertToEstudianteDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Mono<ResponseEntity<EstudianteDTO>> guardar(@RequestBody EstudianteDTO estudiante, final ServerHttpRequest req) {
        return estudianteService.guardar(this.convertToEstudiante(estudiante))
                .map(this::convertToEstudianteDTO)
                .map(e -> ResponseEntity.created(
                                URI.create(req.getURI().toString().concat("/").concat(e.getIdEstudiante())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> actualizar(@PathVariable("id") String id, @RequestBody EstudianteDTO estudiante) {
        return Mono.just(estudiante)
                .map(this::convertToEstudiante)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> estudianteService.actualizar(id, e))
                .map(this::convertToEstudianteDTO)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {
        return estudianteService.eliminar(id)
                .flatMap(result -> {
                    if (result) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }


    public EstudianteDTO convertToEstudianteDTO(Estudiante estudiante) {
        return modelMapper.map(estudiante, EstudianteDTO.class);
    }

    public Estudiante convertToEstudiante(EstudianteDTO curso) {
        return modelMapper.map(curso, Estudiante.class);
    }
}
