package com.mitocode.matriculas.handler;


import com.mitocode.matriculas.dto.CursoDTO;
import com.mitocode.matriculas.model.Curso;
import com.mitocode.matriculas.service.CursoService;
import com.mitocode.matriculas.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class CursoHandler {

    private final CursoService service;

    @Qualifier("cursosMapper")
    private final ModelMapper modelMapper;

    //private final Validator validator;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.listarTodo().map(this::convertToDto), CursoDTO.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");

        return service.listarPorId(id)
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<CursoDTO> monoCursoDTO = request.bodyToMono(CursoDTO.class);
        return monoCursoDTO
                .flatMap(requestValidator::validate)
                .flatMap(e -> service.guardar(convertToDocument(e)))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .created(URI.create(request.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(e)
//                        .body(fromValue(monoCursoDTO))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(CursoDTO.class)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(requestValidator::validate)
                .flatMap(e -> service.actualizar(id, convertToDocument(e)))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");

        return service.eliminar(id)
                .flatMap(result -> {
                    if (result) {
                        return ServerResponse.noContent().build();
                    } else {
                        return ServerResponse.notFound().build();
                    }
                });
    }

    private CursoDTO convertToDto(Curso model) {
        return modelMapper.map(model, CursoDTO.class);
    }

    private Curso convertToDocument(CursoDTO dto) {
        return modelMapper.map(dto, Curso.class);
    }
}
