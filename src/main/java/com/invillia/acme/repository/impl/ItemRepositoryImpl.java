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

import com.invillia.acme.repository.helper.ItemHelper;
import com.invillia.acme.filter.ItemFilter;
import com.invillia.acme.model.Item;


public class ItemRepositoryImpl implements ItemHelper {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Item> filtrar(ItemFilter  itemFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
		Root<Item> root = criteria.from(Item.class);
		
		Predicate[] predicates = criarRestrincoes(itemFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Item> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(itemFilter));
	}
	
	
	private Predicate[] criarRestrincoes(ItemFilter  itemFilter, CriteriaBuilder builder, Root<Item> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (itemFilter != null) {
			
                      if (itemFilter.getId() != null) {
				predicates.add(builder.equal(root.get("id"), itemFilter.getId()));
			}

            if (itemFilter.getVenda() != null) {
				predicates.add(builder.equal(root.get("venda"), itemFilter.getVenda()));
			}

            if (itemFilter.getProduto() != null) {
				predicates.add(builder.equal(root.get("produto"), itemFilter.getProduto()));
			}

            if (itemFilter.getQuantidade() != null) {
				predicates.add(builder.equal(root.get("quantidade"), itemFilter.getQuantidade()));
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
	
	private Long total(ItemFilter  itemFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Item> root = criteria.from(Item.class);
		
		Predicate[] predicates = criarRestrincoes(itemFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
