package com.entrevista.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNoEncontradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecursoNoEncontradoException(String message) {
		super(message);
	}

	public RecursoNoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}
}