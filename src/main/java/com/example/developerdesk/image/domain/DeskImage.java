package com.example.developerdesk.image.domain;

import com.example.developerdesk.post.domain.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Builder
public class DeskImage {

    @Id
    @Column(name="desk_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deskImageId;

    private String url;

    @Setter
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public DeskImage() { }

    public DeskImage(Long deskImageId, String url, Post post) {
        this.deskImageId = deskImageId;
        this.url = url;
        this.post = post;
    }

}
