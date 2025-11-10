package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.post.PostMainDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Test
    void getPosts() {
        String somCategory = null; // 전체 조회
        String orderType = null;   // 기본값: 최신순
        Long memberId = 1L;        // 로그인한 회원(좋아요 여부 확인용)
        String q = null;

        List<PostMainDTO> posts = postService.getPosts(somCategory, orderType, memberId, q);

        // then
        log.info("조회된 게시글 개수 = {}", posts.size());
        posts.forEach(post -> {
            log.info("  게시글 ID: {}", post.getPostId());
            log.info("   제목: {}", post.getPostTitle());
            log.info("   작성자: {}", post.getMemberNickname());
            log.info("   좋아요 수: {}", post.getPostLikeCount());
            log.info("   댓글 수: {}", post.getPostCommentCount());
            log.info("   좋아요 여부: {}", post.getPostIsLike());
            log.info("   ------------------------------");
        });
    }
}
