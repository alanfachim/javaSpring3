package com.svcsolicitarcredito.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@DynamoDBTable(tableName  = "Pedidos")
@Document(indexName = "pedidocredito")
@JsonIgnoreProperties(ignoreUnknown=true)
public class PedidoCredito {

  @Id
  private SolicitarCreditoID id;
  public void setId(String codigoPedidoCredito) {
    if (id == null) {
      id = new SolicitarCreditoID();
    }
    id.setCodigoPedidoCredito(UUID.fromString(codigoPedidoCredito));
  }

  @DynamoDBHashKey(attributeName = "codigo_pedido_credito")
  public UUID getCodigoPedidoCredito() {
    return this.id != null ? this.id.getCodigoPedidoCredito() : null;
  }

  @DynamoDBRangeKey(attributeName = "chave_ordenacao")
  public String getQualifier() {
    return this.id != null ? this.id.getQualifier() : null;
  }
  public void setCodigoPedidoCredito(UUID codigoPedidoCredito) {
    if (id == null) {
      id = new SolicitarCreditoID();
    }
    id.setCodigoPedidoCredito(codigoPedidoCredito);
  }

  public void setQualifier(String qualifier) {
    if (id == null) {
      id = new SolicitarCreditoID();
    }
    id.setQualifier(qualifier);
  }

  @DynamoDBAttribute(attributeName = "codigo_cliente")
  @DynamoDBIndexHashKey(globalSecondaryIndexName = "codigo_cliente_codigo_produto_index")
  private String codigoCliente; // o código do cliente

  private String agencia; // a agência do cliente

  private String conta; // a conta do cliente

  private String regiao; // a região do cliente

  private String dicom; // o dicom do cliente

  @DynamoDBAttribute(attributeName = "status_pedido")
  private String statusPedido; // o status do pedido de crédito

  private String segmentoBancario; // o segmento bancário do cliente

  private String codigoCanalSolicitacao; // o código do canal de solicitação do pedido de crédito

  private String descricaoCanalSolicitacao; // a descrição do canal de solicitação do pedido de crédito

  private Double valorPedido; // o valor do pedido de crédito

  private String unidadeMonetaria; // a unidade monetária do pedido de crédito

  private String nomeGrupo; // o nome do grupo do cliente

  private String dataPedido; // a data do pedido de crédito

  private Integer prazo; // o prazo do pedido de crédito

  private String unidadePrazo; // a unidade de prazo do pedido de crédito

  private String codigoIdentificacaoOrigem; // o código de identificação da origem do pedido de crédito

  private ParecerOrigemPedido parecerOrigemPedido; // o parecer da origem do pedido de crédito

  @DynamoDBAttribute(attributeName = "codigo_produto")
  @DynamoDBIndexRangeKey(globalSecondaryIndexName = "codigo_cliente_codigo_produto_index")
  private String codigoProduto; // o código do produto solicitado pelo cliente

  private String descricaoProduto; // a descrição do produto solicitado pelo cliente


  public String getCodigoCliente() {
    return codigoCliente;
  }

  public void setCodigoCliente(String codigoCliente) {
    this.codigoCliente = codigoCliente;
  }

  public String getAgencia() {
    return agencia;
  }

  public void setAgencia(String agencia) {
    this.agencia = agencia;
  }

  public String getConta() {
    return conta;
  }

  public void setConta(String conta) {
    this.conta = conta;
  }

  public String getRegiao() {
    return regiao;
  }

  public void setRegiao(String regiao) {
    this.regiao = regiao;
  }

  public String getDicom() {
    return dicom;
  }

  public void setDicom(String dicom) {
    this.dicom = dicom;
  }

  public String getStatusPedido() {
    return statusPedido;
  }

  public void setStatusPedido(String statusPedido) {
    this.statusPedido = statusPedido;
  }

  public String getSegmentoBancario() {
    return segmentoBancario;
  }

  public void setSegmentoBancario(String segmentoBancario) {
    this.segmentoBancario = segmentoBancario;
  }

  public String getCodigoCanalSolicitacao() {
    return codigoCanalSolicitacao;
  }

  public void setCodigoCanalSolicitacao(String codigoCanalSolicitacao) {
    this.codigoCanalSolicitacao = codigoCanalSolicitacao;
  }

  public String getDescricaoCanalSolicitacao() {
    return descricaoCanalSolicitacao;
  }

  public void setDescricaoCanalSolicitacao(String descricaoCanalSolicitacao) {
    this.descricaoCanalSolicitacao = descricaoCanalSolicitacao;
  }

  public Double getValorPedido() {
    return valorPedido;
  }

  public void setValorPedido(Double valorPedido) {
    this.valorPedido = valorPedido;
  }

  public String getUnidadeMonetaria() {
    return unidadeMonetaria;
  }

  public void setUnidadeMonetaria(String unidadeMonetaria) {
    this.unidadeMonetaria = unidadeMonetaria;
  }

  public String getNomeGrupo() {
    return nomeGrupo;
  }

  public void setNomeGrupo(String nomeGrupo) {
    this.nomeGrupo = nomeGrupo;
  }

  public String getDataPedido() {
    return dataPedido;
  }

  public void setDataPedido(String dataPedido) {
    this.dataPedido = dataPedido;
  }

  public Integer getPrazo() {
    return prazo;
  }

  public void setPrazo(Integer prazo) {
    this.prazo = prazo;
  }

  public String getUnidadePrazo() {
    return unidadePrazo;
  }

  public void setUnidadePrazo(String unidadePrazo) {
    this.unidadePrazo = unidadePrazo;
  }

  public String getCodigoIdentificacaoOrigem() {
    return codigoIdentificacaoOrigem;
  }

  public void setCodigoIdentificacaoOrigem(String codigoIdentificacaoOrigem) {
    this.codigoIdentificacaoOrigem = codigoIdentificacaoOrigem;
  }

  public ParecerOrigemPedido getParecerOrigemPedido() {
    return parecerOrigemPedido;
  }

  public void setParecerOrigemPedido(ParecerOrigemPedido parecerOrigemPedido) {
    this.parecerOrigemPedido = parecerOrigemPedido;
  }

  public String getCodigoProduto() {
    return codigoProduto;
  }

  public void setCodigoProduto(String codigoProduto) {
    this.codigoProduto = codigoProduto;
  }

  public String getDescricaoProduto() {
    return descricaoProduto;
  }

  public void setDescricaoProduto(String descricaoProduto) {
    this.descricaoProduto = descricaoProduto;
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

