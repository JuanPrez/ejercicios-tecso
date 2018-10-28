package com.entrevista.modelo.alumno;

import java.util.Set;

public class Alumno {

	private Integer id;
	private Persona persona;
	private Integer legajo;
	private Set<AlumnoCarrera> carreras;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Integer getLegajo() {
		return legajo;
	}

	public void setLegajo(Integer legajo) {
		this.legajo = legajo;
	}

	public Set<AlumnoCarrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(Set<AlumnoCarrera> carreras) {
		this.carreras = carreras;
	}

}
