package com.invillia.acme.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.invillia.acme.filter.ProdutoFilter;
import com.invillia.acme.model.Produto;



public interface ProdutoHelper {

	public Page<Produto> filtrar(ProdutoFilter produtoFilter, Pageable pageable);

}
