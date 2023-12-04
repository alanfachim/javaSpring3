package com.svcsolicitarcredito.application.dto;

import com.svcsolicitarcredito.domain.entity.ListaPedidoCredito;
import com.svcsolicitarcredito.domain.entity.ParecerOrigemPedido;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper( componentModel = "spring") // anotação do MapStruct para indicar que essa é uma classe de mapeamento
public interface PedidoCreditoListMapper {

  // instância do mapper que pode ser usada para acessar os métodos de conversão
  PedidoCreditoListMapper INSTANCE = Mappers.getMapper(PedidoCreditoListMapper.class);


  PedidoCreditoListDTO toPedidoCreditoListDTO(PedidoCredito pedidoCredito);
  PedidoCreditoListDTO toListaPedidoCreditoDTO(ListaPedidoCredito pedidoCredito);
  // método para converter um objeto do tipo PedidoCreditoDTO em um objeto do tipo PedidoCredito

  PedidoCredito toPedidoCredito(PedidoCreditoListDTO pedidoCreditoListDTO);
}

