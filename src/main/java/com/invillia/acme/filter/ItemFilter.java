package com.invillia.acme.filter;

import com.invillia.acme.model.Venda;
import com.invillia.acme.model.Produto;


public class ItemFilter {

private Long id;
private Venda venda;
private Produto produto;
private Integer quantidade;


public Long getId() {
  return this.id;
}
public void setId(Long id) {
  this.id = id;
}
public Venda getVenda() {
  return this.venda;
}
public void setVenda(Venda venda) {
  this.venda = venda;
}
public Produto getProduto() {
  return this.produto;
}
public void setProduto(Produto produto) {
  this.produto = produto;
}
public Integer getQuantidade() {
  return this.quantidade;
}
public void setQuantidade(Integer quantidade) {
  this.quantidade = quantidade;
}



}
