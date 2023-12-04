package com.svcsolicitarcredito.infrastructure.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.svcsolicitarcredito.domain.entity.EventoPedidoCredito;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EnableScan
public interface DynamoDBEventoPedidoRepository extends CrudRepository<EventoPedidoCredito, SolicitarCreditoID> {
   // Optional<EventoPedidoCredito> findById(SolicitarCreditoID codigo_pedido_credito);
}
