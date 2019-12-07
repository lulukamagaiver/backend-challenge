package com.invillia.acme.service;


import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Cliente;
import com.invillia.acme.repository.ClienteRepository;
import com.invillia.acme.filter.ClienteFilter;

@Service
public class ClienteService {

	private String errorDelete = "esse registro está relacionado a outras tabelas.";

    @Autowired
	private ClienteRepository clienteRepository;

	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Page<Cliente> filter(ClienteFilter clienteFilter, Pageable pageable) {
		return clienteRepository.filtrar(clienteFilter, pageable);
	}

	@Transactional
	public void delete(Long codigo) {
		try {
			clienteRepository.deleteById(codigo);
		} catch (Exception e) {
			if (e instanceof org.hibernate.exception.ConstraintViolationException
					|| e instanceof DataIntegrityViolationException) {
				throw new IllegalArgumentException(errorDelete);
			}
			throw e;
		}

	}
	
	public Cliente buscarClientePeloCodigo(Long codigo) {
		Cliente clienteSalva = clienteRepository.getOne(codigo);
		if (clienteSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return clienteSalva;
	}
	
	public Cliente atualizar(Long codigo, Cliente cliente) {
		Cliente clienteSalva = buscarClientePeloCodigo(codigo);
		
		BeanUtils.copyProperties(cliente, clienteSalva, "id");
		return clienteRepository.save(clienteSalva);
	}

}
