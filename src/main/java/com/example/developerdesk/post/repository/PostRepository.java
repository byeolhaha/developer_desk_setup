package com.example.developerdesk.post.repository;

import com.example.developerdesk.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id")
    void updateView(@Param("id") Long id);

    @Query("SELECT p FROM Post p " +
            "WHERE (p.title LIKE %:title%) " +
            "AND (p.totalPrice >= :totalPrice) " +
            "AND (p.contents LIKE %:contents%)")
    Slice<Post> findPostAllByFilter(@Param("title") String title,
            @Param("totalPrice") Integer totalPrice,
            @Param("contents") String contents,
            Pageable pageable);

}
