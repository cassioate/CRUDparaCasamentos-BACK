package com.tessaro.sistema.exceptionhandler.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JaPossuiCasamentoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public JaPossuiCasamentoException(String msg) {
		super(msg);
	}
	public JaPossuiCasamentoException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
