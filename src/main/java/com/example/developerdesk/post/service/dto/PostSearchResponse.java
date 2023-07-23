package com.example.developerdesk.post.service.dto;

import com.example.developerdesk.image.domain.DeskImage;
import com.example.developerdesk.post.domain.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSearchResponse {

    private Long id;
    private String title;
    private String contents;
    private List<String> equipments;
    private Integer totalPrice;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int view;

    public static PostSearchResponse of(Post post) {
        return PostSearchResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .equipments(post.getEquipments().stream().map(v -> v.getTitle())
                        .collect(Collectors.toList()))
                .totalPrice(post.getTotalPrice())
                .images(post.getImages().stream().map(DeskImage::getUrl)
                        .collect(Collectors.toList()))
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();

    }

}
