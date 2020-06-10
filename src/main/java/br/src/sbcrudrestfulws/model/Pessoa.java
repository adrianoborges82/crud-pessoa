package br.src.sbcrudrestfulws.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.src.sbcrudrestfulws.util.CustomJsonDateDeserializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pessoa")
public class Pessoa {

	public Pessoa() {

	}

	public Pessoa(long id, String nome, String cpf, Date dataNascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cpf", length = 11)
	private String cpf;

	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
