package com.invillia.acme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invillia.acme.model.Produto;

public interface Produtos extends JpaRepository<Produto, Long> {

}
