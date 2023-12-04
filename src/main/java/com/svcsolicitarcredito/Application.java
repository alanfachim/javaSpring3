package com.svcsolicitarcredito;

import org.opensearch.spring.boot.autoconfigure.data.OpenSearchDataAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan; 

import javax.net.ssl.SSLContext;
// Definir a classe principal da aplicação com as anotações @SpringBootApplication e @EnableDynamoDBRepositories
@SpringBootApplication(exclude = {ElasticsearchDataAutoConfiguration.class, OpenSearchDataAutoConfiguration.class})
@ComponentScan(basePackages = { "com.svcsolicitarcredito" })
public class Application {

    // Definir o método main que inicia a aplicação usando o SpringApplication.run
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}