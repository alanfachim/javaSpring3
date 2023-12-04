package com.svcsolicitarcredito.domain.port.in;

import com.svcsolicitarcredito.application.dto.PedidoCreditoDTO;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;

import java.util.UUID;

public interface UpdatePedidoUseCase {

  // Cria um novo pedido de crédito com o produto informado e retorna o código do pedido
  PedidoCreditoDTO updatePedido(UUID codigo, PedidoCreditoDTO pedidoCreditoDTO);
 

}
