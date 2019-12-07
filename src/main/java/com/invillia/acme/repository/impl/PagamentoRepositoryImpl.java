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

import com.invillia.acme.repository.helper.PagamentoHelper;
import com.invillia.acme.filter.PagamentoFilter;
import com.invillia.acme.model.Pagamento;


public class PagamentoRepositoryImpl implements PagamentoHelper {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Pagamento> filtrar(PagamentoFilter  pagamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pagamento> criteria = builder.createQuery(Pagamento.class);
		Root<Pagamento> root = criteria.from(Pagamento.class);
		
		Predicate[] predicates = criarRestrincoes(pagamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Pagamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(pagamentoFilter));
	}
	
	
	private Predicate[] criarRestrincoes(PagamentoFilter  pagamentoFilter, CriteriaBuilder builder, Root<Pagamento> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (pagamentoFilter != null) {
			
                      if (pagamentoFilter.getId() != null) {
				predicates.add(builder.equal(root.get("id"), pagamentoFilter.getId()));
			}

           if (!StringUtils.isEmpty(pagamentoFilter.getNumeroCartao())) {				
				predicates.add(builder.like(builder.lower(root.get("numeroCartao")),"%" + pagamentoFilter.getNumeroCartao().toLowerCase() + "%"));
			}
			

            if (pagamentoFilter.getDataPagamento() != null) {
				predicates.add(builder.equal(root.get("dataPagamento"), pagamentoFilter.getDataPagamento()));
			}

            if (pagamentoFilter.getStatusPagamento() != null) {
				predicates.add(builder.equal(root.get("statusPagamento"), pagamentoFilter.getStatusPagamento()));
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
	
	private Long total(PagamentoFilter  pagamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pagamento> root = criteria.from(Pagamento.class);
		
		Predicate[] predicates = criarRestrincoes(pagamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
