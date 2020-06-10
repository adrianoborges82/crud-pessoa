package br.src.sbcrudrestfulws.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Erro {

	public Erro(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	private String mensagem;

}
