package com.mitocode.matriculas.filter;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class ExampleFilter implements WebFilter {

    private static final String X_REQUEST_ID = "X-Request-ID";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getResponse().getHeaders().add("user", "mitocode");

        // Obtener el valor de la cabecera X-Request-ID
        String xRequestId = exchange.getRequest().getHeaders().getFirst(X_REQUEST_ID);

        if (xRequestId == null || xRequestId.isEmpty()) {
            xRequestId = UUID.randomUUID().toString();  // Generar un UUID
            log.debug("No X-Request-ID found in header, generated new UUID: {}", xRequestId);
        }

        // Guardar el X-Request-ID en el MDC para loguear el contexto
        MDC.put(X_REQUEST_ID, xRequestId);

        // Agregar el X-Request-ID a la respuesta
        exchange.getResponse().getHeaders().add(X_REQUEST_ID, xRequestId);

        // Loguear la ruta y los parámetros de consulta
        String path = exchange.getRequest().getURI().getPath();
        String queryString = exchange.getRequest().getURI().getQuery();
        log.info("Path: {}, QueryString: {}", path, queryString);

        // Procesar la solicitud y luego remover el X-Request-ID del MDC
        return chain.filter(exchange)
                .doFinally(signalType -> MDC.remove(X_REQUEST_ID));

    }
}
