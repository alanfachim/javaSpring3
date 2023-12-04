package com.svcsolicitarcredito.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.svcsolicitarcredito.domain.entity.EventoPedidoCredito;
import com.svcsolicitarcredito.domain.entity.PedidoCredito;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListaPedidoEventosDTO {// uma lista de objetos do tipo PedidoCredito
  private List<PedidoCredito> pedidos;

  // uma lista de objetos do tipo EventoPedidoCredito
  private List<EventoPedidoCredito> eventos;

  // um construtor que recebe as listas como parâmetros
  public ListaPedidoEventosDTO(List<PedidoCredito> pedidos, List<EventoPedidoCredito> eventos) {
    // atribuir os valores dos parâmetros aos atributos
    this.pedidos = pedidos;
    this.eventos = eventos;
  }

  // um método que retorna a lista de pedidos
  public List<PedidoCredito> getPedidos() {
    // retornar o valor do atributo pedidos
    return pedidos;
  }

  // um método que retorna a lista de eventos
  public List<EventoPedidoCredito> getEventos() {
    // retornar o valor do atributo eventos
    return eventos;
  }
}