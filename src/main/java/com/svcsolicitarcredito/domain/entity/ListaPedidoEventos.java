package com.svcsolicitarcredito.domain.entity;

import java.util.List;

public class ListaPedidoEventos {
    // uma lista de objetos do tipo PedidoCredito
    private List<PedidoCredito> pedidos;

    // uma lista de objetos do tipo EventoPedidoCredito
    private List<EventoPedidoCredito> eventos;

    // um construtor que recebe as listas como parâmetros
    public ListaPedidoEventos(List<PedidoCredito> pedidos, List<EventoPedidoCredito> eventos) {
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
