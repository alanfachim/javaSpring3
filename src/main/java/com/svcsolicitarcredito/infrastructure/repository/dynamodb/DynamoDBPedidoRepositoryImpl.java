package com.svcsolicitarcredito.infrastructure.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.svcsolicitarcredito.domain.entity.EventoPedidoCredito;
import com.svcsolicitarcredito.domain.entity.ListaPedidoEventos;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DynamoDBPedidoRepositoryImpl implements DynamoDBPedidoRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB dynamoDB;

    @Override
    public PedidoCredito findPedidoById(SolicitarCreditoID codigo_pedido_credito) {
        return null;
    }

    @Override
    public ListaPedidoEventos findPedidoEventosById(SolicitarCreditoID codigo_pedido_credito) {
        AttributeValue liftPK = new AttributeValue(codigo_pedido_credito.getCodigoPedidoCredito().toString());
        QueryRequest queryRequest = new QueryRequest()
                .withTableName("Pedidos")
                .withKeyConditionExpression("codigo_pedido_credito = :v_pk")
                .withExpressionAttributeValues(Map.of(":v_pk", liftPK));
        QueryResult queryResult = dynamoDB.query(queryRequest);


        // Criar duas listas vazias, uma para armazenar os objetos do tipo PedidoCredito e outra para armazenar os objetos do tipo EventoPedidoCredito
        List<PedidoCredito> pedidos = new ArrayList<>();
        List<EventoPedidoCredito> eventos = new ArrayList<>();

        // Obter a lista de itens do QueryResult
        List<Map<String, AttributeValue>> items = queryResult.getItems();

        // Iterar sobre a lista de itens
        for (Map<String, AttributeValue> item : items) {
            // obter o valor do atributo qualifier como uma string
            String qualifier = item.get("chave_ordenacao").getS();

            // verificar se o item é do tipo PedidoCredito ou EventoPedidoCredito
            if (qualifier.equals("#PEDIDO")) {
                // o item é do tipo PedidoCredito
                // converter o item para o tipo PedidoCredito
                PedidoCredito pedido = dynamoDBMapper.marshallIntoObject(PedidoCredito.class, item);
                // adicionar o pedido à lista de pedidos
                pedidos.add(pedido);
            } else if (qualifier.startsWith("#EVENTO#")) {
                // o item é do tipo EventoPedidoCredito
                // converter o item para o tipo EventoPedidoCredito
                EventoPedidoCredito evento = dynamoDBMapper.marshallIntoObject(EventoPedidoCredito.class, item);
                // adicionar o evento à lista de eventos
                eventos.add(evento);
            }
        }
        return new ListaPedidoEventos(pedidos,eventos);
    }

    @Override
    public List<PedidoCredito> findByCodigoClienteAndCodigoProdutoAndFilter(final String codigoCliente, final String codigoProduto, final HashMap<String, AttributeValue> listInputFilter, final String filterExpression) {
        DynamoDBQueryExpression<PedidoCredito> queryExpression = new DynamoDBQueryExpression<PedidoCredito>();
        queryExpression.withIndexName("codigo_cliente_codigo_produto_index");
        queryExpression.withKeyConditionExpression("codigo_cliente = :codigoCliente and codigo_produto = :codigoProduto");

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":codigoCliente", new AttributeValue().withS(codigoCliente));
        eav.put(":codigoProduto", new AttributeValue().withS(codigoProduto));
        eav.putAll(listInputFilter);
        queryExpression.withFilterExpression(filterExpression);
        queryExpression.withExpressionAttributeValues(eav);
        queryExpression.setConsistentRead(false); // índices secundários globais só suportam consistência eventual
        List<PedidoCredito> queryResult = dynamoDBMapper.query(PedidoCredito.class, queryExpression);
        return queryResult;
    }


    @Override
    public <S extends PedidoCredito> S save(S entity) {
        dynamoDBMapper.save(entity);
        return entity;
    }

    @Override
    public <S extends PedidoCredito> Iterable<S> saveAll(Iterable<S> entities) {
        // implementação omitida por simplicidade
        return null;
    }

    @Override
    public Optional<PedidoCredito> findById(SolicitarCreditoID solicitarCreditoID) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(SolicitarCreditoID SolicitarCreditoID) {
        // implementação omitida por simplicidade
        return false;
    }

    @Override
    public Iterable<PedidoCredito> findAll() {
        // implementação omitida por simplicidade
        return null;
    }

    @Override
    public Iterable<PedidoCredito> findAllById(Iterable<SolicitarCreditoID> uuids) {
        // implementação omitida por simplicidade
        return null;
    }

    @Override
    public long count() {
        // implementação omitida por simplicidade
        return 0;
    }

    @Override
    public void deleteById(SolicitarCreditoID uuid) {
        // implementação omitida por simplicidade
    }

    @Override
    public void delete(PedidoCredito entity) {
        // implementação omitida por simplicidade
    }

    @Override
    public void deleteAllById(Iterable<? extends SolicitarCreditoID> SolicitarCreditoIDs) {
        //
    }

    @Override
    public void deleteAll(Iterable<? extends PedidoCredito> entities) {
        // implementação omitida por simplicidade
    }

    @Override
    public void deleteAll() {
        // implementação omitida por simplicidade
    }
}
