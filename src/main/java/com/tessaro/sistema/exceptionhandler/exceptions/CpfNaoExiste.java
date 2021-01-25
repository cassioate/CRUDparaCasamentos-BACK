package com.tessaro.sistema.exceptionhandler.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CpfNaoExiste extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public CpfNaoExiste(String msg) {
		super(msg);
	}
	public CpfNaoExiste(String msg, Throwable cause) {
		super(msg,cause);
	}
}
