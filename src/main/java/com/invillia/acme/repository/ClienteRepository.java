package com.invillia.acme.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invillia.acme.model.Cliente;
import com.invillia.acme.repository.helper.ClienteHelper;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>,ClienteHelper {

	

}

