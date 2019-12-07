package com.invillia.acme.service;


import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Item;
import com.invillia.acme.repository.ItemRepository;
import com.invillia.acme.filter.ItemFilter;

@Service
public class ItemService {

	private String errorDelete = "esse registro está relacionado a outras tabelas.";

    @Autowired
	private ItemRepository itemRepository;

	@Transactional
	public Item save(Item item) {
		return itemRepository.save(item);
	}
	
	public Page<Item> filter(ItemFilter itemFilter, Pageable pageable) {
		return itemRepository.filtrar(itemFilter, pageable);
	}

	@Transactional
	public void delete(Long codigo) {
		try {
			itemRepository.deleteById(codigo);
		} catch (Exception e) {
			if (e instanceof org.hibernate.exception.ConstraintViolationException
					|| e instanceof DataIntegrityViolationException) {
				throw new IllegalArgumentException(errorDelete);
			}
			throw e;
		}

	}
	
	public Item buscarItemPeloCodigo(Long codigo) {
		Item itemSalva = itemRepository.getOne(codigo);
		if (itemSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return itemSalva;
	}
	
	public Item atualizar(Long codigo, Item item) {
		Item itemSalva = buscarItemPeloCodigo(codigo);
		
		BeanUtils.copyProperties(item, itemSalva, "id");
		return itemRepository.save(itemSalva);
	}

}
