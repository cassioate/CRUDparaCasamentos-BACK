package com.tessaro.sistema.exceptionhandler.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RepetidoExcepetion extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RepetidoExcepetion(String msg) {
		super(msg);
	}
	public RepetidoExcepetion(String msg, Throwable cause) {
		super(msg,cause);
	}
}
