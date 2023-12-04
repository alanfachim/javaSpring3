package com.svcsolicitarcredito.infrastructure.config;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.http.apache.client.impl.SdkHttpClient;
import com.amazonaws.regions.Region;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.client.OpenSearchClient;
import org.opensearch.client.RestHighLevelClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;


import org.opensearch.data.client.orhlc.AbstractOpenSearchConfiguration;
import org.opensearch.data.client.orhlc.ClientConfiguration;
import org.opensearch.data.client.orhlc.RestClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;

import java.time.Duration;

@Configuration
public class RestClientConfig extends AbstractOpenSearchConfiguration {

    @Value("${opensearch.endpoint}")
    private String opensearchEndpoint;

    @Value("#{'${os.alwed-path}'.split(',')}")
    private List<String> PATHS;

    @Value("#{${os.alwed-parameters}}")  private Map<String,String> parametros;


    @Override
    @Bean
    public RestHighLevelClient opensearchClient() {


        AWS4Signer signer = new AWS4Signer();
        String serviceName = "es";
        signer.setServiceName(serviceName);
        signer.setRegionName("sa-east-1");

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(opensearchEndpoint)
                //.usingSsl()
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(5))
                .withClientConfigurer(RestClients.RestClientConfigurationCallback.from(httpAsyncClientBuilder -> {
                    httpAsyncClientBuilder.addInterceptorLast(new AWSRequestSigningApacheInterceptor("se",signer,new DefaultAWSCredentialsProviderChain(), PATHS, parametros)); // Adicione esta linha para adicionar o interceptor de resposta
                    httpAsyncClientBuilder.addInterceptorFirst(new OpenSearchResponseInterceptor());
                    httpAsyncClientBuilder.addInterceptorFirst(new OpenSearchRequestInterceptor());
                    return httpAsyncClientBuilder;
                })).build();
        return RestClients.create(clientConfiguration).rest();
    }
}
