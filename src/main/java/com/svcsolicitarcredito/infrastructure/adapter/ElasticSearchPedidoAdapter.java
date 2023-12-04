package com.svcsolicitarcredito.infrastructure.adapter;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Entity;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.Subsegment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.svcsolicitarcredito.domain.entity.ListaPedidoCredito;
import com.svcsolicitarcredito.domain.entity.SearchCriteria;
import com.svcsolicitarcredito.domain.port.out.SearchPedidoPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ElasticSearchPedidoAdapter implements SearchPedidoPort {
    // Adiciona a anotação @Autowired para injetar o objeto elasticsearchOperations
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    @Override
    public SearchHits<ListaPedidoCredito> searchPedidos(SearchCriteria searchCriteria) {
        // Cria um objeto de critério inicial
        Criteria criteria = new Criteria();
        // Adiciona as condições ao critério, se informadas
        if (searchCriteria.getCodigoCliente() != null) {
            criteria = criteria.and("codigo_cliente").is(searchCriteria.getCodigoCliente());
        }
        if (searchCriteria.getProduto() != null) {
            criteria = criteria.and("produto").is(searchCriteria.getProduto());
        }
        if (searchCriteria.getValorMinimo() != null) {
            criteria = criteria.and("valor").greaterThanEqual(searchCriteria.getValorMinimo());
        }
        if (searchCriteria.getValorMaximo() != null) {
            criteria = criteria.and("valor").lessThanEqual(searchCriteria.getValorMaximo());
        }
        if (searchCriteria.getDataInicio() != null) {
            criteria = criteria.and("data_pedido").greaterThanEqual(searchCriteria.getDataInicio());
        }
        if (searchCriteria.getDataFim() != null) {
            criteria = criteria.and("data_pedido").lessThanEqual(searchCriteria.getDataFim());
        }
        if (searchCriteria.getStatus() != null) {
            criteria = criteria.and("status").is(searchCriteria.getStatus());
        }
        // Cria um objeto de consulta com o critério
        var query = new CriteriaQuery(criteria);
        // Executa a consulta usando o cliente do Spring Data Elasticsearch e obtém os resultados
        // Obter o segmento atual do X-Ray
        Segment segment = AWSXRay.getCurrentSegment();
        assert segment != null;


        Subsegment sub = AWSXRay.beginSubsegment("OpenSearch-SolicitarCredito");
        SearchHits<ListaPedidoCredito> pedidos;
        try {
            pedidos = elasticsearchOperations.search(query, ListaPedidoCredito.class);
        } catch (Exception ex) {
            sub.addException(ex);
            sub.end();
            throw ex;
        }

        // Criar um mapa com os dados de entrada e saída
        Map<String, Object> annotations = new HashMap<>();
        annotations.put("searchCriteria", searchCriteria.toJson()); // O objeto com os critérios de busca
        annotations.put("pedidos", converteEmJson(pedidos)); // O objeto com os resultados da consulta
        // Adicionar as anotações ao subsegmento
        sub.setAnnotations(annotations);
        sub.end();
        return pedidos;
    }


    public String converteEmJson(SearchHits<ListaPedidoCredito> resultado) {
        // Cria um objeto Gson
        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

        // Cria um novo HashMap<String, Object> para armazenar os atributos do resultado
        HashMap<String, Object> novoResultado = new HashMap<String, Object>();

        // Adiciona os atributos do resultado no novoResultado
        novoResultado.put("totalHits", resultado.getTotalHits());
        novoResultado.put("totalHitsRelation", resultado.getTotalHitsRelation());
        novoResultado.put("maxScore", resultado.getMaxScore());

        // Cria uma lista vazia de SearchHit<ListaPedidoCredito>
        ArrayList<SearchHit<ListaPedidoCredito>> lista = new ArrayList<SearchHit<ListaPedidoCredito>>();

        // Verifica se a lista de searchHits do resultado não está vazia
        if (!resultado.getSearchHits().isEmpty()) {
            // Adiciona o primeiro elemento da lista de searchHits do resultado na lista
            lista.add(resultado.getSearchHits().get(0));
        }

        // Adiciona a lista no novoResultado com a chave "searchHits"
        novoResultado.put("searchHits", lista);

        // Converte o novoResultado em uma string JSON e retorna
        return gson.toJson(novoResultado);
    }
}
