package com.svcsolicitarcredito.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreditoListDTO {


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

  public UUID getCodigoPedidoCredito() {
    return codigoPedidoCredito;
  }

  public void setCodigoPedidoCredito(UUID codigoPedidoCredito) {
    this.codigoPedidoCredito = codigoPedidoCredito;
  }

  @JsonProperty(value = "codigo_pedido_credito")
  private UUID codigoPedidoCredito; // o código do pedido de crédito

  @JsonProperty(value ="codigo_cliente")
  private String codigoCliente; // o código do cliente

  @JsonProperty(value ="agencia")
  private String agencia; // a agência do cliente

  @JsonProperty(value ="conta")
  private String conta; // a conta do cliente

  @JsonProperty(value ="regiao")
  private String regiao; // a região do cliente

  @JsonProperty(value ="dicom")
  private String dicom; // o dicom do cliente

  @JsonProperty(value ="status_pedido")
  private String statusPedido; // o status do pedido de crédito

  @JsonProperty(value ="segmento_bancario")
  private String segmentoBancario; // o segmento bancário do cliente

  @JsonProperty(value ="codigo_canal_solicitacao")
  private String codigoCanalSolicitacao; // o código do canal de solicitação do pedido de crédito

  @JsonProperty(value ="descricao_canal_solicitacao")
  private String descricaoCanalSolicitacao; // a descrição do canal de solicitação do pedido de crédito

  @JsonProperty(value ="valor_pedido")
  private Double valorPedido; // o valor do pedido de crédito


  @JsonProperty(value ="unidade_monetaria")
  private String unidadeMonetaria; // a unidade monetária do pedido de crédito

  @JsonProperty(value ="nome_grupo")
  private String nomeGrupo; // o nome do grupo do cliente

  @JsonProperty(value ="data_pedido")
  private String dataPedido; // a data do pedido de crédito

  @JsonProperty(value ="prazo")
  private Integer prazo; // o prazo do pedido de crédito

  @JsonProperty(value ="unidade_prazo")
  private String unidadePrazo; // a unidade de prazo do pedido de crédito

  @JsonProperty(value ="codigo_identificacao_origem")
  private String codigoIdentificacaoOrigem; // o código de identificação da origem do pedido de crédito


  @JsonProperty(value ="codigo_produto")
  private String codigoProduto; // o código do produto solicitado pelo cliente

  @JsonProperty(value ="descricao_produto")
  private String descricaoProduto; // a descrição do produto solicitado pelo cliente
}