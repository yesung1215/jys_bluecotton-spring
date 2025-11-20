package com.app.bluecotton.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@SpringBootTest
@Slf4j
class PostServiceTest {

    @Autowired
    private PostService postService;

    private final Random random = new Random();


    @Test
    @Transactional
    @Rollback(false)
    void generateAllLikeDummyData() {
        togglePostLikeRandom();
        toggleCommentLikeRandom();
        toggleReplyLikeRandom();

    }

    // 게시글 좋아요 (1~100)
    private void togglePostLikeRandom() {

        for (long postId = 1L; postId <= 100; postId++) {

            int maxLikes;
            if (postId >= 90) {               // 90~100
                maxLikes = 5;
            } else if (postId >= 70) {        // 70~89
                maxLikes = 15;
            } else if (postId >= 40) {        // 40~69
                maxLikes = 30;
            } else {                          // 1~39
                maxLikes = 50;
            }

            int likeCount = random.nextInt(maxLikes + 1);

            for (int k = 0; k < likeCount; k++) {
                long memberId = 1 + random.nextInt(50);
                postService.toggleLike(postId, memberId);
            }
        }
    }

    // 댓글 좋아요 (1~520)
    private void toggleCommentLikeRandom() {

        for (long commentId = 1L; commentId <= 520L; commentId++) {

            int maxLikes = (commentId >= 500 ? 4 : 15);
            int likeCount = random.nextInt(maxLikes + 1);

            for (int k = 0; k < likeCount; k++) {
                long memberId = 1 + random.nextInt(50);
                postService.toggleCommentLike(commentId, memberId);
            }
        }
    }

    // 답글 좋아요 (1~299)
    private void toggleReplyLikeRandom() {

        for (long replyId = 1L; replyId <= 299L; replyId++) {

            int likeCount = random.nextInt(5);   // 0~3

            for (int k = 0; k < likeCount; k++) {
                long memberId = 1 + random.nextInt(50);
                postService.toggleReplyLike(replyId, memberId);
            }
        }
    }
}
