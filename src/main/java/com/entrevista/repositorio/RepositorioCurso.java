package com.entrevista.repositorio;

import java.util.List;

import com.entrevista.modelo.curso.Curso;

public interface RepositorioCurso {
	List<Curso> listarCursos(Boolean alumnos);

}
