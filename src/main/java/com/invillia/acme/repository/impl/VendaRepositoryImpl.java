package com.invillia.acme.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.invillia.acme.repository.helper.VendaHelper;
import com.invillia.acme.filter.VendaFilter;
import com.invillia.acme.model.Venda;


public class VendaRepositoryImpl implements VendaHelper {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Venda> filtrar(VendaFilter  vendaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Venda> criteria = builder.createQuery(Venda.class);
		Root<Venda> root = criteria.from(Venda.class);
		
		Predicate[] predicates = criarRestrincoes(vendaFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Venda> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(vendaFilter));
	}
	
	
	private Predicate[] criarRestrincoes(VendaFilter  vendaFilter, CriteriaBuilder builder, Root<Venda> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (vendaFilter != null) {
			
                      if (vendaFilter.getId() != null) {
				predicates.add(builder.equal(root.get("id"), vendaFilter.getId()));
			}

            if (vendaFilter.getDataVenda() != null) {
				predicates.add(builder.equal(root.get("dataVenda"), vendaFilter.getDataVenda()));
			}

            if (vendaFilter.getConfirmacao() != null) {
				predicates.add(builder.equal(root.get("confirmacao"), vendaFilter.getConfirmacao()));
			}

            if (vendaFilter.getCliente() != null) {
				predicates.add(builder.equal(root.get("cliente"), vendaFilter.getCliente()));
			}

            if (vendaFilter.getFrete() != null) {
				predicates.add(builder.equal(root.get("frete"), vendaFilter.getFrete()));
			}

            if (vendaFilter.getTotal() != null) {
				predicates.add(builder.equal(root.get("total"), vendaFilter.getTotal()));
			}

            if (vendaFilter.getPagamento() != null) {
				predicates.add(builder.equal(root.get("pagamento"), vendaFilter.getPagamento()));
			}

            if (vendaFilter.getEnderecoEntrega() != null) {
				predicates.add(builder.equal(root.get("enderecoEntrega"), vendaFilter.getEnderecoEntrega()));
			}

            if (vendaFilter.getStatusVenda() != null) {
				predicates.add(builder.equal(root.get("statusVenda"), vendaFilter.getStatusVenda()));
			}



		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(VendaFilter  vendaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Venda> root = criteria.from(Venda.class);
		
		Predicate[] predicates = criarRestrincoes(vendaFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
