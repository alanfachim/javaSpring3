package com.svcsolicitarcredito.infrastructure.adapter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.svcsolicitarcredito.domain.entity.EventoPedidoCredito;
import com.svcsolicitarcredito.domain.entity.ListaPedidoEventos;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;
import com.svcsolicitarcredito.domain.port.out.PersistPedidoPort;
import com.svcsolicitarcredito.infrastructure.repository.dynamodb.DynamoDBEventoPedidoRepository;
import com.svcsolicitarcredito.infrastructure.repository.dynamodb.DynamoDBPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DynamoDBPedidoAdapter implements PersistPedidoPort {

  @Autowired
  DynamoDBPedidoRepository dynamoDBPedidoRepository;
  @Autowired
  private DynamoDBMapper dynamoDBMapper;
  @Autowired
  DynamoDBEventoPedidoRepository dynamoDBEventoPedidoRepository;

  // Salva um pedido no banco de dados e retorna o pedido salvo
  @Override
  public PedidoCredito savePedido(PedidoCredito Pedido) {
    return dynamoDBPedidoRepository.save(Pedido);
  }

  @Override
  public EventoPedidoCredito saveEvento(EventoPedidoCredito Evento) {
    return dynamoDBEventoPedidoRepository.save(Evento);
  }

  // Deleta um pedido do banco de dados
  @Override
  public void deletePedido(PedidoCredito Pedido) {
    dynamoDBPedidoRepository.delete(Pedido);
  }

  @Override
  public List<PedidoCredito> findByCodigoClienteAndCodigoProdutoNaoConcluidoUltimomes(String cpdigoCliente, String cpdigoProduto) {
    String filterExpression = "status_pedido <> :statusPedido";
    HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
    eav.put(":statusPedido", new AttributeValue().withS("concluido"));
    return dynamoDBPedidoRepository.findByCodigoClienteAndCodigoProdutoAndFilter(cpdigoCliente,cpdigoProduto , eav, filterExpression );
  }

  @Override
  public PedidoCredito loadPedidoByCode(SolicitarCreditoID id) {
    return dynamoDBPedidoRepository.findPedidoById(id);
  }

  @Override
  public ListaPedidoEventos loadListaPedidoEventosByCode(SolicitarCreditoID id) {
    return dynamoDBPedidoRepository.findPedidoEventosById(id);
  }

  // Busca todos os pedidos de crédito no banco de dados e retorna uma lista de pedidos
  @Override
  public List<PedidoCredito> loadAllPedidos() {
    // Usa o repositório do DynamoDB para buscar todos os pedidos
    Iterable<PedidoCredito> pedidos = dynamoDBPedidoRepository.findAll();

    // Converte o iterável em uma lista
    List<PedidoCredito> pedidosList = new ArrayList<>();
    pedidos.forEach(pedidosList::add);

    // Retorna a lista de pedidos
    return pedidosList;
  }

  @Override
  public PedidoCredito savePedidoEvento(PedidoCredito pedido, EventoPedidoCredito evento) throws Exception {

    // Cria uma lista de objetos que quer persistir no DynamoDB
    List<Object> items = new ArrayList<>();
    items.add(pedido);
    items.add(evento);

    // Persiste a lista de objetos no DynamoDB em um único pedido
    List<DynamoDBMapper.FailedBatch> failedBatches = dynamoDBMapper.batchSave(items);
    if (failedBatches.isEmpty()) {
      return pedido;
    } else {
      // Adicione as seguintes linhas para imprimir o erro
      for (DynamoDBMapper.FailedBatch failedBatch : failedBatches) {
        Exception e = failedBatch.getException(); // Obtém a exceção do lote
        String errorMessage = e.getMessage(); // Obtém a mensagem de erro da exceção
        System.out.println(errorMessage); // Imprime a mensagem de erro na saída padrão
        // Ou use um logger para gravar a mensagem de erro em um arquivo de log
        // logger.error(errorMessage);
      }
      throw new Exception("dfsdf");
    }
  }


}
