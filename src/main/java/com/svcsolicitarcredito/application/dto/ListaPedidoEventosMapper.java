package com.svcsolicitarcredito.application.dto;

import com.svcsolicitarcredito.domain.entity.ListaPedidoEventos;
import com.svcsolicitarcredito.domain.entity.ParecerOrigemPedido;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper( componentModel = "spring") // anotação do MapStruct para indicar que essa é uma classe de mapeamento
public interface ListaPedidoEventosMapper {

  // instância do mapper que pode ser usada para acessar os métodos de conversão
  ListaPedidoEventosMapper INSTANCE = Mappers.getMapper(ListaPedidoEventosMapper.class);

  ListaPedidoEventosDTO toListaPedidoEventosDTO(ListaPedidoEventos listaPedidoEventos);

  // método para converter um objeto do tipo PedidoCreditoDTO em um objeto do tipo PedidoCredito

  ListaPedidoEventos toListaPedidoEventos(ListaPedidoEventosDTO listaPedidoEventosDTO);
}

