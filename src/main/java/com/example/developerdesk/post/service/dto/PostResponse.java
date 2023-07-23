package com.example.developerdesk.post.service.dto;

import com.example.developerdesk.image.domain.DeskImage;
import com.example.developerdesk.post.domain.Post;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private Long id;

    private String title;

    private String contents;

    private List<String> equipments;

    private List<String> images;

    private int view;

    private Integer totalPrice;

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .equipments(post.getEquipments().stream().map(v -> v.getTitle())
                        .collect(Collectors.toList()))
                .images(post.getImages().stream().map(DeskImage::getUrl)
                        .collect(Collectors.toList()))
                .view(post.getView())
                .totalPrice(post.getTotalPrice())
                .build();
    }

}
