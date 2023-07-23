package com.example.developerdesk.post.controller;

import com.example.developerdesk.post.controller.dto.PostCreateRequest;
import com.example.developerdesk.post.controller.dto.PostUpdateRequest;
import com.example.developerdesk.post.service.PostService;
import com.example.developerdesk.post.service.dto.PostResponse;
import com.example.developerdesk.post.service.dto.PostSearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Slice<PostSearchResponse> searchAllPosts(
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "totalPrice", required = false, defaultValue = "0") Integer totalPrice,
            @RequestParam(value = "contents", required = false, defaultValue = "") String contents,
            @PageableDefault Pageable pageable
    ) {
        return postService.findPostAllByFilter(title, totalPrice, contents, pageable);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestBody PostCreateRequest postCreateRequest) {
        return new ResponseEntity<>(postService.createPost(postCreateRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id,
            @RequestBody PostUpdateRequest postUpdateRequest) {
        boolean isUpdated = postService.updatePost(id, postUpdateRequest);

        if (isUpdated == true) {
            return new ResponseEntity<>("Post updated successfully.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to update post.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.detailPostByPostId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> detailPost(@PathVariable Long id) {
        postService.deletePost(id);

        return new ResponseEntity<>("게시글이 성공적으로 지워졌습니다.", HttpStatus.NO_CONTENT);
    }

}
