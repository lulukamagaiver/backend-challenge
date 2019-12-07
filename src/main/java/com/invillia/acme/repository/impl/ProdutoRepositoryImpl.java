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

import com.invillia.acme.repository.helper.ProdutoHelper;
import com.invillia.acme.filter.ProdutoFilter;
import com.invillia.acme.model.Produto;


public class ProdutoRepositoryImpl implements ProdutoHelper {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Produto> filtrar(ProdutoFilter  produtoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		Predicate[] predicates = criarRestrincoes(produtoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Produto> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(produtoFilter));
	}
	
	
	private Predicate[] criarRestrincoes(ProdutoFilter  produtoFilter, CriteriaBuilder builder, Root<Produto> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (produtoFilter != null) {
			
                      if (produtoFilter.getId() != null) {
				predicates.add(builder.equal(root.get("id"), produtoFilter.getId()));
			}

           if (!StringUtils.isEmpty(produtoFilter.getNome())) {				
				predicates.add(builder.like(builder.lower(root.get("nome")),"%" + produtoFilter.getNome().toLowerCase() + "%"));
			}
			

            if (produtoFilter.getValor() != null) {
				predicates.add(builder.equal(root.get("valor"), produtoFilter.getValor()));
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
	
	private Long total(ProdutoFilter  produtoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		Predicate[] predicates = criarRestrincoes(produtoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
