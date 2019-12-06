package com.invillia.acme.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;


/**
 * @author Marcus Vinicius
 * 6 de dez de 2019
 */
public class RecursoCriado extends ApplicationEvent {
	
private static final long serialVersionUID = 1L;
	
	private HttpServletResponse response;
	private Long codigo;

	public RecursoCriado(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCodigo() {
		return codigo;
	}

}
