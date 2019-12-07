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

import com.invillia.acme.enums.StatusPagamento;
import com.invillia.acme.event.RecursoCriado;
import com.invillia.acme.filter.PagamentoFilter;
import com.invillia.acme.model.Pagamento;
import com.invillia.acme.service.PagamentoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/pagamento")
@Api(value = "API Rest PagamentoResource")
public class PagamentoResource {
	
	@Autowired
	private PagamentoService pagamentoService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * Pesquisar Pagamento
	 */
	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Pagamento")
	public Page<Pagamento> search(PagamentoFilter pagamentoFilter, Pageable pageable) {
		return pagamentoService.filter(pagamentoFilter, pageable);
	}
	
	/**
	 * Buscar pelo codigo o Pagamento
	 */
	@GetMapping("/{code}")
	@ApiOperation(value = "Retorna um Pagamento pelo Codigo")
	public ResponseEntity<Pagamento> buscarPeloCodigo(@PathVariable Long code) {
		Pagamento pagamento = pagamentoService.buscarPagamentoPeloCodigo(code);
		return pagamento != null ? ResponseEntity.ok(pagamento) : ResponseEntity.notFound().build();
	}
	
	/**
	 * Criar Pagamento
	 */
	@PostMapping
	@ApiOperation(value = "criar um Pagamento")
	public ResponseEntity<Pagamento> criar(@Valid @RequestBody Pagamento pagamento, HttpServletResponse response) {
		Pagamento pagamentoSalvo = pagamentoService.save(pagamento);
		publisher.publishEvent(new RecursoCriado(this, response, pagamentoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoSalvo);
	}
	
	/**
	 * Atualizar Pagamento
	 */
	@PutMapping("/{code}")
	@ApiOperation(value = "Atualizar um Pagamento")
	public ResponseEntity<Pagamento> atualizar(@PathVariable Long code, @Valid @RequestBody Pagamento pagamento) {
		Pagamento pagamentoSalva = pagamentoService.atualizar(code, pagamento);
		return ResponseEntity.ok(pagamentoSalva);
	}
	
	/**
	 * Atualizar status do Pagamento
	 */
	@PutMapping("/{code}/statusPagamento")
	@ApiOperation(value = "Atualizar Status de um Pagamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatusPagamento(@PathVariable Long code, @RequestBody StatusPagamento stausPagamento) {
		pagamentoService.atualizarStatusPagamento(code, stausPagamento);
	}
	

	/**
	 * Deletar Pagamento
	 */
	@DeleteMapping("/{code}")
	@ApiOperation(value = "Deletar um Pagamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long code) {
		pagamentoService.delete(code);
	}

}
