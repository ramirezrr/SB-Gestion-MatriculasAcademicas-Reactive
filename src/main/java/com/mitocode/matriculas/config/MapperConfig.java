package com.mitocode.matriculas.config;

import com.mitocode.matriculas.dto.CursoDTO;
import com.mitocode.matriculas.dto.EstudianteDTO;
import com.mitocode.matriculas.dto.MatriculaDTO;
import com.mitocode.matriculas.model.Curso;
import com.mitocode.matriculas.model.Estudiante;
import com.mitocode.matriculas.model.Matricula;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class MapperConfig {



    @Bean(name = "estudiantesMapper")
    public ModelMapper estudianteMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.createTypeMap(EstudianteDTO.class, Estudiante.class)
                .addMapping(EstudianteDTO::getId, (dest, v) -> dest.setIdEstudiante((String) v))
                .addMapping(EstudianteDTO::getNombre, (dest, v) -> dest.setNombreEstudiante((String) v))
                .addMapping(EstudianteDTO::getApellido, (dest, v) -> dest.setApellidoEstudiante((String) v))
                .addMapping(EstudianteDTO::getDni, (dest, v) -> dest.setDniEstudiante((Integer) v))
                .addMapping(EstudianteDTO::getEdad, (dest, v) -> dest.setEdadEstudiante((Short) v));

        //Lectura
        mapper.createTypeMap(Estudiante.class, EstudianteDTO.class)
                .addMapping(Estudiante::getIdEstudiante, (dest, v) -> dest.setId((String) v))
                .addMapping(Estudiante::getNombreEstudiante, (dest, v) -> dest.setNombre((String) v))
                .addMapping(Estudiante::getApellidoEstudiante, (dest, v) -> dest.setApellido((String) v))
                .addMapping(Estudiante::getDniEstudiante, (dest, v) -> dest.setDni((Integer) v))
                .addMapping(Estudiante::getEdadEstudiante, (dest, v) -> dest.setEdad((Short) v));

        return mapper;
    }

    @Bean(name = "cursosMapper")
    public ModelMapper cursoMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //Escritura
        mapper.createTypeMap(CursoDTO.class, Curso.class)
                .addMapping(CursoDTO::getId, (dest, v) -> dest.setIdCurso((String) v))
                .addMapping(CursoDTO::getNombre, (dest, v) -> dest.setNameCurso((String) v))
                .addMapping(CursoDTO::getSiglas, (dest, v) -> dest.setSiglasCurso((String) v))
                .addMapping(CursoDTO::getEstado, (dest, v) -> dest.setEstadoCurso((Boolean) v));

        //Lectura
        mapper.createTypeMap(Curso.class, CursoDTO.class)
                .addMapping(Curso::getIdCurso, (dest, v) -> dest.setId((String) v))
                .addMapping(Curso::getNameCurso, (dest, v) -> dest.setNombre((String) v))
                .addMapping(Curso::getSiglasCurso, (dest, v) -> dest.setSiglas((String) v))
                .addMapping(Curso::getEstadoCurso, (dest, v) -> dest.setEstado((Boolean) v));

        return mapper;
    }

    @Bean(name = "matriculasMapper")
    public ModelMapper matriculaMapper(@Qualifier("cursosMapper") ModelMapper cursosMapper) {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Escritura
        mapper.createTypeMap(MatriculaDTO.class, Matricula.class)
                .addMapping(MatriculaDTO::getId, Matricula::setId)
                .addMapping(MatriculaDTO::getFecha, Matricula::setFecha)
                .addMapping(MatriculaDTO::getEstado, Matricula::setEstado)
                .addMapping(e -> e.getEstudiante().getId(), (dest, v) -> dest.getEstudiante().setIdEstudiante((String) v))
                .addMappings(m -> m.using(ctx -> {
                    List<CursoDTO> cursoDTOs = (List<CursoDTO>) ctx.getSource();
                    return cursoDTOs.stream()
                            .map(dto -> cursosMapper.map(dto, Curso.class))
                            .collect(Collectors.toList());
                }).map(MatriculaDTO::getCursos, Matricula::setCursos));

        // Lectura
        mapper.createTypeMap(Matricula.class, MatriculaDTO.class)
                .addMapping(Matricula::getId, MatriculaDTO::setId)
                .addMapping(Matricula::getFecha, MatriculaDTO::setFecha)
                .addMapping(Matricula::getEstado, MatriculaDTO::setEstado)
                .addMapping(e -> e.getEstudiante().getIdEstudiante(), (dest, v) -> dest.getEstudiante().setId((String) v))
                .addMappings(m -> m.using(ctx -> {
                    List<Curso> cursos = (List<Curso>) ctx.getSource();
                    return cursos.stream()
                            .map(curso -> cursosMapper.map(curso, CursoDTO.class))
                            .collect(Collectors.toList());
                }).map(Matricula::getCursos, MatriculaDTO::setCursos));

        return mapper;
    }

}
