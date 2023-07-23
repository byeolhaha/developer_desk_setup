package com.example.developerdesk.image.repository;

import com.example.developerdesk.image.domain.DeskImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeskImageRepository extends JpaRepository<DeskImage, Long> {

    @Query("select d from DeskImage d where d.post.id= ?1")
    List<DeskImage> findByPostId(Long postId);

}
