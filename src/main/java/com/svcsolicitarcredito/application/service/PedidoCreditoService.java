package com.svcsolicitarcredito.application.service;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.svcsolicitarcredito.application.dto.*;
import com.svcsolicitarcredito.application.dto.exceptions.PedidoNotFoundException;
import com.svcsolicitarcredito.domain.entity.*;
import com.svcsolicitarcredito.domain.entity.enumerators.TipoEvento;
import com.svcsolicitarcredito.domain.port.in.*;
import com.svcsolicitarcredito.domain.port.out.PersistEventoPort;
import com.svcsolicitarcredito.domain.port.out.PersistPedidoPort;
import com.svcsolicitarcredito.domain.port.out.SearchPedidoPort;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@XRayEnabled
public class PedidoCreditoService implements CreatePedidoUseCase, DeletePedidoUseCase, GetPedidoUseCase, SearchPedidosUseCase, UpdatePedidoUseCase {


    @Autowired
    private PersistPedidoPort persistPedidoPort;

    @Autowired
    private PersistEventoPort persistEventoPort;

    @Autowired
    private SearchPedidoPort searchPedidoPort;
    @Autowired
    private PedidoCreditoMapper pedidoCreditoMapper;

    @Autowired
    private PedidoCreditoListMapper pedidoCreditoListMapper;

    @Autowired
    private ListaPedidoEventosMapper listaPedidoEventosMapper;



    // Cria um novo pedido de crédito com o produto informado e retorna o DTO do pedido
    @Override
    public PedidoCreditoDTO createPedido(PedidoCreditoDTO pedidoCreditoDTO) throws Exception {
        // Converte o DTO em entidade
        PedidoCredito Pedido = pedidoCreditoMapper.toPedidoCredito(pedidoCreditoDTO);
        List<PedidoCredito> pedisdosDoCliente = persistPedidoPort.findByCodigoClienteAndCodigoProdutoNaoConcluidoUltimomes(Pedido.getCodigoCliente(), Pedido.getCodigoProduto());
        EventoPedidoCredito Evento = new EventoPedidoCredito();
        if (!pedisdosDoCliente.isEmpty()) {
            Pedido.setCodigoPedidoCredito(pedisdosDoCliente.get(0).getCodigoPedidoCredito());
            Pedido.setQualifier("#PEDIDO");
            Evento.setTipoEvento(TipoEvento.EDITADO);
            Evento.setOrigemEvento("Sistema Solicitar Crédito");
        } else {
            Evento.setTipoEvento(TipoEvento.NOVO);
            Evento.setOrigemEvento("Sistema Solicitar Crédito");
            Pedido.setCodigoPedidoCredito(UUID.randomUUID());
            Pedido.setQualifier("#PEDIDO");
        }
        Evento.setCodigoPedidoCredito(Pedido.getCodigoPedidoCredito());
        Evento.setQualifier("#EVENTO#" + DateTime.now());
        // Persiste o pedido na fonte de dados
        PedidoCredito createdPedido = persistPedidoPort.savePedidoEvento(Pedido, Evento);

        //implementar a chamada do roteador
        // Converte a entidade em DTO e retorna
        return pedidoCreditoMapper.toPedidoCreditoDTO(createdPedido);
    }


    // Obtém um pedido de crédito pelo seu código e retorna o DTO do pedido
    @Override
    public ListaPedidoEventosDTO getPedido(UUID codigo) {
        // Carrega o pedido da fonte de dados pelo código
        ListaPedidoEventos Pedido = persistPedidoPort.loadListaPedidoEventosByCode(new SolicitarCreditoID(codigo));
        // Converte a entidade em DTO e retorna
        return listaPedidoEventosMapper.toListaPedidoEventosDTO(Pedido);
    }

    // Atualiza um pedido de crédito pelo seu código e retorna o DTO do pedido atualizado
    // Atualiza todos os atributos do pedido de crédito pelo seu código e retorna o DTO do pedido atualizado
    @Override
    public PedidoCreditoDTO updatePedido(UUID codigo, PedidoCreditoDTO pedidoCreditoDTO) {
        // Verifica se o código do pedido é válido
        if (codigo == null) {
            throw new IllegalArgumentException("O código do pedido não pode ser nulo");
        }

        // Verifica se o DTO do pedido é válido
        if (pedidoCreditoDTO == null) {
            throw new IllegalArgumentException("O DTO do pedido não pode ser nulo");
        }

        // Busca o pedido pelo seu código no repositório
        PedidoCredito pedido = persistPedidoPort.loadPedidoByCode(new SolicitarCreditoID(codigo, "#PEDIDO"));

        // Verifica se o pedido existe
        if (pedido == null) {
            throw new PedidoNotFoundException("O pedido com o código " + codigo + " não foi encontrado");
        }

        // Atualiza todos os atributos do pedido com os valores do DTO
        BeanUtils.copyProperties(pedidoCreditoDTO, pedido);

        // Persiste o pedido atualizado no repositório
        persistPedidoPort.savePedido(pedido);

        // Converte o pedido atualizado em um DTO
        PedidoCreditoDTO updatedPedido = pedidoCreditoMapper.toPedidoCreditoDTO(pedido);

        // Retorna o DTO do pedido atualizado
        return updatedPedido;
    }


    // Deleta um pedido de crédito pelo seu código
    @Override
    public void deletePedido(UUID codigo) {
        // Carrega o pedido da fonte de dados pelo código
        PedidoCredito Pedido = persistPedidoPort.loadPedidoByCode(new SolicitarCreditoID(codigo));
        // Deleta o pedido da fonte de dados
        persistPedidoPort.deletePedido(Pedido);
    }


    @Override
    // Faz uma consulta avançada nos pedidos usando vários critérios de filtragem e ordenação e retorna uma lista de DTOs dos pedidos
    public List<PedidoCreditoListDTO> searchPedidos(
            String codigoCliente,
            String produto,
            Double valorMinimo,
            Double valorMaximo,
            Date dataInicio,
            Date dataFim,
            String status,
            String sortBy,
            Integer pageSize,
            Integer pageNumber

    ) {
        // Cria um objeto de critério de busca com os parâmetros informados
        SearchCriteria searchCriteria = new SearchCriteria(codigoCliente, produto, valorMinimo, valorMaximo, dataInicio, dataFim, status, sortBy, pageSize, pageNumber);
        // Faz a consulta na fonte de dados usando o critério de busca
        SearchHits<ListaPedidoCredito> Pedidos = searchPedidoPort.searchPedidos(searchCriteria);

        // Converte as entidades em DTOs e retorna
        return null;
    }

}