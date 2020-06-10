package br.src.sbcrudrestfulws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(value = { NotFoundException.class })
	public ResponseEntity<?> handleCustomException(NotFoundException ex, WebRequest request) {

		String errorMessageDescription = ex.getLocalizedMessage();

		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		Erro erro = new Erro(ex.getMessage());

		return new ResponseEntity(erro, HttpStatus.NOT_FOUND);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {

		String errorMessageDescription = ex.getLocalizedMessage();

		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		Erro erro = new Erro(ex.getMessage());

		return new ResponseEntity(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
