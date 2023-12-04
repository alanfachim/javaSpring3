package com.svcsolicitarcredito.domain.entity;

import lombok.*;

import java.util.Date;
import java.util.HashMap;
import com.google.gson.Gson;

@Data
@Getter
@Setter
@NoArgsConstructor
public class SearchCriteria {
  private String codigoCliente; // o CPF do cliente
  private String produto; // o produto solicitado pelo cliente
  private Double valorMinimo; // o valor mínimo do pedido
  private Double valorMaximo; // o valor máximo do pedido
  private Date dataInicio;
  private Date dataFim;
  private String status; // o status do pedido
  private String sortBy; // o campo pelo qual ordenar os resultados
  private Integer pageSize;
  private Integer pageNumber;

  public SearchCriteria(String codigoCliente, String produto, Double valorMinimo, Double valorMaximo, Date dataInicio, Date dataFim, String status, String sortBy, Integer pageSize, Integer pageNumber) {
    this.codigoCliente = codigoCliente;
    this.produto = produto;
    this.valorMinimo = valorMinimo;
    this.valorMaximo = valorMaximo;
    this.dataInicio = dataInicio;
    this.dataFim = dataFim;
    this.status = status;
    this.sortBy = sortBy;
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
  }

  public String getCodigoCliente() {
    return codigoCliente;
  }

  public String getProduto() {
    return produto;
  }

  public Double getValorMinimo() {
    return valorMinimo;
  }

  public Double getValorMaximo() {
    return valorMaximo;
  }

  public Date getDataInicio() {
    return dataInicio;
  }

  public Date getDataFim() {
    return dataFim;
  }

  public String getStatus() {
    return status;
  }

  public String getSortBy() {
    return sortBy;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }


  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
  public String toJson() {
    return this.toString();
  }
  public HashMap toHashMap() {
    Gson gson = new Gson();
    return gson.fromJson(gson.toJson(this), HashMap.class);
  }
}
