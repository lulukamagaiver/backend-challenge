package com.invillia.acme.filter;

import java.time.LocalDateTime;
import com.invillia.acme.enums.StatusPagamento;


public class PagamentoFilter {

private Long id;
private String numeroCartao;
private LocalDateTime dataPagamento;
private StatusPagamento statusPagamento;


public Long getId() {
  return this.id;
}
public void setId(Long id) {
  this.id = id;
}
public String getNumeroCartao() {
  return this.numeroCartao;
}
public void setNumeroCartao(String numeroCartao) {
  this.numeroCartao = numeroCartao;
}
public LocalDateTime getDataPagamento() {
  return this.dataPagamento;
}
public void setDataPagamento(LocalDateTime dataPagamento) {
  this.dataPagamento = dataPagamento;
}
public StatusPagamento getStatusPagamento() {
  return this.statusPagamento;
}
public void setStatusPagamento(StatusPagamento statusPagamento) {
  this.statusPagamento = statusPagamento;
}



}
