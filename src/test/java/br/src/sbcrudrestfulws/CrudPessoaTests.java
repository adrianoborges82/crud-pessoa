package br.src.sbcrudrestfulws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import br.src.sbcrudrestfulws.model.Pessoa;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbCrudRestfulWsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrudPessoaTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port + "/api/v1";
	}

	
	//Inserir pessoa
	@Test	
	public void test1() {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome("João da Silva");
		pessoa.setCpf("123456");

		ResponseEntity<Pessoa> postResponse = restTemplate.postForEntity(getRootUrl() + "/pessoas", pessoa,
				Pessoa.class);

		assertNotNull(postResponse.getBody());
	}
	
	//Recuperar pessoas
	@Test
	public void test2() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/pessoas", HttpMethod.GET, entity,
				String.class);

		assertEquals(200, response.getStatusCodeValue());
	}
	
	//Recuperar pessoa por Id
	@Test
	public void test3() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/pessoas/1", HttpMethod.GET, entity,
				String.class);

		assertEquals(200, response.getStatusCodeValue());
	}
	
	//Atualizar pessoa
	@Test
	public void test4() {

		Pessoa pessoa = restTemplate.getForObject(getRootUrl() + "/pessoas/1", Pessoa.class);
		pessoa.setNome("Ciclano Silveira");
		pessoa.setCpf("987654");

		restTemplate.put(getRootUrl() + "/pessoas/1", pessoa);

		Pessoa pessoaNew = restTemplate.getForObject(getRootUrl() + "/pessoas/1", Pessoa.class);
		assertNotNull(pessoaNew);
	}
	
	//Exluir pessoa
	@Test
	public void test5() {

		Pessoa pessoa = restTemplate.getForObject(getRootUrl() + "/pessoas/1", Pessoa.class);
		assertNotNull(pessoa);

		restTemplate.delete(getRootUrl() + "/pessoas/1");

		try {
			pessoa = restTemplate.getForObject(getRootUrl() + "/pessoas/1", Pessoa.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}