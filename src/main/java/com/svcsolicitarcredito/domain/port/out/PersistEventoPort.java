package com.svcsolicitarcredito.domain.port.out;

import com.svcsolicitarcredito.domain.entity.EventoPedidoCredito;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;

import java.util.List;
import java.util.UUID;

public interface PersistEventoPort {

    EventoPedidoCredito saveEvento(EventoPedidoCredito Evento);

    void deleteEvento(EventoPedidoCredito pedido);

  EventoPedidoCredito loadEventoByCode(SolicitarCreditoID codigo);

}
