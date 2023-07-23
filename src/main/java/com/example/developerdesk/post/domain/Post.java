package com.example.developerdesk.post.domain;

import com.example.developerdesk.equipment.domain.Equipment;
import com.example.developerdesk.image.domain.DeskImage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;


//@Where(clause = "deleted = true")
@Entity
@Getter
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    public Post() { }

    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipment> equipments;

    @Column
    private Integer totalPrice;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeskImage> images ;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @Column
    private boolean deleted = Boolean.FALSE;

    public void addImage(DeskImage image) {
        if (images == null) images = new ArrayList<>();

        images.add(image);
        image.setPost(this);
    }

    public void removeEquipment(Equipment equipment) {
        totalPrice -= equipment.getPrice();
        this.equipments.remove(equipment);
    }

    public void addEquipment(Equipment equipment) {
        if (equipment == null) equipments = new ArrayList<>();

        totalPrice += equipment.getPrice();
        equipments.add(equipment);
        equipment.setPost(this);
    }

    public void removeImage(DeskImage image) {
        this.images.remove(image);
    }

    public boolean updatePost(Post updatePost){

        boolean isUpdated = false;

        String updatedContents = updatePost.getContents();
        String updatedTitle = updatePost.getTitle();

        if (updatedContents != null) {
            this.contents = updatedContents;
            isUpdated = true;
        }

        if (updatedTitle != null) {
            this.title = updatedTitle;
            isUpdated = true;
        }

        return isUpdated;
    }

}
