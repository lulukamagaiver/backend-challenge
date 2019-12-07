package com.invillia.acme.filter;

import java.time.LocalDateTime;
import java.time.LocalDate;
import com.invillia.acme.model.Cliente;
import java.util.List;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.invillia.acme.model.Pagamento;
import com.invillia.acme.model.Endereco;
import com.invillia.acme.enums.StatusVenda;


public class VendaFilter {

private Long id;
private LocalDateTime dataVenda;
private LocalDate confirmacao;
private Cliente cliente;
private BigDecimal frete;
private BigDecimal total;
private Pagamento pagamento;
private Endereco enderecoEntrega;
private StatusVenda statusVenda;


public Long getId() {
  return this.id;
}
public void setId(Long id) {
  this.id = id;
}
public LocalDateTime getDataVenda() {
  return this.dataVenda;
}
public void setDataVenda(LocalDateTime dataVenda) {
  this.dataVenda = dataVenda;
}
public LocalDate getConfirmacao() {
  return this.confirmacao;
}
public void setConfirmacao(LocalDate confirmacao) {
  this.confirmacao = confirmacao;
}
public Cliente getCliente() {
  return this.cliente;
}
public void setCliente(Cliente cliente) {
  this.cliente = cliente;
}
public BigDecimal getFrete() {
  return this.frete;
}
public void setFrete(BigDecimal frete) {
  this.frete = frete;
}
public BigDecimal getTotal() {
  return this.total;
}
public void setTotal(BigDecimal total) {
  this.total = total;
}
public Pagamento getPagamento() {
  return this.pagamento;
}
public void setPagamento(Pagamento pagamento) {
  this.pagamento = pagamento;
}
public Endereco getEnderecoEntrega() {
  return this.enderecoEntrega;
}
public void setEnderecoEntrega(Endereco enderecoEntrega) {
  this.enderecoEntrega = enderecoEntrega;
}
public StatusVenda getStatusVenda() {
  return this.statusVenda;
}
public void setStatusVenda(StatusVenda statusVenda) {
  this.statusVenda = statusVenda;
}



}
