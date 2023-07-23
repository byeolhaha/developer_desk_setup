package com.example.developerdesk.infra.client;

import com.example.developerdesk.equipment.api.ApiShopItem;
import com.example.developerdesk.equipment.service.ShopService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"api", "aws"})
@SpringBootTest
class ApiNaverFetchServiceTest {

    @Autowired
    private ShopService service;

    @Test
    void searchResult() {
       List<ApiShopItem> list=  service.get("앱코 키");
       list.stream().forEach(v-> System.out.println(v.getImage()));
    }
}
