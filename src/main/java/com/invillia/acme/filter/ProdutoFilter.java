package com.invillia.acme.filter;

import java.math.BigDecimal;


public class ProdutoFilter {

private Long id;
private String nome;
private BigDecimal valor;


public Long getId() {
  return this.id;
}
public void setId(Long id) {
  this.id = id;
}
public String getNome() {
  return this.nome;
}
public void setNome(String nome) {
  this.nome = nome;
}
public BigDecimal getValor() {
  return this.valor;
}
public void setValor(BigDecimal valor) {
  this.valor = valor;
}



}
