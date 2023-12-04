package com.svcsolicitarcredito.domain.port.in;

import com.svcsolicitarcredito.application.dto.PedidoCreditoDTO;
import com.svcsolicitarcredito.application.dto.PedidoCreditoListDTO;

import java.util.Date;
import java.util.List;

public interface SearchPedidosUseCase {


    // Lista todos os pedidos de crédito e retorna uma lista de DTOs dos pedidos
    List<PedidoCreditoListDTO> searchPedidos( String codigoCliente,
                                              String produto,
                                              Double valorMinimo,
                                              Double valorMaximo,
                                              Date dataInicio,
                                              Date dataFim,
                                              String status,
                                              String sortBy,
                                              Integer pageSize,
                                              Integer pageNumber);
}
