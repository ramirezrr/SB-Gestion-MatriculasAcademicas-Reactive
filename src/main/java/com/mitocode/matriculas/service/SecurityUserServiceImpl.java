package com.mitocode.matriculas.service;

import com.mitocode.matriculas.model.SecurityRole;
import com.mitocode.matriculas.model.SecurityUser;
import com.mitocode.matriculas.repository.GenericoRepository;
import com.mitocode.matriculas.repository.SecurityRoleRepo;
import com.mitocode.matriculas.repository.SecurityUserRepo;
import com.mitocode.matriculas.security.ISecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl extends CRUDImpl<SecurityUser, String> implements SecurityUserService {

    private final SecurityUserRepo userRepo;
    private final SecurityRoleRepo roleRepo;

    @Override
    protected GenericoRepository<SecurityUser, String> getRepo() {
        return userRepo;
    }

    @Override
    public Mono<ISecurityUser> searchByUser(String username) {
        return userRepo.findOneByUsername(username)
                .flatMap(user -> Flux.fromIterable(user.getSecurityRoles())
                        .flatMap(userRole -> roleRepo.findById(userRole.getId())
                                .map(SecurityRole::getName))
                        .collectList()
                        .map(roles -> new com.mitocode.matriculas.security.ISecurityUser
                                (user.getUsername(), user.getPassword(), user.isStatus(), roles))
                );
    }


}
