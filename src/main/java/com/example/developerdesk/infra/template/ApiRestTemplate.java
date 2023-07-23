package com.example.developerdesk.infra.template;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiRestTemplate implements Template {

    private final RestTemplate restTemplate;

    public ApiRestTemplate() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ResponseEntity<String> exchange(String url, HttpMethod method, HttpEntity<Object> entity, Class<String> responseType) {
        return restTemplate.exchange(url, method, entity, responseType);
    }

}
