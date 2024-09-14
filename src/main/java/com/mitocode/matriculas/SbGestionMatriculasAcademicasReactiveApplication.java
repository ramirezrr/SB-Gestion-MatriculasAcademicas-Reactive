package com.mitocode.matriculas;


import lombok.extern.log4j.Log4j2;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
//@Slf4j
@SpringBootApplication
public class SbGestionMatriculasAcademicasReactiveApplication {


    public static void main(String[] args) {

        SpringApplication.run(SbGestionMatriculasAcademicasReactiveApplication.class, args);
        log.info("-------> Matriculas Academicas Reactive");

    }

}
