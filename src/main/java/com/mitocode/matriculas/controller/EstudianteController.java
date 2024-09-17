package com.mitocode.matriculas.controller;

import com.mitocode.matriculas.dto.EstudianteDTO;
import com.mitocode.matriculas.model.Estudiante;
import com.mitocode.matriculas.paginacion.PageSupport;
import com.mitocode.matriculas.service.EstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    @Qualifier("estudiantesMapper")
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
    public Mono<ResponseEntity<EstudianteDTO>> guardar(@RequestBody @Valid EstudianteDTO estudiante, final ServerHttpRequest req) {
        log.info("Inicia proceso de guardar, body: \n {}", estudiante.toString());
        return estudianteService.guardar(this.convertToEstudiante(estudiante))
                .map(this::convertToEstudianteDTO)
                .map(e -> ResponseEntity.created(
                                URI.create(req.getURI().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> actualizar(@PathVariable("id") String id, @RequestBody @Valid EstudianteDTO estudiante) {
        return Mono.just(estudiante)
                .map(this::convertToEstudiante)
                .map(e -> {
                    e.setIdEstudiante(id);
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

    @GetMapping("/pageable")
    public Mono<ResponseEntity<PageSupport<EstudianteDTO>>> getPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size
    ){
        return estudianteService.getPage(PageRequest.of(page, size))
                .map(pageSupport -> new PageSupport<>(
                        pageSupport.getContent().stream().map(this::convertToEstudianteDTO).toList(),
                        pageSupport.getPageNumber(),
                        pageSupport.getPageSize(),
                        pageSupport.getTotalElements()
                ))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @GetMapping("/listarPorEdadDesc")
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> ordenarEstudianteEdadDescendente() {
        Flux<EstudianteDTO> fx = estudianteService.ordenarEstudianteEdadDescendente().map(this::convertToEstudianteDTO); //e -> convertToDto(e)

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fx)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/listarPorEdadAsc")
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> ordenarEstudianteEdadAscendente() {
        Flux<EstudianteDTO> fx = estudianteService.ordenarEstudianteEdadAscendente().map(this::convertToEstudianteDTO); //e -> convertToDto(e)

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fx)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //    implement Mapper
    public EstudianteDTO convertToEstudianteDTO(Estudiante estudiante) {
        return modelMapper.map(estudiante, EstudianteDTO.class);
    }

    public Estudiante convertToEstudiante(EstudianteDTO curso) {
        return modelMapper.map(curso, Estudiante.class);
    }
}
