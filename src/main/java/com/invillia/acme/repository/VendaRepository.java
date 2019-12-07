package com.invillia.acme.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invillia.acme.model.Venda;
import com.invillia.acme.repository.helper.VendaHelper;



@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>,VendaHelper {

	

}

