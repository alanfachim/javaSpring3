package com.svcsolicitarcredito.application.controller;

import com.svcsolicitarcredito.application.dto.ListaPedidoEventosDTO;
import com.svcsolicitarcredito.application.dto.PedidoCreditoDTO;
import com.svcsolicitarcredito.application.dto.PedidoCreditoListDTO;
import com.svcsolicitarcredito.application.service.PedidoCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Controller
@RequestMapping("/pedidos")
public class PedidoCreditoController {

  @Autowired
  private PedidoCreditoService pedidoCreditoService;

  // Cria um novo pedido de crédito com o produto informado
  @PostMapping
  public ResponseEntity<PedidoCreditoDTO> createPedido(@RequestBody PedidoCreditoDTO pedidoCreditoDTO) throws Exception {
    PedidoCreditoDTO createdPedido = pedidoCreditoService.createPedido(pedidoCreditoDTO);
    return new ResponseEntity<>(createdPedido, HttpStatus.CREATED);
  }

  // Obtém um pedido de crédito pelo seu código e todos os eventos do pedido
  @GetMapping("/{codigo}")
  public ResponseEntity<ListaPedidoEventosDTO> getPedido(@PathVariable UUID codigo) {
    ListaPedidoEventosDTO listaPedidoEventosDTO = pedidoCreditoService.getPedido(codigo);
    return new ResponseEntity<>(listaPedidoEventosDTO, HttpStatus.OK);
  }

  // Atualiza um pedido de crédito pelo seu código
  @PutMapping("/{codigo}")
  public ResponseEntity<PedidoCreditoDTO> updatePedido(@PathVariable UUID codigo,  @RequestBody PedidoCreditoDTO pedidoCreditoDTO) {
    PedidoCreditoDTO updatedPedido = pedidoCreditoService.updatePedido(codigo, pedidoCreditoDTO);
    return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
  }

  // Deleta um pedido de crédito pelo seu código
  @DeleteMapping("/{codigo}")
  public ResponseEntity<Void> deletePedido(@PathVariable UUID codigo) {
    pedidoCreditoService.deletePedido(codigo);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  // Faz uma consulta avançada nos pedidos usando vários critérios de filtragem e ordenação
  @GetMapping
  public ResponseEntity<List<PedidoCreditoListDTO>> searchPedidos(
      @RequestParam(required = false) String codigoCliente,
      @RequestParam(required = false) String produto,
      @RequestParam(required = false) Double valorMinimo,
      @RequestParam(required = false) Double valorMaximo,
      @RequestParam(required = false) Date dataInicio,
      @RequestParam(required = false) Date dataFim,
      @RequestParam(required = false) String status,
      @RequestParam(defaultValue = "codigo_pedido_credito") String sortBy,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "0") Integer pageNumber
  ) {
    List<PedidoCreditoListDTO> pedidos = pedidoCreditoService.searchPedidos( codigoCliente, produto, valorMinimo, valorMaximo,dataInicio,dataFim, status, sortBy, pageSize, pageNumber);
    return new ResponseEntity<>(pedidos, HttpStatus.OK);
  }
}
