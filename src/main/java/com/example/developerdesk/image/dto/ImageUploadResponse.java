package com.example.developerdesk.image.dto;

import lombok.Builder;

@Builder
public class ImageUploadResponse {

    private String id;
    private String imageUrl;

    public ImageUploadResponse() { }

    public ImageUploadResponse(String id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
