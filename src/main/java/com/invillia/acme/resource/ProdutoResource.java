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
import com.invillia.acme.filter.ProdutoFilter;
import com.invillia.acme.model.Produto;
import com.invillia.acme.service.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/produto")
@Api(value = "API Rest ProdutoResource")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * Pesquisar Produto
	 */
	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Produto")
	public Page<Produto> search(ProdutoFilter produtoFilter, Pageable pageable) {
		return produtoService.filter(produtoFilter, pageable);
	}
	
	/**
	 * Buscar pelo codigo o Produto
	 */
	@GetMapping("/{code}")
	@ApiOperation(value = "Retorna um Produto pelo Codigo")
	public ResponseEntity<Produto> buscarPeloCodigo(@PathVariable Long code) {
		Produto produto = produtoService.buscarProdutoPeloCodigo(code);
		return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}
	
	/**
	 * Criar Produto
	 */
	@PostMapping
	@ApiOperation(value = "criar um Produto")
	public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto, HttpServletResponse response) {
		Produto produtoSalvo = produtoService.save(produto);
		publisher.publishEvent(new RecursoCriado(this, response, produtoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}
	
	/**
	 * Atualizar Produto
	 */
	@PutMapping("/{code}")
	@ApiOperation(value = "Atualizar um Produto")
	public ResponseEntity<Produto> atualizar(@PathVariable Long code, @Valid @RequestBody Produto produto) {
		Produto produtoSalva = produtoService.atualizar(code, produto);
		return ResponseEntity.ok(produtoSalva);
	}
	

	/**
	 * Deletar Produto
	 */
	@DeleteMapping("/{code}")
	@ApiOperation(value = "Deletar um Produto")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long code) {
		produtoService.delete(code);
	}

}
