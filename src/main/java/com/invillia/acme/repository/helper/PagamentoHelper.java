package com.invillia.acme.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.invillia.acme.filter.PagamentoFilter;
import com.invillia.acme.model.Pagamento;



public interface PagamentoHelper {

	public Page<Pagamento> filtrar(PagamentoFilter pagamentoFilter, Pageable pageable);

}
