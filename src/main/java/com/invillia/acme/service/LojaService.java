package com.invillia.acme.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Loja;
import com.invillia.acme.repository.Lojas;
import com.invillia.acme.util.RecursoNaoEncontradoException;

@Service
public class LojaService {
	
	@Autowired
	private Lojas lojas;
	
	public List<Loja> listar(){
		return this.lojas.findAll();
	}
	
	public Loja buscarPorCodigo(Long codigo) {
		try {
			Loja Loja = this.lojas.findById(codigo).get();
			if (Loja == null) {
				throw new EmptyResultDataAccessException(1);
			}	
			return Loja;
		} catch (NoSuchElementException | IllegalArgumentException e) {
			throw new RecursoNaoEncontradoException("Recurso não encontrado!");
		}
	}
	
	public Loja salvar(Loja loja) {
		return this.lojas.save(loja);
	}
	
	public Loja atualizar(Loja loja) {
		Loja lojaModify = this.buscarPorCodigo(loja.getId());
		BeanUtils.copyProperties(loja, lojaModify, "id");
		return this.salvar(lojaModify);
	}

}
