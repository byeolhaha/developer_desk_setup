package com.example.developerdesk.post.service;

import com.example.developerdesk.equipment.domain.Equipment;
import com.example.developerdesk.equipment.repository.EquipmentRepository;
import com.example.developerdesk.image.domain.DeskImage;
import com.example.developerdesk.image.repository.DeskImageRepository;
import com.example.developerdesk.post.controller.dto.PostCreateRequest;
import com.example.developerdesk.post.controller.dto.PostUpdateRequest;
import com.example.developerdesk.post.domain.Post;
import com.example.developerdesk.post.repository.PostRepository;
import com.example.developerdesk.post.service.dto.PostResponse;
import com.example.developerdesk.post.service.dto.PostSearchResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {


    private final EquipmentRepository equipmentRepository;
    private final DeskImageRepository deskImageRepository;
    private final PostRepository postRepository;
    private final PostEntityConverter postEntityConverter;
    private final PostImageEquipmentUpdater postImageEquipmentUpdater;


    public PostService(EquipmentRepository equipmentRepository,
            DeskImageRepository deskImageRepository, PostRepository postRepository,
            PostEntityConverter postEntityConverter,
            PostImageEquipmentUpdater postImageEquipmentUpdater) {
        this.equipmentRepository = equipmentRepository;
        this.deskImageRepository = deskImageRepository;
        this.postRepository = postRepository;
        this.postEntityConverter = postEntityConverter;
        this.postImageEquipmentUpdater = postImageEquipmentUpdater;
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest postCreateRequest) {
        Post post = postEntityConverter.toEntity(postCreateRequest);
        postImageEquipmentUpdater.start(post);

        postRepository.save(post);

        return PostResponse.of(post);
    }

    @Transactional
    public boolean updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Post postToEdit = postRepository.findById(postId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수정하시려는 게시글이 없어요"));

        Post editedPost = postEntityConverter.toEntity(postUpdateRequest);

        editImages(postToEdit, editedPost);
        editEquipment(postToEdit, editedPost);

        return postToEdit.updatePost(editedPost);

    }

    private void editImages(Post postToEdit, Post editedPost) {
        List<DeskImage> existingDeskImages = postToEdit.getImages();
        List<DeskImage> imagesCopy = new ArrayList<>(existingDeskImages);

        for (DeskImage newDeskImage : editedPost.getImages()) {
            if (!imagesCopy.contains(newDeskImage)) {
                postToEdit.addImage(newDeskImage);
                continue;
            }

            imagesCopy.remove(newDeskImage);
        }

        for (DeskImage deskImage : imagesCopy) {
            postToEdit.removeImage(deskImage);
            deskImageRepository.delete(deskImage);
        }
    }

    private void editEquipment(Post postToEdit, Post editedPost) {
        List<Equipment> existingEquipments = postToEdit.getEquipments();
        List<Equipment> equipmentsCopy = new ArrayList<>(existingEquipments);

        for (Equipment newEquipment : editedPost.getEquipments()) {
            if (!equipmentsCopy.contains(newEquipment)) {
                postToEdit.addEquipment(newEquipment);
                continue;
            }

            equipmentsCopy.remove(newEquipment);
        }

        for (Equipment equipment : equipmentsCopy) {
            postToEdit.removeEquipment(equipment);
            equipmentRepository.delete(equipment);
        }
    }

    @Transactional
    public PostResponse detailPostByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "찾으시는 게시글이 없어요"));
        postRepository.updateView(postId);

        return PostResponse.of(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post postToDelete = postRepository.findById(postId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제하려는 게시글이 없어요"));

        postRepository.deleteById(postToDelete.getId());
    }

    @Transactional
    public Slice<PostSearchResponse> findPostAllByFilter(String title, Integer totalPrice, String contents, Pageable pageable) {
        Slice<Post> result =  postRepository.findPostAllByFilter(title, totalPrice, contents, pageable);
        return  result.map(PostSearchResponse::of);
    }

}
