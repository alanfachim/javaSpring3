package com.svcsolicitarcredito.infrastructure.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.svcsolicitarcredito.domain.entity.ListaPedidoEventos;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DynamoDBPedidoRepository extends CrudRepository<PedidoCredito, SolicitarCreditoID> {

    PedidoCredito findPedidoById(SolicitarCreditoID codigo_pedido_credito);

    ListaPedidoEventos findPedidoEventosById(SolicitarCreditoID codigo_pedido_credito);


    List<PedidoCredito> findByCodigoClienteAndCodigoProdutoAndFilter(final String codigoCliente, final String codigoProduto, final HashMap<String, AttributeValue> listInputFilter, final String filterExpression);

}
