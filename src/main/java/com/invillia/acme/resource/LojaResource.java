package com.invillia.acme.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.model.Loja;
import com.invillia.acme.repository.Lojas;
import com.invillia.acme.service.LojaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/lojas")
@Api(value = "API Rest Lojas")
public class LojaResource {
	
	@Autowired
	private LojaService lojaService;
	
	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Lojas")
	public List<Loja> listar(){
		return this.lojaService.listar();
	}
	
	@PostMapping
	@ApiOperation(value = "Criar uma Loja")
	public Loja adicionarLoja(@Valid @RequestBody Loja loja) {
		return this.lojaService.salvar(loja);
	}
	
	@GetMapping("/{codigo}")
	@ApiOperation(value = "Buscar uma Loja por código")
	public Loja buscarPorCodigo(@PathVariable Long codigo) {
		return this.lojaService.buscarPorCodigo(codigo);
	}
	
	@PutMapping("/{codigo}")
	@ApiOperation(value = "Atualizar uma Loja por código")
	public Loja modificar(@Valid @RequestBody Loja loja) {
		return this.lojaService.atualizar(loja);
	}

}
