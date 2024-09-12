package com.mitocode.matriculas.handler;


import com.mitocode.matriculas.dto.MatriculaDTO;
import com.mitocode.matriculas.model.Matricula;
import com.mitocode.matriculas.service.MatriculaService;
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
public class MatriculaHandler {

    private final MatriculaService service;

    @Qualifier("matriculasMapper")
    private final ModelMapper modelMapper;

    //private final Validator validator;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.listarTodo().map(this::convertToDto), MatriculaDTO.class);
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
        Mono<MatriculaDTO> monoMatriculaDTO = request.bodyToMono(MatriculaDTO.class);
        return monoMatriculaDTO
                .flatMap(requestValidator::validate)
                .flatMap(e -> service.guardar(convertToDocument(e)))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .created(URI.create(request.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(e)
//                        .body(fromValue(monoMatriculaDTO))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(MatriculaDTO.class)
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

    private MatriculaDTO convertToDto(Matricula model) {
        return modelMapper.map(model, MatriculaDTO.class);
    }

    private Matricula convertToDocument(MatriculaDTO dto) {
        return modelMapper.map(dto, Matricula.class);
    }
}
