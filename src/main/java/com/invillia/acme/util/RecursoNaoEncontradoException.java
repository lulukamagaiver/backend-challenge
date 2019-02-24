package com.invillia.acme.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3103808671567434864L;
	
	public RecursoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
