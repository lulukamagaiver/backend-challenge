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
import com.invillia.acme.filter.LojaFilter;
import com.invillia.acme.model.Loja;
import com.invillia.acme.service.LojaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@CrossOrigin("*")
@RestController
@RequestMapping("/loja")
@Api(value = "API Rest LojaResource")
public class LojaResource {
	
	@Autowired
	private LojaService lojaService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * Pesquisar Loja
	 */
	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Loja")
	public Page<Loja> search(LojaFilter lojaFilter, Pageable pageable) {
		return lojaService.filter(lojaFilter, pageable);
	}
	
	/**
	 * Buscar pelo codigo o Loja
	 */
	@GetMapping("/{code}")
	@ApiOperation(value = "Retorna um Loja pelo Codigo")
	public ResponseEntity<Loja> buscarPeloCodigo(@PathVariable Long code) {
		Loja loja = lojaService.buscarLojaPeloCodigo(code);
		return loja != null ? ResponseEntity.ok(loja) : ResponseEntity.notFound().build();
	}
	
	/**
	 * Criar Loja
	 */
	@PostMapping
	@ApiOperation(value = "criar um Loja")
	public ResponseEntity<Loja> criar(@Valid @RequestBody Loja loja, HttpServletResponse response) {
		Loja lojaSalvo = lojaService.save(loja);
		publisher.publishEvent(new RecursoCriado(this, response, lojaSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lojaSalvo);
	}
	
	/**
	 * Atualizar Loja
	 */
	@PutMapping("/{code}")
	@ApiOperation(value = "Atualizar um Loja")
	public ResponseEntity<Loja> atualizar(@PathVariable Long code, @Valid @RequestBody Loja loja) {
		Loja lojaSalva = lojaService.atualizar(code, loja);
		return ResponseEntity.ok(lojaSalva);
	}
	

	/**
	 * Deletar Loja
	 */
	@DeleteMapping("/{code}")
	@ApiOperation(value = "Deletar um Loja")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long code) {
		lojaService.delete(code);
	}

}
