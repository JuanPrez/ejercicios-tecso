package com.entrevista.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.entrevista.modelo.alumno.Alumno;

@Repository
public interface RepositorioAlumno {

	Optional<Alumno> listarPorId(Integer alumnoId);
	List<Alumno> listarAlumnos();
	Alumno guardar(Alumno alumno);
	Alumno crear(Alumno alumno);
	Boolean inscribir(Integer idalumno, Integer idcurso);

}
