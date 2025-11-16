package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.dto.post.PostDetailDTO;
import com.app.bluecotton.domain.dto.post.PostMainDTO;
import com.app.bluecotton.domain.dto.post.PostNeighborDTO;
import com.app.bluecotton.service.MemberService;
import com.app.bluecotton.service.PostService;
import com.app.bluecotton.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main/post")
public class PostApi {

    private final PostService postService;
    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;

    // 게시글 전체 목록 조회 (로그인 / 비로그인)
    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getAllPosts(
            @RequestParam(required = false) String somCategory,
            @RequestParam(defaultValue = "latest") String orderType,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) Long memberId,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {

        // 로그인 우선 적용
        Long effectiveMemberId = null;
        if (currentUser != null) {
            effectiveMemberId = currentUser.getId();
        } else if (memberId != null) {
            effectiveMemberId = memberId;
        }

        // 페이징된 게시글 목록 조회
        List<PostMainDTO> posts = postService.getPosts(
                somCategory,
                orderType,
                effectiveMemberId,
                q,
                page,
                size
        );

        // totalCount 조회 (카테고리 + 검색 조건 동일하게 적용)
        int totalCount = postService.countPosts(somCategory, q);

        // 응답 묶기
        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("totalCount", totalCount);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDTO.of("게시글 목록 조회 완료", result));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> readPost(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {

        Long memberId = 0L;

        // 1) Authorization 헤더에서 JWT 토큰 추출
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // 2) JWT → 이메일(claim)
            Claims claims = jwtTokenUtil.getMemberEmailFromToken(token);

            if (claims != null) {
                String email = claims.get("memberEmail", String.class);

                // 3) 이메일 → memberId 조회
                memberId = memberService.getMemberIdByMemberEmail(email);
            }
        }

        // 4) 게시글 조회 (memberId 전달 → 좋아요 여부까지 포함)
        PostDetailDTO post = postService.getPost(id, memberId);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.of("게시글을 찾을 수 없습니다.", null));
        }

        // 5) 이전/다음
        PostNeighborDTO prev = postService.getPrevPost(id);
        PostNeighborDTO next = postService.getNextPost(id);

        // 6) 결과 묶기
        Map<String, Object> result = new HashMap<>();
        result.put("post", post);
        result.put("prev", prev);
        result.put("next", next);

        return ResponseEntity.ok(ApiResponseDTO.of("조회 성공", result));
    }


}