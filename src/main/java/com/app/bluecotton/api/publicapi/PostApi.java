package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.dto.post.PostDetailDTO;
import com.app.bluecotton.domain.dto.post.PostMainDTO;
import com.app.bluecotton.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/main/post")
public class PostApi {

    private final PostService postService;

    // 게시글 전체 목록 조회 (로그인 / 비로그인)
    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO<List<PostMainDTO>>> getAllPosts(
            @RequestParam(required = false) String somCategory,
            @RequestParam(defaultValue = "latest") String orderType,
            @RequestParam(required = false) String q,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        Long memberId = (currentUser != null) ? currentUser.getId() : null;

        List<PostMainDTO> posts = postService.getPosts(somCategory, orderType, memberId, q);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDTO.of("게시글 목록 조회 완료", posts));
    }

//    // 게시글 상세 조회 (로그인 / 비로그인 자동 분기)
//    @GetMapping("/read/{postId}")
//    public ResponseEntity<ApiResponseDTO<PostDetailDTO>> getPostDetail(
//            @PathVariable("postId") Long postId,
//            @AuthenticationPrincipal MemberResponseDTO currentUser
//    ) {
//        try {
//            Long memberId = (currentUser != null) ? currentUser.getId() : null;
//
//            log.info("📄 게시글 상세 조회 요청: postId={}, memberId={}", postId, memberId);
//
//            PostDetailDTO postDetail = postService.getPostDetail(postId, memberId);
//
//            if (postDetail == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(ApiResponseDTO.of("존재하지 않는 게시글입니다.", null));
//            }
//
//            log.info("{}", postDetail);
//            return ResponseEntity.ok(ApiResponseDTO.of("게시글 상세 조회 성공", postDetail));
//
//        } catch (Exception e) {
//            log.error("❌ 게시글 상세 조회 중 오류", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ApiResponseDTO.of("게시글 조회 중 오류가 발생했습니다.", null));
//        }
//    }

    @PostMapping("/read/{postId}")
    public ResponseEntity<ApiResponseDTO<PostDetailDTO>> postTestPost(@PathVariable Long postId){
        PostDetailDTO postDetail = postService.selectTest(postId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("게시글 상세 조회 성공", postDetail));
    }

}