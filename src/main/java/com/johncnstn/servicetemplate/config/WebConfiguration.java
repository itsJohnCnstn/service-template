package com.johncnstn.servicetemplate.config;

import com.johncnstn.servicetemplate.exception.RestTemplateErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Configuration
public class WebConfiguration {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder, RestTemplateErrorHandler errorHandler) {

        return restTemplateBuilder.errorHandler(errorHandler).build();
    }
}

