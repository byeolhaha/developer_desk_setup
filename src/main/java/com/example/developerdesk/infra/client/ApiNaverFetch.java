package com.example.developerdesk.infra.client;

import com.example.developerdesk.infra.template.Template;
import com.example.developerdesk.equipment.api.ApiShopItem;
import com.example.developerdesk.equipment.api.ItemAdapter;
import com.example.developerdesk.infra.dto.NaverApiShopItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;

@Slf4j
@Component
public class ApiNaverFetch {

    private final Template template;
    private final ItemAdapter itemAdapter;

    public ApiNaverFetch(Template template, ItemAdapter itemAdapter) {
        this.template = template;
        this.itemAdapter = itemAdapter;
    }

    @Value("${api.shop.url}")
    private String apiUrl;

    @Value("${api.shop.id}")
    private String serviceId;

    @Value("${api.shop.secret}")
    private String serviceKey;

    public List<ApiShopItem> searchResult(String query) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Naver-Client-Id", serviceId);
        httpHeaders.add("X-Naver-Client-Secret", serviceKey);

        String url = String.format("%s?query=%s", apiUrl, query);

        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.GET,
                new HttpEntity<>(httpHeaders), String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            JsonNode itemNode = jsonNode.get("items");

            return objectMapper.readValue(itemNode.toString(),
                    new TypeReference<List<NaverApiShopItem>>() {
                    }).stream().map(v -> itemAdapter.toItem(v)).collect(
                    Collectors.toList());

        } catch (JsonProcessingException e) {
            log.error("Error while parsing JSON response: {}", e.getMessage());
        }
        return Collections.emptyList();
    }

}
