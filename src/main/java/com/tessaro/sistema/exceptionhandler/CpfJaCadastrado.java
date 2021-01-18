package com.tessaro.sistema.exceptionhandler;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CpfJaCadastrado extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public CpfJaCadastrado(String msg) {
		super(msg);
	}
	public CpfJaCadastrado(String msg, Throwable cause) {
		super(msg,cause);
	}
}
