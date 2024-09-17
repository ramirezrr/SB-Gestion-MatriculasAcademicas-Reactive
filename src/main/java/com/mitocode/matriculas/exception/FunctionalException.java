package com.mitocode.matriculas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class FunctionalException extends RuntimeException {

    private final HttpStatus status;


    public FunctionalException(HttpStatus status, String mensaje) {
        super(mensaje);
        this.status = status;
    }



}
