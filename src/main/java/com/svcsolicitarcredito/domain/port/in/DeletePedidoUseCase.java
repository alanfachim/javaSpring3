package com.svcsolicitarcredito.domain.port.in;

import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;

import java.util.UUID;

public interface DeletePedidoUseCase {


  // Deleta um pedido de crédito pelo seu código
  void deletePedido(UUID codigo);
}
