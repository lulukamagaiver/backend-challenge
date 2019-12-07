package com.invillia.acme.service;


import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Loja;
import com.invillia.acme.repository.LojaRepository;
import com.invillia.acme.filter.LojaFilter;

@Service
public class LojaService {

	private String errorDelete = "esse registro está relacionado a outras tabelas.";

    @Autowired
	private LojaRepository lojaRepository;

	@Transactional
	public Loja save(Loja loja) {
		return lojaRepository.save(loja);
	}
	
	public Page<Loja> filter(LojaFilter lojaFilter, Pageable pageable) {
		return lojaRepository.filtrar(lojaFilter, pageable);
	}

	@Transactional
	public void delete(Long codigo) {
		try {
			lojaRepository.deleteById(codigo);
		} catch (Exception e) {
			if (e instanceof org.hibernate.exception.ConstraintViolationException
					|| e instanceof DataIntegrityViolationException) {
				throw new IllegalArgumentException(errorDelete);
			}
			throw e;
		}

	}
	
	public Loja buscarLojaPeloCodigo(Long codigo) {
		Loja lojaSalva = lojaRepository.getOne(codigo);
		if (lojaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lojaSalva;
	}
	
	public Loja atualizar(Long codigo, Loja loja) {
		Loja lojaSalva = buscarLojaPeloCodigo(codigo);
		
		BeanUtils.copyProperties(loja, lojaSalva, "id");
		return lojaRepository.save(lojaSalva);
	}

}
