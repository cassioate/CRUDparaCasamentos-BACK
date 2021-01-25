package com.tessaro.sistema.exceptionhandler.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NaoExisteNaBaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NaoExisteNaBaseException(String msg) {
		super(msg);
	}
	public NaoExisteNaBaseException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
