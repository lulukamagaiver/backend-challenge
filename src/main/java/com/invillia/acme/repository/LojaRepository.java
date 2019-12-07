package com.invillia.acme.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invillia.acme.model.Loja;
import com.invillia.acme.repository.helper.LojaHelper;



@Repository
public interface LojaRepository extends JpaRepository<Loja, Long>,LojaHelper {

	

}

