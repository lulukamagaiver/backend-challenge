package com.invillia.acme.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invillia.acme.model.Pagamento;
import com.invillia.acme.repository.helper.PagamentoHelper;



@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>,PagamentoHelper {

	

}

