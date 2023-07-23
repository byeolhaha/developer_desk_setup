package com.example.developerdesk.post.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.developerdesk.equipment.api.ApiShopItem;
import com.example.developerdesk.image.domain.DeskImage;
import com.example.developerdesk.post.controller.dto.PostCreateRequest;
import com.example.developerdesk.post.controller.dto.PostUpdateRequest;
import com.example.developerdesk.post.service.dto.PostResponse;
import com.example.developerdesk.post.service.dto.PostSearchResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@ActiveProfiles({"api", "aws"})
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    private List<DeskImage> images;

    private  List<String> imageUrls;

    private List<ApiShopItem> equipments;

    @BeforeEach
    void setUp() {

        DeskImage deskImage1 = DeskImage.builder()
                .url("오늘의 날씨.png").build();
        DeskImage deskImage2 = DeskImage.builder()
                .url("오늘의 작물.png").build();

        images = List.of(deskImage1, deskImage2);

        imageUrls = List.of("오늘의 날씨.png","오늘의 작물.png");

        ApiShopItem equipment1 = ApiShopItem.builder().price(20000)
                .title("앱코 키보드")
                .maker("앱코")
                .price(2000)
                .link("www.naver.com").build();
        ApiShopItem equipment2 = ApiShopItem.builder().price(100000)
                .title("삼섬 모니터")
                .maker("삼성")
                .price(5000)
                .link("www.naver.com").build();

        equipments = List.of(equipment1, equipment2);

    }

    @Test
    void createPost(){

        PostCreateRequest postCreateRequest = new PostCreateRequest("내 책상을 소개합니다","이런 책상 처음이죠?",imageUrls,equipments);
        postService.createPost(postCreateRequest);
    }

    @Test
    @Transactional
    void updatePost_ImagesDelete() {
        List<String> deletedImageUrls = List.of("오늘의 날씨.png");

        ApiShopItem equipment1 = ApiShopItem.builder().price(20000)
                .title("앱코 키보드")
                .maker("앱코")
                .price(1000)
                .link("www.naver.com").build();
        ApiShopItem equipment2 = ApiShopItem.builder().price(100000)
                .title("삼섬 모니터")
                .price(3000)
                .maker("삼성")
                .link("www.naver.com").build();

        ApiShopItem equipment3 = ApiShopItem.builder().price(100000)
                .title("엘지 모니터")
                .maker("엘지")
                .price(400)
                .link("www.naver.com").build();

        List<ApiShopItem> addEquipments = List.of(equipment1, equipment2, equipment3);

        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("내 책상을 소개합니다","새롭게 키보드를 장만했어요",deletedImageUrls,addEquipments);
        postService.updatePost(13L,postUpdateRequest);

    }

    @Test
    void detailPostByPostId_PostId() {

        PostCreateRequest postCreateRequest = new PostCreateRequest("내 책상을 소개합니다","이런 책상 처음이죠?",imageUrls,equipments);
        PostResponse postResponse = postService.createPost(postCreateRequest);
        Long postId = postResponse.getId();

        postService.detailPostByPostId(postId);

    }

    @Test
    void deletedPost_PostId() {
        PostCreateRequest postCreateRequest = new PostCreateRequest("내 책상을 소개합니다","모니터 2개를 새로 장만했습니다.",imageUrls,equipments);
        PostResponse postResponse = postService.createPost(postCreateRequest);
        Long postId = postResponse.getId();

        postService.deletePost(postId);
    }

    @Test
    void findPostAllByFilter() {

        PostCreateRequest postCreateRequest = new PostCreateRequest("내 새로운 책상을 소개합니다","모니터 2개를 새로 장만했습니다.",imageUrls,equipments);
        postService.createPost(postCreateRequest);

        PostCreateRequest postCreateRequest1 = new PostCreateRequest("이사간 집의 책상을 자랑할게요","모니터 3개를 새로 장만했습니다.",imageUrls,equipments);
        postService.createPost(postCreateRequest1);

        PostCreateRequest postCreateRequest2 = new PostCreateRequest("제 사무실 책상을 소개합니다.","모니터 4개를 새로 장만했습니다.",imageUrls,equipments);
        postService.createPost(postCreateRequest2);

        Pageable pageable = PageRequest.of(0, 10);

        Slice<PostSearchResponse> result = postService.findPostAllByFilter(null, 3000, "",pageable);

        result.forEach(v-> System.out.println(v.getTitle()));

    }

}
