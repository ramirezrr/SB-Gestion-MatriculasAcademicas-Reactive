package com.mitocode.matriculas.config;


import com.mitocode.matriculas.handler.CursoHandler;
import com.mitocode.matriculas.handler.EstudianteHandler;
import com.mitocode.matriculas.handler.MatriculaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    //Functional Endpoints
    @Bean
    public RouterFunction<ServerResponse> routesCurso(CursoHandler handler) { //, ClientHandler clientHandler
        return route(GET("/v2/cursos"), handler::findAll) //req -> handler.findAll(req)
                .andRoute(GET("/v2/cursos/{id}"), handler::findById)
                .andRoute(POST("/v2/cursos"), handler::save)
                .andRoute(PUT("/v2/cursos/{id}"), handler::update)
                .andRoute(DELETE("/v2/cursos/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routesEstudiante(EstudianteHandler handler) { //, ClientHandler clientHandler
        return route(GET("/v2/estudiantes"), handler::findAll) //req -> handler.findAll(req)
                .andRoute(GET("/v2/estudiantes/asc"), handler::ordenarEstudianteEdadAscendente)
                .andRoute(GET("/v2/estudiantes/desc"), handler::ordenarEstudianteEdadDescendente)
                .andRoute(GET("/v2/estudiantes/{id}"), handler::findById)
                .andRoute(POST("/v2/estudiantes"), handler::save)
                .andRoute(PUT("/v2/estudiantes/{id}"), handler::update)
                .andRoute(DELETE("/v2/estudiantes/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routesMatricula(MatriculaHandler handler) { //, ClientHandler clientHandler
        return route(GET("/v2/matriculas"), handler::findAll) //req -> handler.findAll(req)
                .andRoute(GET("/v2/matriculas/{id}"), handler::findById)
                .andRoute(POST("/v2/matriculas"), handler::save)
                .andRoute(PUT("/v2/matriculas/{id}"), handler::update)
                .andRoute(DELETE("/v2/matriculas/{id}"), handler::delete);
    }



}
