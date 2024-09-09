package com.mitocode.matriculas.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericoRepository<T, ID> extends ReactiveMongoRepository<T, ID> {
}
