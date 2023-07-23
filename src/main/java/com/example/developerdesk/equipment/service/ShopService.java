package com.example.developerdesk.equipment.service;

import com.example.developerdesk.equipment.api.ApiShopItem;
import com.example.developerdesk.infra.client.ApiNaverFetch;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    private final ApiNaverFetch apiFetch;

    public ShopService(ApiNaverFetch apiFetch) {
        this.apiFetch = apiFetch;
    }

    public List<ApiShopItem> get(String query) {
        return apiFetch.searchResult(query);
    }
}
