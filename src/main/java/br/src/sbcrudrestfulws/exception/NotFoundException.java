package br.src.sbcrudrestfulws.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String mensagem) {
		super(mensagem);
	}

}
