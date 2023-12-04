package com.svcsolicitarcredito.application.dto;

import com.svcsolicitarcredito.domain.entity.ParecerOrigemPedido;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper( componentModel = "spring") // anotação do MapStruct para indicar que essa é uma classe de mapeamento
public interface ParecerOrigemPedidoCreditoMapper {

  // instância do mapper que pode ser usada para acessar os métodos de conversão
  ParecerOrigemPedidoCreditoMapper INSTANCE = Mappers.getMapper(ParecerOrigemPedidoCreditoMapper.class);
  // método para converter um objeto do tipo PedidoCredito em um objeto do tipo PedidoCreditoDTO

  ParecerOrigemPedidoDTO toParecerOrigemPedidoDTO(ParecerOrigemPedido pedidoCredito);

  // método para converter um objeto do tipo PedidoCreditoDTO em um objeto do tipo PedidoCredito
  ParecerOrigemPedido toParecerOrigemPedido(ParecerOrigemPedidoDTO pedidoCreditoDTO);
}

