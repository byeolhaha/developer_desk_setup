package com.example.developerdesk.equipment.controller;

import com.example.developerdesk.equipment.service.ShopService;
import com.example.developerdesk.equipment.api.ApiShopItem;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class EquipmentController {

    private final ShopService shopService;

    public EquipmentController(ShopService service) {
        this.shopService = service;
    }

    @GetMapping("/equipment")
    public ResponseEntity<List<ApiShopItem>> showSearchResults(@RequestParam String query) {
        List<ApiShopItem> apiShopItems = shopService.get(query);

        return ResponseEntity.ok(apiShopItems);
    }
}

