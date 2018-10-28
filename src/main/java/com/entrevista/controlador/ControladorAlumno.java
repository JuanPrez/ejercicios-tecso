package com.entrevista.controlador;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entrevista.excepcion.RecursoNoEncontradoException;
import com.entrevista.modelo.alumno.Alumno;
import com.entrevista.repositorio.RepositorioAlumno;

@RestController
public class ControladorAlumno {

	@Autowired
	private RepositorioAlumno repositorioAlumno;

	/**
	 * Obtiene un alumno usando el id
	 * 
	 * @param alumnoId
	 * @return
	 */
	@GetMapping("/rest/alumno/{alumnoId}")
	public Optional<Alumno> getAlumnoById(@PathVariable Integer alumnoId) {
		return repositorioAlumno.listarPorId(alumnoId);
	}

	/**
	 * Obtiene el listado completo de alumnos
	 * 
	 * @return
	 */
	@GetMapping("/rest/alumnos")
	public List<Alumno> getAlumnos() {
		return repositorioAlumno.listarAlumnos();
	}

	/**
	 * Crea un nuevo alumno
	 * 
	 * @param alumno
	 * @return
	 */
	@PostMapping("/rest/alumno")
	public Alumno createAlumno(@Valid @RequestBody Alumno alumno) {
		return repositorioAlumno.crear(alumno);
	}

	/**
	 * Actualiza los datos de un alumno, primero busca si el alumno existe, de ser
	 * asi, lo actualiza usando el alumno pasado en el cuerpo del mensaje
	 * 
	 * 
	 * @param alumnoId
	 * @param alumnoRequest
	 * @return
	 */
	@PutMapping("/rest/alumno/{alumnoId}")
	public Alumno updateAlumno(@PathVariable Integer alumnoId, @Valid @RequestBody Alumno alumnoRequest) {
		return repositorioAlumno.listarPorId(alumnoId).map(alumno -> {
			alumno.setLegajo(alumnoRequest.getLegajo());
			alumno.setPersona(alumnoRequest.getPersona());
			return repositorioAlumno.guardar(alumno);
		}).orElseThrow(() -> new RecursoNoEncontradoException("Alumno no encontrada con ID: " + alumnoId));
	}

	/**
	 * Inscribe a un alumno en un curso, basicamente crea una nueva inscripcion
	 * 
	 * @param idalumno
	 * @param idcurso
	 * @return
	 */
	@PostMapping("/rest/inscripcion/alumno/{idalumno}/curso/{idcurso}")
	public Boolean createAlumno(@PathVariable Integer idalumno, @PathVariable Integer idcurso) {
		return repositorioAlumno.inscribir(idalumno, idcurso);
	}

}
