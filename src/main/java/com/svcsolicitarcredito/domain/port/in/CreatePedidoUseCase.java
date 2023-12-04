package com.svcsolicitarcredito.domain.port.in;


import com.svcsolicitarcredito.application.dto.PedidoCreditoDTO;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;

import java.util.UUID;

public interface CreatePedidoUseCase {


  // Cria um novo pedido de crédito com o produto informado e retorna o DTO do pedido
  PedidoCreditoDTO createPedido(PedidoCreditoDTO pedidoCreditoDTO) throws Exception;

}
