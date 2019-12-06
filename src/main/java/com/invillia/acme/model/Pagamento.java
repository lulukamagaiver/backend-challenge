package com.invillia.acme.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.invillia.acme.enums.StatusPagamento;

/**
 * @author Marcus Vinicius
 * 6 de dez de 2019
 */
@Entity
@Table(name = "pagamento")
public class Pagamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -453380393191583895L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String numeroCartao;
	
	private LocalDateTime dataPagamento;
	
	@Column(name = "status_pagamento")
    @Enumerated(EnumType.STRING)
	private StatusPagamento statusPagamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}
	
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}
	
	public void setStatusPagamento(StatusPagamento statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
