package com.example.developerdesk.post.service;

import com.example.developerdesk.equipment.api.ApiShopItem;
import com.example.developerdesk.equipment.domain.Equipment;
import com.example.developerdesk.image.domain.DeskImage;
import com.example.developerdesk.post.controller.dto.PostCreateRequest;
import com.example.developerdesk.post.controller.dto.PostUpdateRequest;
import com.example.developerdesk.post.domain.Post;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostEntityConverter {

    private PostEntityConverter() { }

    Post toEntity(PostCreateRequest postCreateRequest) {
        return Post.builder()
                .contents(postCreateRequest.getContents())
                .title(postCreateRequest.getTitle())
                .equipments(
                        postCreateRequest.getEquipments()
                                .stream()
                                .map(e -> toEntity(e))
                                .collect(Collectors.toList()))
                .totalPrice(
                        postCreateRequest.getEquipments().stream()
                                .map(e->toEntity(e))
                                .map(e->e.getPrice())
                                .reduce(0,Integer::sum)
                )
                .images(
                        postCreateRequest.getImages()
                                .stream()
                                .map(url -> toEntity(url))
                                .collect(Collectors.toList()))
                .build();

    }


    Post toEntity(PostUpdateRequest postUpdateRequest) {
        return Post.builder()
                .contents(postUpdateRequest.getContents())
                .title(postUpdateRequest.getTitle())
                .equipments(
                        postUpdateRequest.getEquipments()
                                .stream()
                                .map(e -> toEntity(e))
                                .collect(Collectors.toList()))
                .images(
                        postUpdateRequest.getImages()
                                .stream()
                                .map(url -> toEntity(url))
                                .collect(Collectors.toList()))
                .build();
    }



    Equipment toEntity(ApiShopItem apiShopItem) {
        return Equipment.builder()
                .title(apiShopItem.getTitle())
                .price(apiShopItem.getPrice())
                .maker(apiShopItem.getMaker())
                .brand(apiShopItem.getMaker())
                .link(apiShopItem.getLink())
                .build();
    }

    DeskImage toEntity(String url) {
        return DeskImage.builder()
                .url(url)
                .build();
    }

}
