package com.entrevista.modelo.alumno;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

public class AlumnoCarrera {
	private Integer id;
	private String nombre;
	private Date fechaInscripcion;
	private BigDecimal promedio;
	private Set<AlumnoCurso> cursos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public BigDecimal getPromedio() {
		return promedio;
	}

	public void setPromedio(BigDecimal promedio) {
		this.promedio = promedio;
	}

	public Set<AlumnoCurso> getCursos() {
		return cursos;
	}

	public void setCursos(Set<AlumnoCurso> cursos) {
		this.cursos = cursos;
	}

}
