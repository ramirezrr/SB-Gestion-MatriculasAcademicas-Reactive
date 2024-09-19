package com.mitocode.matriculas.controller;

import com.mitocode.matriculas.security.AuthRequest;
import com.mitocode.matriculas.security.AuthResponse;
import com.mitocode.matriculas.security.JwtUtil;
import com.mitocode.matriculas.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class LoginSecurityController {

    private final JwtUtil jwtUtil;
    private final SecurityUserService service;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest){
        return service.searchByUser(authRequest.getUsername())
                .map(userDetails -> {
                    if(BCrypt.checkpw(authRequest.getPassword(), userDetails.getPassword())){
                        String token = jwtUtil.generateToken(userDetails);
                        Date expiration = jwtUtil.getExpirationDateFromToken(token);

                        return ResponseEntity.ok(new AuthResponse(token, expiration));
                    }else{
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
