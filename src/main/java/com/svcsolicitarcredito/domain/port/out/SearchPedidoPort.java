package com.svcsolicitarcredito.domain.port.out;

import com.svcsolicitarcredito.domain.entity.ListaPedidoCredito;
import com.svcsolicitarcredito.domain.entity.SearchCriteria;
import org.springframework.data.elasticsearch.core.SearchHits;

public interface SearchPedidoPort {

  // Faz uma consulta avançada nos pedidos usando o critério de busca informado e retorna uma lista de pedidos
  SearchHits<ListaPedidoCredito> searchPedidos(SearchCriteria searchCriteria);

}
