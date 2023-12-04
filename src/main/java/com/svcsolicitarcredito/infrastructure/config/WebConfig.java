package com.svcsolicitarcredito.infrastructure.config;
import com.amazonaws.xray.strategy.jakarta.SegmentNamingStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import jakarta.servlet.Filter;
import com.amazonaws.xray.jakarta.servlet.AWSXRayServletFilter;

@Configuration
public class WebConfig {

    @Bean
    public Filter TracingFilter() {
        return  new AWSXRayServletFilter(String.valueOf(SegmentNamingStrategy.dynamic("Scorekeep")));
    }
}