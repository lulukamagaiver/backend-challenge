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

import com.invillia.acme.repository.helper.LojaHelper;
import com.invillia.acme.filter.LojaFilter;
import com.invillia.acme.model.Loja;


public class LojaRepositoryImpl implements LojaHelper {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Loja> filtrar(LojaFilter  lojaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Loja> criteria = builder.createQuery(Loja.class);
		Root<Loja> root = criteria.from(Loja.class);
		
		Predicate[] predicates = criarRestrincoes(lojaFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Loja> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lojaFilter));
	}
	
	
	private Predicate[] criarRestrincoes(LojaFilter  lojaFilter, CriteriaBuilder builder, Root<Loja> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (lojaFilter != null) {
			
                      if (lojaFilter.getId() != null) {
				predicates.add(builder.equal(root.get("id"), lojaFilter.getId()));
			}

           if (!StringUtils.isEmpty(lojaFilter.getRazaoSocial())) {				
				predicates.add(builder.like(builder.lower(root.get("razaoSocial")),"%" + lojaFilter.getRazaoSocial().toLowerCase() + "%"));
			}
			

           if (!StringUtils.isEmpty(lojaFilter.getIe())) {				
				predicates.add(builder.like(builder.lower(root.get("ie")),"%" + lojaFilter.getIe().toLowerCase() + "%"));
			}
			

           if (!StringUtils.isEmpty(lojaFilter.getCnpj())) {				
				predicates.add(builder.like(builder.lower(root.get("cnpj")),"%" + lojaFilter.getCnpj().toLowerCase() + "%"));
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
	
	private Long total(LojaFilter  lojaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Loja> root = criteria.from(Loja.class);
		
		Predicate[] predicates = criarRestrincoes(lojaFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
