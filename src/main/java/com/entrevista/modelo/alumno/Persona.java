package com.entrevista.modelo.alumno;

import java.math.BigInteger;
import java.sql.Date;

public class Persona {

	private String apellido;
	private String nombre;
	private String tipodoc;
	private BigInteger documento;
	private Date fechanac;
	private String direccion;

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipodoc() {
		return tipodoc;
	}

	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}

	public BigInteger getDocumento() {
		return documento;
	}

	public void setDocumento(BigInteger documento) {
		this.documento = documento;
	}

	public Date getFechanac() {
		return fechanac;
	}

	public void setFechanac(Date fechanac) {
		this.fechanac = fechanac;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
