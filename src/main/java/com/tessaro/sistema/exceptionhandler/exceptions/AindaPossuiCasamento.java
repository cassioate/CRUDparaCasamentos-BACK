package com.tessaro.sistema.exceptionhandler.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AindaPossuiCasamento extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AindaPossuiCasamento(String msg) {
		super(msg);
	}
	public AindaPossuiCasamento(String msg, Throwable cause) {
		super(msg,cause);
	}
}
