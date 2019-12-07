package com.invillia.acme.service;


import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Pagamento;
import com.invillia.acme.repository.PagamentoRepository;
import com.invillia.acme.enums.StatusPagamento;
import com.invillia.acme.filter.PagamentoFilter;

@Service
public class PagamentoService {

	private String errorDelete = "esse registro está relacionado a outras tabelas.";

    @Autowired
	private PagamentoRepository pagamentoRepository;

	@Transactional
	public Pagamento save(Pagamento pagamento) {
		return pagamentoRepository.save(pagamento);
	}
	
	public Page<Pagamento> filter(PagamentoFilter pagamentoFilter, Pageable pageable) {
		return pagamentoRepository.filtrar(pagamentoFilter, pageable);
	}

	@Transactional
	public void delete(Long codigo) {
		try {
			pagamentoRepository.deleteById(codigo);
		} catch (Exception e) {
			if (e instanceof org.hibernate.exception.ConstraintViolationException
					|| e instanceof DataIntegrityViolationException) {
				throw new IllegalArgumentException(errorDelete);
			}
			throw e;
		}

	}
	
	public Pagamento buscarPagamentoPeloCodigo(Long codigo) {
		Pagamento pagamentoSalva = pagamentoRepository.getOne(codigo);
		if (pagamentoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pagamentoSalva;
	}
	
	public Pagamento atualizar(Long codigo, Pagamento pagamento) {
		Pagamento pagamentoSalva = buscarPagamentoPeloCodigo(codigo);
		
		BeanUtils.copyProperties(pagamento, pagamentoSalva, "id");
		return pagamentoRepository.save(pagamentoSalva);
	}

	public void atualizarStatusPagamento(Long code, StatusPagamento stausPagamento) {
		Pagamento pagamentoSalva = buscarPagamentoPeloCodigo(code);
		pagamentoSalva.setStatusPagamento(stausPagamento);
		pagamentoRepository.save(pagamentoSalva);
	}

}
