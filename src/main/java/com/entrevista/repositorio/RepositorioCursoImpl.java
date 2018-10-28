package com.entrevista.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entrevista.modelo.curso.AlumnoListado;
import com.entrevista.modelo.curso.Curso;
import com.entrevista.modelo.curso.Docente;

@Repository
public class RepositorioCursoImpl implements RepositorioCurso {

	@Autowired
	EntityManager em;

	/**
	 * Obtiene la lista de cursos y la devuelve
	 * 
	 * @return
	 */
	@Override
	public List<Curso> listarCursos(Boolean alumnos) {
		Query q = em.createNativeQuery("SELECT  " + "	C.identificador as id, " + "	C.nombre as nombre_curso, "
				+ "	C.descripcion, " + "	C.cupomaximo, " + "	C.anio,  " + "	D.identificador as iddocente, "
				+ "	D.nombre as nombre_docente " + "FROM curso C "
				+ "	JOIN docente D ON D.identificador = C.iddocente;");

		List<Curso> listaCursos = new ArrayList<>();
		for (Object lista : q.getResultList()) {
			Object[] object = (Object[]) lista;
			Curso curso = new Curso();

			curso.setId((Integer) object[0]);
			curso.setNombre((String) object[1]);
			curso.setDescripcion((String) object[2]);
			curso.setCupomaximo((Short) object[3]);
			curso.setAnio((Short) object[4]);

			Docente docente = new Docente((Integer) object[5], (String) object[6]);
			curso.setDocente(docente);

			listaCursos.add(curso);

			if (alumnos)
				curso.setAlumnos(obtenerAlumnosCurso(curso.getId()));
		}
		return listaCursos;
	}

	/**
	 * Obtiene la lista de alumnos por cada curso
	 * 
	 * @param cursoId
	 * @return
	 */
	public List<AlumnoListado> obtenerAlumnosCurso(Integer cursoId) {

		List<AlumnoListado> listaAlumnos = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT A.identificador as idalumno, A.legajo as legajo, P.nombre as "
					+ "	  alumno_nombre, P.apellido as alumno_apellido FROM curso CU JOIN "
					+ "	  inscripciones_curso ICU ON CU.identificador = ICU.idcurso JOIN alumno A ON "
					+ "	  A.identificador = ICU.idalumno JOIN persona P ON A.idpersona = "
					+ "	  P.identificador WHERE idcurso = :cursoId ;");
			q.setParameter("cursoId", cursoId);

			for (Object lista : q.getResultList()) {
				Object[] object = (Object[]) lista;

				AlumnoListado alumno = new AlumnoListado();

				alumno.setId((Integer) object[0]);
				alumno.setLegajo((Integer) object[1]);
				alumno.setNombre((String) object[2]);
				alumno.setApellido((String) object[3]);

				listaAlumnos.add(alumno);
			}
		} catch (Exception exc) {
			// Algo con los registros no encontrados
		}
		return listaAlumnos;
	}
}
