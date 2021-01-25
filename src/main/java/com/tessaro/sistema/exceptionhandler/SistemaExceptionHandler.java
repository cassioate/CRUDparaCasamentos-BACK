package com.tessaro.sistema.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.tessaro.sistema.exceptionhandler.exceptions.AindaPossuiCasamento;
import com.tessaro.sistema.exceptionhandler.exceptions.CpfJaCadastrado;
import com.tessaro.sistema.exceptionhandler.exceptions.CpfNaoExiste;
import com.tessaro.sistema.exceptionhandler.exceptions.JaPossuiCasamentoException;
import com.tessaro.sistema.exceptionhandler.exceptions.NaoExisteNaBaseException;
import com.tessaro.sistema.exceptionhandler.exceptions.NegocioException;
import com.tessaro.sistema.exceptionhandler.exceptions.RepetidoExcepetion;

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
	
	@ExceptionHandler({AindaPossuiCasamento.class })
	public ResponseEntity<Object> handleAindaPossuiCasamento(AindaPossuiCasamento ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("ainda.possui.casamento", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("falta.campo", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({JaPossuiCasamentoException.class })
	public ResponseEntity<Object> handleJaPossuiCasamentoException(JaPossuiCasamentoException ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("cpf.ja.possui.casamento", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({NaoExisteNaBaseException.class })
	public ResponseEntity<Object> handleNaoExisteNaBaseException(NaoExisteNaBaseException ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("objeto.inexistente", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ RepetidoExcepetion.class })
	public ResponseEntity<Object> handleRepetidoExcepetion(RepetidoExcepetion ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("possui.perfil", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ NegocioException.class })
	public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("cpf.ja.possui.casamento", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ CpfNaoExiste.class })
	public ResponseEntity<Object> handleCpfNaoExiste(CpfNaoExiste ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("cpf.inexistente", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ CpfJaCadastrado.class })
	public ResponseEntity<Object> handleCpfJaCadastrado(CpfJaCadastrado ex, WebRequest request) {
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
