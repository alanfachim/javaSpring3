package com.svcsolicitarcredito.domain.port.out;

import com.svcsolicitarcredito.domain.entity.EventoPedidoCredito;
import com.svcsolicitarcredito.domain.entity.ListaPedidoEventos;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;

import java.util.List;
import java.util.UUID;

public interface PersistPedidoPort {

  // Faz uma consulta avançada nos pedidos usando o critério de busca informado e retorna uma lista de pedidos
  PedidoCredito savePedido(PedidoCredito pedido);

    EventoPedidoCredito saveEvento(EventoPedidoCredito Evento);

    void deletePedido(PedidoCredito pedido);

    List<PedidoCredito> findByCodigoClienteAndCodigoProdutoNaoConcluidoUltimomes(String cpdigoCliente, String cpdigoProduto);

    // Faz uma consulta avançada nos pedidos usando o critério de busca informado e retorna uma lista de pedidos
    PedidoCredito loadPedidoByCode(SolicitarCreditoID codigo);
    List<PedidoCredito> loadAllPedidos();

    PedidoCredito savePedidoEvento(PedidoCredito pedido, EventoPedidoCredito evento) throws Exception;

  ListaPedidoEventos loadListaPedidoEventosByCode(SolicitarCreditoID solicitarCreditoID);
}
