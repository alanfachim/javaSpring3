package com.svcsolicitarcredito.infrastructure.adapter;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.svcsolicitarcredito.domain.entity.EventoPedidoCredito;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;
import com.svcsolicitarcredito.domain.port.out.PersistEventoPort;
import com.svcsolicitarcredito.domain.port.out.PersistPedidoPort;
import com.svcsolicitarcredito.infrastructure.repository.dynamodb.DynamoDBEventoPedidoRepository;
import com.svcsolicitarcredito.infrastructure.repository.dynamodb.DynamoDBPedidoRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class DynamoDBEventoAdapter implements PersistEventoPort {

  @Autowired
  DynamoDBEventoPedidoRepository dynamoDBEventoPedidoRepository;

  @Override
  public EventoPedidoCredito saveEvento(EventoPedidoCredito Evento) {
    return dynamoDBEventoPedidoRepository.save(Evento);
  }

  // Deleta um pedido do banco de dados
  @Override
  public void deleteEvento(EventoPedidoCredito Evento) {
    dynamoDBEventoPedidoRepository.delete(Evento);
  }

  @Override
  public EventoPedidoCredito loadEventoByCode(SolicitarCreditoID codigo) {
    return null;
  }


}
