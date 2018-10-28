package com.entrevista.repositorio;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entrevista.modelo.alumno.Alumno;
import com.entrevista.modelo.alumno.AlumnoCarrera;
import com.entrevista.modelo.alumno.AlumnoCurso;
import com.entrevista.modelo.alumno.Persona;
import com.entrevista.modelo.curso.Docente;

@Repository
public class RepositorioAlumnoImpl implements RepositorioAlumno {

	@Autowired
	EntityManager em;

	@Override
	public Optional<Alumno> listarPorId(Integer alumnoId) {
		try {
			Query q = em.createNativeQuery("SELECT  " + "	A.identificador as id, " + "	A.legajo, "
					+ "	P.tipodoc,  " + "	P.documento, " + "	P.apellido, " + "	P.nombre,  " + "	P.fechanac, "
					+ "	P.direccion " + " FROM alumno AS A "
					+ " LEFT JOIN persona AS P ON P.identificador = A.idpersona " + " WHERE A.identificador = :id ;");
			q.setParameter("id", alumnoId);

			Object[] object = (Object[]) q.getSingleResult();

			Alumno alumno = new Alumno();

			alumno.setId((Integer) object[0]);
			alumno.setLegajo((Integer) object[1]);

			Persona persona = new Persona();
			persona.setTipodoc((String) object[2]);
			persona.setDocumento((BigInteger) object[3]);
			persona.setApellido((String) object[4]);
			persona.setNombre((String) object[5]);
			persona.setFechanac((Date) object[6]);
			persona.setDireccion((String) object[7]);

			alumno.setPersona(persona);

			obtenerCarreras(Arrays.asList(alumno));

			return Optional.of(alumno);

		} catch (Exception exc) {
			// Algo con los registros no encontrados
			return Optional.ofNullable(null);
		}
	}

	private void obtenerCarreras(List<Alumno> listaAlumno) {
		for (Alumno alumno : listaAlumno) {
			Set<AlumnoCarrera> listaCarrera = new HashSet<>();
			try {

				Query q = em.createNativeQuery("SELECT " + " CA.identificador as idcarrera, "
						+ " ICA.fechainscripcion, " + "	CA.nombre as nombre_carrera " + " FROM alumno A"
						+ "	JOIN inscripciones_carrera ICA ON A.identificador = ICA.idalumno"
						+ "	JOIN carrera CA ON ICA.idcarrera = CA.identificador " + " WHERE A.identificador = :id ;");
				q.setParameter("id", alumno.getId());

				for (Object lista : q.getResultList()) {
					Object[] object = (Object[]) lista;
					AlumnoCarrera carrera = new AlumnoCarrera();

					carrera.setId((Integer) object[0]);
					carrera.setFechaInscripcion((Date) object[1]);
					carrera.setNombre((String) object[2]);

					listaCarrera.add(carrera);
				}

			} catch (Exception exc) {
				// Algo con los registros no encontrados
			}
			alumno.setCarreras(listaCarrera);

			obtenerCursos(Arrays.asList(alumno));
		}
	}

	private void obtenerCursos(List<Alumno> listaAlumno) {
		for (Alumno alumno : listaAlumno) {

			if (alumno.getCarreras() != null || !alumno.getCarreras().isEmpty()) {

				for (AlumnoCarrera carrera : alumno.getCarreras()) {
					Set<AlumnoCurso> listaCursos = new HashSet<>();
					try {
						Query q = em.createNativeQuery("SELECT " + "	ICU.idcurso as idcurso, "
								+ "	CU.nombre as nombre_curso, " + "	ICU.fechainscripcion, " + "	ICU.nota as nota, "
								+ "	D.identificador as iddocente," + "	D.nombre as nombre_docente" + " FROM alumno A "
								+ "	JOIN inscripciones_curso ICU ON A.identificador = ICU.idalumno"
								+ "	JOIN curso CU ON ICU.idcurso = CU.identificador"
								+ "	JOIN carrera CA ON CA.identificador = CU.idcarrera"
								+ "	JOIN docente D ON CU.iddocente = D.identificador"
								+ " WHERE A.identificador = :idalumno AND CA.identificador = :idcarrera ;");
						q.setParameter("idalumno", alumno.getId());
						q.setParameter("idcarrera", carrera.getId());

						for (Object lista : q.getResultList()) {
							Object[] object = (Object[]) lista;
							AlumnoCurso curso = new AlumnoCurso();
							curso.setId((Integer) object[0]);
							curso.setNombre((String) object[1]);
							curso.setFechaInscripcion((Date) object[2]);
							curso.setNota((Short) object[3]);
							curso.setDocente(new Docente((Integer) object[4], (String) object[5]));

							listaCursos.add(curso);
						}

						carrera.setPromedio(obtenerPromedio(alumno.getId(), carrera.getId()).setScale(2));
					} catch (Exception exc) {
						// Algo con los registros no encontrados
					}
					carrera.setCursos(listaCursos);
				}
			}
		}
	}

