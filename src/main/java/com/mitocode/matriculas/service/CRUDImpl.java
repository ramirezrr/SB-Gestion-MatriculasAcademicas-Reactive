package com.mitocode.matriculas.service;

import com.mitocode.matriculas.paginacion.PageSupport;
import com.mitocode.matriculas.repository.GenericoRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CRUDImpl<T, ID> implements CRUD<T, ID> {

    protected abstract GenericoRepository<T, ID> getRepo();

    @Override
    public Mono<T> guardar(T t) {
        return getRepo().save(t);
    }

    @Override
    public Mono<T> actualizar(ID id, T t) {
        //LUEGO SE AGREGARA VALIDACION DE ID
        return getRepo().findById(id).flatMap(e -> getRepo().save(t));
    }

    @Override
    public Flux<T> listarTodo() {
        return getRepo().findAll();
    }

    @Override
    public Mono<T> listarPorId(ID id) {
        return getRepo().findById(id);
    }

    @Override
    public Mono<Boolean> eliminar(ID id) {
        //AGREGAR VALIDACION SI ES TRUE O FALSE
        return getRepo().findById(id)
                .hasElement()
                .flatMap(resultado -> {
                    if (resultado) {
                        return getRepo().deleteById(id).thenReturn(true);
                    } else {
                        return Mono.just(false);
                    }
                });
    }

    @Override
    public Mono<PageSupport<T>> getPage(Pageable pageable) {
        return getRepo().findAll()
                .collectList()
                .map(list -> new PageSupport<>(
                        list.stream()
                                .skip(pageable.getPageNumber() * pageable.getPageSize())
                                .limit(pageable.getPageSize()).toList(),
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        list.size()
                ));
    }


}
