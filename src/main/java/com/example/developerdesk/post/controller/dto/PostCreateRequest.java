package com.example.developerdesk.post.controller.dto;

import com.example.developerdesk.equipment.api.ApiShopItem;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {

    private String title;
    private String contents;
    private List<String> images;
    private List<ApiShopItem> equipments;

}
