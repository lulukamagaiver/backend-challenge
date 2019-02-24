package com.invillia.acme.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.model.Cliente;
import com.invillia.acme.repository.Clientes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
@Api(value = "API Rest Clientes")
public class ClienteResource {

	@Autowired
	private Clientes clientes;

	@GetMapping
	@ApiOperation(value = "Retorna uma Lista de Clientes")
	public List<Cliente> listar() {
		return clientes.findAll();
	}

}
