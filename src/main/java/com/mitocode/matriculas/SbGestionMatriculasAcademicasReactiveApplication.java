package com.mitocode.matriculas;


import com.mitocode.matriculas.model.SecurityRole;
import com.mitocode.matriculas.model.SecurityUser;
import com.mitocode.matriculas.repository.SecurityRoleRepo;
import com.mitocode.matriculas.repository.SecurityUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SbGestionMatriculasAcademicasReactiveApplication  implements CommandLineRunner {

    private final SecurityUserRepo userRepo;
    private final SecurityRoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(SbGestionMatriculasAcademicasReactiveApplication.class, args);
        log.info("-------> Matriculas Academicas Reactive");
    }

    @Override
    public void run(String... args) {

        roleRepo.deleteAll()
                .doOnSuccess(roles -> log.info("Se eliminó los roles"))
                .subscribe();
        userRepo.deleteAll()
                .doOnSuccess(usuarios -> log.info("Se eliminó los usuarios"))
                .subscribe();

        SecurityRole role1 = SecurityRole.builder()
                .id("12345678911")
                .name("ADMIN")
                .build();
        SecurityRole role2 = SecurityRole.builder()
                .id("12345678922")
                .name("USER")
                .build();

        roleRepo.saveAll(Arrays.asList(role1, role2))
                .doOnNext(rol -> log.info("Se creó el rol: {}", rol))
                .doOnError(error -> log.error("Ocurrió un error: {}",error.getMessage()))
                .doOnComplete(() -> log.info("Todos los roles fueron creados correctamente."))
                .subscribe();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode("123");

        SecurityUser adminn = SecurityUser.builder()
                .username("mitocode")
                .password(encryptedPassword)
                .status(true)
                .securityRoles(Collections.singletonList(
                        SecurityRole.builder().id("12345678911").build()
                )).build();

        SecurityUser coder = SecurityUser.builder()
                .username("code")
                .password(encryptedPassword)
                .status(true)
                .securityRoles(Collections.singletonList(
                        SecurityRole.builder().id("12345678922").build()
                )).build();

        userRepo.saveAll(Arrays.asList(adminn, coder))
                .doOnNext(user -> log.info("Se Registró el usuario: {}", user))
                .doOnError(error -> log.info("error al guardar usuario: {}", error.getMessage()))
                .doOnComplete(() -> log.info("Todos los usuarios fueron creados correctamente."))
                .subscribe();

        log.info("Usuario y rol creados correctamente.");
    }

}
