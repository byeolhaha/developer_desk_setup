package com.example.developerdesk.equipment.api;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiShopItem {

    private String title;
    private String link;
    private String image;
    private Integer price;
    private String maker;

    public ApiShopItem(String title, String link, String image, Integer price, String maker) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.price = price;
        this.maker = maker;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }

    public String getMaker() {
        return maker;
    }
}
