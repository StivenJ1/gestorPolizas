package com.pruebatecnica.gestorPolizas.adapter;

import com.pruebatecnica.gestorPolizas.port.CoreEventPort;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.Map;

@Service
public class CoreMockAdapter implements CoreEventPort {

    private final RestTemplate restTemplate;

    public CoreMockAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendEvent(Map<String, Object> event) {
        String url = "http://localhost:8080/core-mock/evento";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", "123456");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(event, headers);

        restTemplate.postForEntity(url, entity, Void.class);
    }
}