package com.mitocode.matriculas.security;

import java.util.Date;

//Clase S3
public record AuthResponse(
        String token,
        Date expiration
) {
}
