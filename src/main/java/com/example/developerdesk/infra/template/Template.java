package com.example.developerdesk.infra.template;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface Template {

    ResponseEntity<String> exchange(String url, HttpMethod get, HttpEntity<Object> objectHttpEntity, Class<String> stringClass);

}

