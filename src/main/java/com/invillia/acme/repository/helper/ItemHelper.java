package com.invillia.acme.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.invillia.acme.filter.ItemFilter;
import com.invillia.acme.model.Item;



public interface ItemHelper {

	public Page<Item> filtrar(ItemFilter itemFilter, Pageable pageable);

}
