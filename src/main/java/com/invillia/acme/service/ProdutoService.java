package com.invillia.acme.service;


import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Produto;
import com.invillia.acme.repository.ProdutoRepository;
import com.invillia.acme.filter.ProdutoFilter;

@Service
public class ProdutoService {

	private String errorDelete = "esse registro está relacionado a outras tabelas.";

    @Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Page<Produto> filter(ProdutoFilter produtoFilter, Pageable pageable) {
		return produtoRepository.filtrar(produtoFilter, pageable);
	}

	@Transactional
	public void delete(Long codigo) {
		try {
			produtoRepository.deleteById(codigo);
		} catch (Exception e) {
			if (e instanceof org.hibernate.exception.ConstraintViolationException
					|| e instanceof DataIntegrityViolationException) {
				throw new IllegalArgumentException(errorDelete);
			}
			throw e;
		}

	}
	
	public Produto buscarProdutoPeloCodigo(Long codigo) {
		Produto produtoSalva = produtoRepository.getOne(codigo);
		if (produtoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtoSalva;
	}
	
	public Produto atualizar(Long codigo, Produto produto) {
		Produto produtoSalva = buscarProdutoPeloCodigo(codigo);
		
		BeanUtils.copyProperties(produto, produtoSalva, "id");
		return produtoRepository.save(produtoSalva);
	}

}