	private BigDecimal obtenerPromedio(Integer idAlumno, Integer idCarrera) throws NoResultException {

		Query q = em.createNativeQuery("SELECT avg(ICU.nota) " + "		FROM alumno A "
				+ "			JOIN inscripciones_carrera ICA ON A.identificador = ICA.idalumno "
				+ "			JOIN inscripciones_curso ICU ON A.identificador = ICU.idalumno "
				+ "			JOIN curso CU ON CU.idcarrera = ICA.idcarrera " + "		WHERE A.identificador = :idalumno "
				+ "		 	AND ICA.idcarrera = :idcarrera " + "			AND ICU.nota IS NOT NULL "
				+ "			GROUP BY A.identificador, CU.idcarrera;");
		q.setParameter("idalumno", idAlumno);
		q.setParameter("idcarrera", idCarrera);

		return ((BigDecimal) q.getSingleResult());
	}

	@Override
	public List<Alumno> listarAlumnos() {
		Query q = em.createNativeQuery("SELECT  " + "	A.identificador as id, " + "	A.legajo, " + "	P.tipodoc,  "
				+ "	P.documento, " + "	P.apellido, " + "	P.nombre,  " + "	P.fechanac, " + "	P.direccion "
				+ " FROM alumno AS A " + " LEFT JOIN persona AS P ON P.identificador = A.idpersona");

		List<Alumno> listaAlumnos = new ArrayList<>();
		for (Object lista : q.getResultList()) {
			Object[] object = (Object[]) lista;
			Alumno alumno = new Alumno();

			alumno.setId((Integer) object[0]);
			alumno.setLegajo((Integer) object[1]);

			Persona persona = new Persona();
			persona.setTipodoc((String) object[2]);
			persona.setDocumento((BigInteger) object[3]);
			persona.setApellido((String) object[4]);
			persona.setNombre((String) object[5]);
			persona.setFechanac((Date) object[6]);
			persona.setDireccion((String) object[7]);

			alumno.setPersona(persona);

			listaAlumnos.add(alumno);
		}

		obtenerCarreras(listaAlumnos);

		return listaAlumnos;
	}

	@Transactional
	@Override
	public Alumno guardar(Alumno alumno) {
		Query persona = em.createNativeQuery("UPDATE persona " + " SET tipodoc= :tipodoc, documento= :documento , "
				+ "  nombre= :nombre, apellido= :apellido, " + "  fechanac= :fechanac, direccion= :direccion "
				+ " WHERE identificador = (SELECT idpersona FROM alumno WHERE identificador = :idalumno );");
		persona.setParameter("idalumno", alumno.getId());
		persona.setParameter("tipodoc", alumno.getPersona().getTipodoc());
		persona.setParameter("documento", alumno.getPersona().getDocumento());
		persona.setParameter("nombre", alumno.getPersona().getNombre());
		persona.setParameter("apellido", alumno.getPersona().getApellido());
		persona.setParameter("fechanac", alumno.getPersona().getFechanac());
		persona.setParameter("direccion", alumno.getPersona().getDireccion());

		if (persona.executeUpdate() > 0) {
			return alumno;
		}

		return null;
	}

	@Transactional
	@Override
	public Alumno crear(Alumno alumno) {
		Integer idPersona = 0;

		Query qidPersona = em.createNativeQuery("SELECT MAX(identificador)+1 as id FROM persona;");

		idPersona = (Integer) qidPersona.getSingleResult();

		// Crear persona
		Query qPersona = em.createNativeQuery(
				"INSERT INTO persona(" + "identificador, tipodoc, documento, nombre, apellido, fechanac," + "direccion)"
						+ "VALUES (:id , :tipodoc , :documento , :nombre , :apellido , :fechanac , :direccion );");
		qPersona.setParameter("id", idPersona);
		qPersona.setParameter("tipodoc", alumno.getPersona().getTipodoc());
		qPersona.setParameter("documento", alumno.getPersona().getDocumento());
		qPersona.setParameter("nombre", alumno.getPersona().getNombre());
		qPersona.setParameter("apellido", alumno.getPersona().getApellido());
		qPersona.setParameter("fechanac", alumno.getPersona().getFechanac());
		qPersona.setParameter("direccion", alumno.getPersona().getDireccion());

		if (qPersona.executeUpdate() > 0) {
			Integer idAlumno = 0;
			Query qidAlumno = em.createNativeQuery("SELECT MAX(identificador)+1 as id FROM alumno;");

			idAlumno = (Integer) qidAlumno.getSingleResult();

			// Crear alumno
			Query qAlumno = em.createNativeQuery("INSERT INTO alumno VALUES (:id , :idpersona , :legajo );");
			qAlumno.setParameter("id", idAlumno);
			qAlumno.setParameter("idpersona", idPersona);
			qAlumno.setParameter("legajo", alumno.getLegajo());

			if (qAlumno.executeUpdate() > 0) {
				return alumno;
			}
		}
		return null;
	}

	@Transactional
	@Override
	public Boolean inscribir(Integer idalumno, Integer idcurso) {
		Query qInscripcion = em.createNativeQuery(
				"INSERT INTO inscripciones_curso VALUES (:idalumno , :idcurso, current_date, null);");
		qInscripcion.setParameter("idalumno", idalumno);
		qInscripcion.setParameter("idcurso", idcurso);

		if (qInscripcion.executeUpdate() > 0) {
			return true;
		}
		return false;
	}
}
