package com.svcsolicitarcredito.application.dto;

import com.svcsolicitarcredito.domain.entity.ListaPedidoCredito;
import com.svcsolicitarcredito.domain.entity.ParecerOrigemPedido;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import com.svcsolicitarcredito.domain.entity.SolicitarCreditoID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.UUID;

@Component
@Mapper( componentModel = "spring") // anotação do MapStruct para indicar que essa é uma classe de mapeamento
public interface PedidoCreditoMapper {

  // instância do mapper que pode ser usada para acessar os métodos de conversão
  PedidoCreditoMapper INSTANCE = Mappers.getMapper(PedidoCreditoMapper.class);
  // método para converter um objeto do tipo PedidoCredito em um objeto do tipo PedidoCreditoDTO
  default ParecerOrigemPedidoDTO ParecerOrigemPedidoToParecerOrigemPedidoDto(ParecerOrigemPedido parecer) {
    return Mappers.getMapper(ParecerOrigemPedidoCreditoMapper.class).toParecerOrigemPedidoDTO(parecer);
  }
  default ParecerOrigemPedido ParecerOrigemPedidoDtoToParecerOrigemPedido(ParecerOrigemPedidoDTO parecer) {
    return Mappers.getMapper(ParecerOrigemPedidoCreditoMapper.class).toParecerOrigemPedido(parecer);
  }



  PedidoCreditoDTO toPedidoCreditoDTO(PedidoCredito pedidoCredito);



  // método para converter um objeto do tipo PedidoCreditoDTO em um objeto do tipo PedidoCredito

  PedidoCredito toPedidoCredito(PedidoCreditoDTO pedidoCreditoDTO);

}

