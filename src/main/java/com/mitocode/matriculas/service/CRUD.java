package com.mitocode.matriculas.service;

import com.mitocode.matriculas.paginacion.PageSupport;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CRUD<T, ID> {

    Mono<T> guardar(T t);

    Mono<T> actualizar(ID id, T t);

    Flux<T> listarTodo();

    Mono<T> listarPorId(ID id);

    Mono<Boolean> eliminar(ID id);

    Mono<PageSupport<T>> getPage(Pageable pageable);

}
