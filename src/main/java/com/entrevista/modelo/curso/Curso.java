package com.entrevista.modelo.curso;

import java.util.List;

public class Curso {
	private Integer id;
	private String nombre;
	private String descripcion;
	private Short cupomaximo;
	private Short anio;
	private Docente docente;
	private List<AlumnoListado> alumnos;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Short getCupomaximo() {
		return cupomaximo;
	}

	public void setCupomaximo(Short cupomaximo) {
		this.cupomaximo = cupomaximo;
	}

	public Short getAnio() {
		return anio;
	}

	public void setAnio(Short anio) {
		this.anio = anio;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public List<AlumnoListado> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<AlumnoListado> alumnos) {
		this.alumnos = alumnos;
	}

}
