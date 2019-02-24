package com.invillia.acme.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.model.Produto;
import com.invillia.acme.repository.Produtos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/produtos")
@Api(value = "API Rest Produtos")
public class ProdutosResource {

	@Autowired
	private Produtos produtos;

	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Produtos")
	public List<Produto> listar() {
		return produtos.findAll();
	}

}
