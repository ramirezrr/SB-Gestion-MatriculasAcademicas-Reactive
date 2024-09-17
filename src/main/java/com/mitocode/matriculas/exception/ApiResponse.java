package com.mitocode.matriculas.exception;

import lombok.Data;

import java.util.Date;


@Data
public class ApiResponse {

    private final Date date = new Date();
    private final Integer status;
    private final String message;

    public ApiResponse(Integer status, String mensaje) {
        this.status = status;
        this.message = mensaje;
    }

}
