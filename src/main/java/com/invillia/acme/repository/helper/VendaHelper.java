package com.invillia.acme.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.invillia.acme.filter.VendaFilter;
import com.invillia.acme.model.Venda;



public interface VendaHelper {

	public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);

}
