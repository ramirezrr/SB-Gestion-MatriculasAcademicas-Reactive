package com.mitocode.matriculas.service;

import com.mitocode.matriculas.model.SecurityUser;
import com.mitocode.matriculas.security.ISecurityUser;
import reactor.core.publisher.Mono;

public interface SecurityUserService extends CRUD<SecurityUser, String>{

    Mono<ISecurityUser> searchByUser(String username);
}
