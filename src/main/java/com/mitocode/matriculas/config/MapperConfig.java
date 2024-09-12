package com.mitocode.matriculas.config;

import com.mitocode.matriculas.dto.CursoDTO;
import com.mitocode.matriculas.dto.EstudianteDTO;
import com.mitocode.matriculas.model.Curso;
import com.mitocode.matriculas.model.Estudiante;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
                .addMapping(CursoDTO::getNombre, (dest, v) -> dest.setNombreCurso((String) v))
                .addMapping(CursoDTO::getSiglas, (dest, v) -> dest.setSiglasCurso((String) v))
                .addMapping(CursoDTO::getEstado, (dest, v) -> dest.setEstadoCurso((Boolean) v));

        //Lectura
        mapper.createTypeMap(Curso.class, CursoDTO.class)
                .addMapping(Curso::getIdCurso, (dest, v) -> dest.setId((String) v))
                .addMapping(Curso::getNombreCurso, (dest, v) -> dest.setNombre((String) v))
                .addMapping(Curso::getSiglasCurso, (dest, v) -> dest.setSiglas((String) v))
                .addMapping(Curso::getEstadoCurso, (dest, v) -> dest.setEstado((Boolean) v));

        return mapper;
    }
}
