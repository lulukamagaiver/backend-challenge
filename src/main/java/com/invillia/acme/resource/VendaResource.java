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

import com.invillia.acme.enums.StatusVenda;
import com.invillia.acme.event.RecursoCriado;
import com.invillia.acme.filter.VendaFilter;
import com.invillia.acme.model.Venda;
import com.invillia.acme.service.VendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/venda")
@Api(value = "API Rest VendaResource")
public class VendaResource {
	
	@Autowired
	private VendaService vendaService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * Pesquisar Venda
	 */
	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Venda")
	public Page<Venda> search(VendaFilter vendaFilter, Pageable pageable) {
		return vendaService.filter(vendaFilter, pageable);
	}
	
	/**
	 * Buscar pelo codigo o Venda
	 */
	@GetMapping("/{code}")
	@ApiOperation(value = "Retorna um Venda pelo Codigo")
	public ResponseEntity<Venda> buscarPeloCodigo(@PathVariable Long code) {
		Venda venda = vendaService.buscarVendaPeloCodigo(code);
		return venda != null ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
	}
	
	/**
	 * Criar Venda
	 */
	@PostMapping
	@ApiOperation(value = "criar um Venda")
	public ResponseEntity<Venda> criar(@Valid @RequestBody Venda venda, HttpServletResponse response) {
		Venda vendaSalvo = vendaService.save(venda);
		publisher.publishEvent(new RecursoCriado(this, response, vendaSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalvo);
	}
	
	/**
	 * Atualizar Venda
	 */
	@PutMapping("/{code}")
	@ApiOperation(value = "Atualizar um Venda")
	public ResponseEntity<Venda> atualizar(@PathVariable Long code, @Valid @RequestBody Venda venda) {
		Venda vendaSalva = vendaService.atualizar(code, venda);
		return ResponseEntity.ok(vendaSalva);
	}
	
	/**
	 * Atualizar status da Venda
	 */
	@PutMapping("/{code}/statusVenda")
	@ApiOperation(value = "Atualizar status de um Venda")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatusVenda(@PathVariable Long code, StatusVenda statusVenda){
		vendaService.atualizarStatusVenda(code, statusVenda);
	}
	

	/**
	 * Deletar Venda
	 */
	@DeleteMapping("/{code}")
	@ApiOperation(value = "Deletar um Venda")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long code) {
		vendaService.delete(code);
	}

}
