package com.invillia.acme.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.invillia.acme.enums.StatusVenda;
import com.invillia.acme.filter.VendaFilter;
import com.invillia.acme.model.Produto;
import com.invillia.acme.model.Venda;
import com.invillia.acme.repository.VendaRepository;
import com.invillia.acme.util.NegocioException;
import com.invillia.acme.util.RecursoNaoEncontradoException;

@Service
public class VendaService {

	private String errorDelete = "esse registro está relacionado a outras tabelas.";

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ProdutoService produtoService;

	@Transactional
	public Venda save(Venda venda) {
		
		venda.getPagamento().setDataPagamento(LocalDate.now());
		venda.setDataVenda(LocalDate.now());
		venda.getItens().forEach(i -> {
			i.setVenda(venda);
			
			Produto prodVenda = produtoService.buscarProdutoPeloCodigo(i.getProduto().getId());
			
			i.setProduto(prodVenda);
		});

		BigDecimal totalItens = venda.getItens().stream()
				.map(i -> i.getProduto().getValor().multiply(new BigDecimal(i.getQuantidade())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		venda.setTotal(totalItens.add(venda.getFrete()));

		return vendaRepository.save(venda);
	}

	public Page<Venda> filter(VendaFilter vendaFilter, Pageable pageable) {
		return vendaRepository.filtrar(vendaFilter, pageable);
	}

	@Transactional
	public void delete(Long codigo) {
		try {
			Venda vendaCancelada = this.buscarVendaPeloCodigo(codigo);
			if (reembolso(vendaCancelada)) {
				vendaRepository.deleteById(vendaCancelada.getId());
			} else {
				throw new NegocioException("Venda acima de 10 dias, impossivel reembolsar!");
			}

		} catch (Exception e) {
			if (e instanceof org.hibernate.exception.ConstraintViolationException
					|| e instanceof DataIntegrityViolationException) {
				throw new IllegalArgumentException(errorDelete);
			}
			throw e;
		}

	}

	public Venda buscarVendaPeloCodigo(Long codigo) {
		Venda vendaSalva = vendaRepository.getOne(codigo);
		if (vendaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendaSalva;
	}

	public Venda atualizar(Long codigo, Venda venda) {
		Venda vendaSalva = buscarVendaPeloCodigo(codigo);

		if (reembolso(vendaSalva)) {
			BeanUtils.copyProperties(venda, vendaSalva, "id");
			return vendaRepository.save(vendaSalva);
		} else {
			throw new NegocioException("Venda acima de 10 dias, impossivel reembolsar!");
		}
	}

	private boolean reembolso(Venda venda) {
		long dias = venda.getDataVenda().until(LocalDate.now(), ChronoUnit.DAYS);

		try {
			if (dias > 10) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			throw new RecursoNaoEncontradoException("Recurso não encontrado!");
		}

	}

	public void atualizarStatusVenda(Long code, StatusVenda statusVenda) {
		Venda vendaSalva = buscarVendaPeloCodigo(code);
		vendaSalva.setStatusVenda(statusVenda);
		vendaRepository.save(vendaSalva);
	}

}
