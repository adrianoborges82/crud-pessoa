package br.src.sbcrudrestfulws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.src.sbcrudrestfulws.exception.NotFoundException;
import br.src.sbcrudrestfulws.model.Pessoa;
import br.src.sbcrudrestfulws.repository.PessoaRepository;

@RestController
@RequestMapping("/api/v1")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	// inserir pessoa
	@PostMapping("/pessoas")
	public Pessoa inserirPessoa(@RequestBody Pessoa pessoa) throws Exception {

		try {

			return pessoaRepository.save(pessoa);

		} catch (Exception e) {
			throw new Exception("Erro ao gravar dados de pessoa.");
		}
	}

	// recuperar pessoa pelo id
	@GetMapping("/pessoas/{id}")
	public ResponseEntity<Pessoa> recuperarPessoaPorId(@PathVariable(value = "id") long idPessoa) throws Exception {

		try {

			Pessoa pessoa = pessoaRepository.findById(idPessoa)
					.orElseThrow(() -> new NotFoundException("Pessoa não encontrada com o id: " + idPessoa));
			return ResponseEntity.ok().body(pessoa);

		} catch (NotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Erro ao consultar pessoa.");
		}
	}

	// recuperar todas as pessoas
	@GetMapping("/pessoas")
	public List<Pessoa> recuperarPessoas() throws Exception {

		try {

			return pessoaRepository.findAll();

		} catch (Exception e) {
			throw new Exception("Erro ao consultar pessoas.");
		}
	}

	// atualizar pessoa
	@PutMapping("/pessoas/{id}")
	public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable(value = "id") long idPessoa,
			@RequestBody Pessoa pessoaNew) throws Exception {

		Pessoa pessoa = null;

		try {

			pessoa = pessoaRepository.findById(idPessoa).orElseThrow(
					() -> new NotFoundException("Pessoa não encontrada com o id: " + idPessoa + "."));

			pessoa.setNome(pessoaNew.getNome());
			pessoa.setCpf(pessoaNew.getCpf());
			pessoa.setDataNascimento(pessoaNew.getDataNascimento());

			pessoaRepository.save(pessoa);
			return ResponseEntity.ok(pessoa);

		} catch (NotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Erro ao gravar dados de pessoa.");
		}

	}

	// excluir pessoa
	@DeleteMapping("/pessoas/{id}")
	public ResponseEntity<?> excluirPessoa(@PathVariable(value = "id") long id) throws Exception {

		try {

			Pessoa pessoa = pessoaRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("Pessoa não encontrada com o id: " + id));

			pessoaRepository.delete(pessoa);

		} catch (NotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Erro ao excluir pessoa.");
		}

		return ResponseEntity.ok().build();
	}
}
