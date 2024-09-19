package com.mitocode.matriculas.repository;

import com.mitocode.matriculas.model.SecurityUser;
import reactor.core.publisher.Mono;

public interface SecurityUserRepo extends GenericoRepository<SecurityUser, String>{

    //@Query("{username: ?1}")
    Mono<SecurityUser> findOneByUsername(String username);
}
