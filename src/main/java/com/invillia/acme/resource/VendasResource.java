package com.invillia.acme.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.invillia.acme.model.Venda;
import com.invillia.acme.service.VendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/vendas")
@Api(value = "API Rest Vendas")
public class VendasResource {


	@Autowired
	private VendaService vendaService;

	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Vendas")
	public List<Venda> listar() {
		return this.vendaService.listar();
	}

	@PostMapping
	@ApiOperation(value = "Adiciona uma Venda")
	public Venda adicionar(@RequestBody @Valid Venda venda) {
		return this.vendaService.adicionar(venda);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Atrualizar uma Venda")
	public Venda alterarVenda(@PathVariable Long id, @Valid @RequestBody Venda venda) {
		return this.vendaService.atualizarVenda(id,venda);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar uma Venda")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelarVenda(@PathVariable Long id) {
		this.vendaService.cancelarVenda(id);
	}

}
