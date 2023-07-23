package com.example.developerdesk.equipment.api;

import com.example.developerdesk.infra.dto.NaverApiShopItem;
import org.springframework.stereotype.Component;

@Component
public class ItemAdapter {

    private ItemAdapter() { }

    public ApiShopItem toItem(NaverApiShopItem item) {
        return ApiShopItem.builder()
                .price(item.getLprice())
                .title(item.getTitle())
                .maker(item.getMaker())
                .image(item.getImage())
                .link(item.getLink())
                .build();
    }

}
