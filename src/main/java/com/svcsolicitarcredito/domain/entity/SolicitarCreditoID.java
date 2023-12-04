package com.svcsolicitarcredito.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import java.util.UUID;
@DynamoDBDocument
public class SolicitarCreditoID {

  @DynamoDBHashKey(attributeName  = "codigo_pedido_credito")
  private UUID codigoPedidoCredito; // o código do pedido de crédito

  @DynamoDBRangeKey(attributeName = "chave_ordenacao")
  private String qualifier; // #PEDIDO, #EVENTO#2020-01-01T01:01:01.00000

  public SolicitarCreditoID(UUID uuid, String qualifier) {
    this.codigoPedidoCredito=uuid;
    this.qualifier=qualifier;
  }

  public SolicitarCreditoID(UUID uuid) {
    this.codigoPedidoCredito=uuid;
  }

  public SolicitarCreditoID() {

  }

  public UUID getCodigoPedidoCredito() {
    return codigoPedidoCredito;
  }

  public void setCodigoPedidoCredito(UUID codigoPedidoCredito) {
    this.codigoPedidoCredito = codigoPedidoCredito;
  }

  public String getQualifier() {
    return qualifier;
  }

  public void setQualifier(String qualifier) {
    this.qualifier = qualifier;
  }
}

