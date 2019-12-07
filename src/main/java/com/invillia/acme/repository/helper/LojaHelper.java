package com.invillia.acme.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.invillia.acme.filter.LojaFilter;
import com.invillia.acme.model.Loja;



public interface LojaHelper {

	public Page<Loja> filtrar(LojaFilter lojaFilter, Pageable pageable);

}
