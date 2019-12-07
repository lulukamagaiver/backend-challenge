package com.invillia.acme.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.invillia.acme.filter.ClienteFilter;
import com.invillia.acme.model.Cliente;



public interface ClienteHelper {

	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable);

}
