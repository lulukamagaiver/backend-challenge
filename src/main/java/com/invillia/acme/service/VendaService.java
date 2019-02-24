package com.invillia.acme.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Venda;
import com.invillia.acme.repository.Produtos;
import com.invillia.acme.repository.Vendas;
import com.invillia.acme.util.NegocioException;
import com.invillia.acme.util.RecursoNaoEncontradoException;

@Service
public class VendaService {

	@Autowired
	private Vendas vendas;

	@Autowired
	private Produtos produtos;

	public Venda adicionar(Venda venda) {
		venda.setCadastro(LocalDateTime.now());
		venda.getItens().forEach(i -> {
			i.setVenda(venda);
			i.setProduto(produtos.findById(i.getProduto().getId()).get());
		});

		BigDecimal totalItens = venda.getItens().stream()
				.map(i -> i.getProduto().getValor().multiply(new BigDecimal(i.getQuantidade())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		venda.setTotal(totalItens.add(venda.getFrete()));

		return this.vendas.save(venda);
	}

	public Venda buscarVendaPorCodigo(Long codigo) {
		try {
			Venda venda = this.vendas.findById(codigo).get();
			if (venda == null) {
				throw new EmptyResultDataAccessException(1);
			}
			return venda;
		} catch (NoSuchElementException | IllegalArgumentException e) {
			throw new RecursoNaoEncontradoException("Recurso não encontrado!");
		}
	}

	public List<Venda> listar() {
		return this.vendas.findAll();
	}

	public Venda atualizarVenda(Long id, Venda venda) {
		Venda vendaModify = this.buscarVendaPorCodigo(id);
		if (reembolso(vendaModify)) {
			BeanUtils.copyProperties(venda, vendaModify, "id");
			return this.adicionar(vendaModify);
		} else {
			throw new NegocioException("Venda acima de 10 dias, impossivel reembolsar!");
		}
	}

	private boolean reembolso(Venda venda) {
		long dias = venda.getConfirmacao().until(LocalDateTime.now(), ChronoUnit.DAYS);
		
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
	
	public void cancelarVenda(Long id) {
		Venda vendaCancelada = this.buscarVendaPorCodigo(id);
		if (reembolso(vendaCancelada)) {
			this.vendas.delete(vendaCancelada);
		} else {
			throw new NegocioException("Venda acima de 10 dias, impossivel reembolsar!");
		}
	}
}
