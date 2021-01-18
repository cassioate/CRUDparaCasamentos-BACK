package com.tessaro.sistema.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SistemaExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = errosValidator(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({IllegalArgumentException.class })
	public ResponseEntity<Object> handleConstraintViolationException(IllegalArgumentException ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("falta.campo", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
//	@ExceptionHandler({ConstraintViolationException.class })
//	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
//		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
//		String mensagemUsuario = messageSource.getMessage("falta.campo", null, LocaleContextHolder.getLocale());
//		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
//		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//	}
	
	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("nao.existe.na.base", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({ RepetidoExcepetion.class })
	public ResponseEntity<Object> handleNoSuchElementException(RepetidoExcepetion ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("possui.perfil", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ NegocioException.class })
	public ResponseEntity<Object> handleNoSuchElementException(NegocioException ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("cpf.ja.possui.casamento", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ CpfNaoExiste.class })
	public ResponseEntity<Object> handleNoSuchElementException(CpfNaoExiste ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("cpf.inexistente", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ CpfJaCadastrado.class })
	public ResponseEntity<Object> handleNoSuchElementException(CpfJaCadastrado ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("cpf.ja.existe", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	
/////// ---- Métodos ---- ///////
	
	public List<Erro> errosValidator (BindingResult ex) {
		List<Erro> erros = new ArrayList<>();
		for (ObjectError x : ex.getAllErrors()) {
			
			String mensagemUsuario = messageSource.getMessage(x, LocaleContextHolder.getLocale());
			String mensagemDev = x.toString();
			
			erros.add(new Erro(mensagemUsuario, mensagemDev));
		}
		return erros;
	}

//	O metodo acima é igual esse de baixo, só que melhorado!!
//	public List<Erro> criarListaDeErros(BindingResult ex) {
//		List<Erro> erros = new ArrayList<>();
//
//		for (FieldError fieldError : ex.getFieldErrors()) {
//			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
//			String mensagemDev = fieldError.toString();
//			erros.add(new Erro(mensagemUsuario, mensagemDev));
//		}S
//		return erros;
//	}

}
