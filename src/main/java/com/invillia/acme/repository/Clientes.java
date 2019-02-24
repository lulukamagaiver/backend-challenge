package com.invillia.acme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invillia.acme.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long>{

}
