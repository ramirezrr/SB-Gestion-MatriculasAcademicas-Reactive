package com.mitocode.matriculas.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Component
@Order(-1)
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {


    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest req) {
        Map<String, Object> generalError = getErrorAttributes(req, ErrorAttributeOptions.defaults());
        Integer statusCode = (Integer) generalError.get("status");

        HttpStatus httpStatus;

        Throwable error = getError(req);
        ApiResponse apiResponse;

        // Captura de validaciones con @Valid
        if (error instanceof WebExchangeBindException validationException) {
//            if (error instanceof WebExchangeBindException) {
//            WebExchangeBindException validationException = (WebExchangeBindException) error;

                // Extraer los mensajes de validación
            List<String> validationMessages = validationException.getBindingResult().getAllErrors().stream().map(objectError -> {
                if (objectError instanceof FieldError fieldError) {
                    return fieldError.getDefaultMessage(); // Solo el mensaje, sin incluir el campo
                }
                return objectError.getDefaultMessage();
            }).toList();
            httpStatus = HttpStatus.BAD_REQUEST;
            apiResponse = new ApiResponse(httpStatus.value(), String.join(";", validationMessages));
        } else if (error instanceof FunctionalException customException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            apiResponse = new ApiResponse(httpStatus.value(), customException.getMessage());
        } else {
            httpStatus = HttpStatus.resolve(statusCode) != null ? HttpStatus.resolve(statusCode) : HttpStatus.INTERNAL_SERVER_ERROR;
            apiResponse = new ApiResponse(statusCode, error.getMessage());
        }

        return ServerResponse.status(Objects.requireNonNull(httpStatus)).
                contentType(MediaType.APPLICATION_JSON).
                body(BodyInserters.fromValue(apiResponse));
    }
}
