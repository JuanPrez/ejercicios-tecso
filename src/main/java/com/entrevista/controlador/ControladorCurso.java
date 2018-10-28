package com.entrevista.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entrevista.modelo.curso.Curso;
import com.entrevista.repositorio.RepositorioCurso;

@RestController
public class ControladorCurso {

	@Autowired
	private RepositorioCurso repositorioCurso;

	/**
	 * Obtiene el listado completo de cursos
	 * 
	 * @return
	 */
	@GetMapping("/rest/cursos")
	public List<Curso> getCursos(@RequestParam(name = "alumnos", defaultValue = "false") Boolean alumnos) {
		return repositorioCurso.listarCursos(alumnos);
	}

}
