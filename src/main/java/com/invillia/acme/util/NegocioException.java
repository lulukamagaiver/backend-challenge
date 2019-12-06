package com.invillia.acme.util;

/**
 * @author Marcus Vinicius
 * 6 de dez de 2019
 */
public class NegocioException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7078098861585238401L;

	public NegocioException(String msg) {
		super(msg);
	}

}
