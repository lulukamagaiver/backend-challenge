package com.invillia.acme.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invillia.acme.model.Produto;
import com.invillia.acme.repository.helper.ProdutoHelper;



@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>,ProdutoHelper {

	

}

