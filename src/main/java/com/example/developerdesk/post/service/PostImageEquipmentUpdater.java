package com.example.developerdesk.post.service;

import com.example.developerdesk.equipment.domain.Equipment;
import com.example.developerdesk.image.domain.DeskImage;
import com.example.developerdesk.post.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class PostImageEquipmentUpdater {
    private PostImageEquipmentUpdater() { }

    public Post start(Post post) {

        for (DeskImage image : post.getImages()) {
            image.setPost(post);
        }

        for (Equipment equipment : post.getEquipments()) {
            equipment.setPost(post);
        }

        return post;

    }
}
