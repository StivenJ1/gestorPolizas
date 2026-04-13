package com.pruebatecnica.gestorPolizas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@Component
public class CoreClient {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}