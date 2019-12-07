package com.invillia.acme.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.event.RecursoCriado;
import com.invillia.acme.filter.ClienteFilter;
import com.invillia.acme.model.Cliente;
import com.invillia.acme.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@CrossOrigin("*")
@RestController
@RequestMapping("/cliente")
@Api(value = "API Rest ClienteResource")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * Pesquisar Cliente
	 */
	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Cliente")
	public Page<Cliente> search(ClienteFilter clienteFilter, Pageable pageable) {
		return clienteService.filter(clienteFilter, pageable);
	}
	
	/**
	 * Buscar pelo codigo o Cliente
	 */
	@GetMapping("/{code}")
	@ApiOperation(value = "Retorna um Cliente pelo Codigo")
	public ResponseEntity<Cliente> buscarPeloCodigo(@PathVariable Long code) {
		Cliente cliente = clienteService.buscarClientePeloCodigo(code);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}
	
	/**
	 * Criar Cliente
	 */
	@PostMapping
	@ApiOperation(value = "criar um Cliente")
	public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		Cliente clienteSalvo = clienteService.save(cliente);
		publisher.publishEvent(new RecursoCriado(this, response, clienteSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	/**
	 * Atualizar Cliente
	 */
	@PutMapping("/{code}")
	@ApiOperation(value = "Atualizar um Cliente")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long code, @Valid @RequestBody Cliente cliente) {
		Cliente clienteSalva = clienteService.atualizar(code, cliente);
		return ResponseEntity.ok(clienteSalva);
	}
	

	/**
	 * Deletar Cliente
	 */
	@DeleteMapping("/{code}")
	@ApiOperation(value = "Deletar um Cliente")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long code) {
		clienteService.delete(code);
	}

}
