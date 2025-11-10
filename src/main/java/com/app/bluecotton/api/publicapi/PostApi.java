package com.app.bluecotton.api.publicapi;


import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.PostCommentVO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.post.PostReplyVO;
import com.app.bluecotton.domain.vo.post.PostVO;
import com.app.bluecotton.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/main/post")
public class PostApi {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO<List<PostMainDTO>>> getAllPosts(
            @RequestParam(required = false) String somCategory,
            @RequestParam(defaultValue = "latest") String orderType,
//            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) String q
    ) {
        Long memberId = 1L; // 로그인 가정
        List<PostMainDTO> posts = postService.getPosts(somCategory, orderType, memberId, q);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("게시글 목록 조회 완료", posts));
    }

    //     게시글 등록 API
    //     카테고리(SOM_ID)별 하루 1개 제한(마지막)
    //     이미지 미첨부 시 기본 이미지 자동 등록
    @PostMapping("/write")
    public ResponseEntity<ApiResponseDTO> writePost(@RequestBody PostWriteDTO dto) {
        PostVO postVO = dto.postVO();
        postService.write(postVO, dto.getImageUrls());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("게시글이 등록되었습니다.", Map.of("postId", postVO.getId())));
    }

    // 회원이 참여 중인 솜 카테고리 목록 조회 (수정/등록용 드롭다운)
    @GetMapping("/categories/{memberId}")
    public ResponseEntity<List<SomCategoryDTO>> getJoinedCategories(@PathVariable Long memberId) {
        return ResponseEntity.ok(postService.getJoinedCategories(memberId));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponseDTO> withdrawPost(@RequestParam Long id) {
        postService.withdraw(id);
        return ResponseEntity.ok(
                ApiResponseDTO.of("게시글이 성공적으로 삭제되었습니다.", null)
        );
    }

    //    임시저장 등록 api
    @PostMapping("/draft")
    public ResponseEntity<ApiResponseDTO> draftPost(@RequestBody PostDraftVO postdraftVO) {
        postService.registerDraft(postdraftVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("게시글이 임시 저장되었습니다."));
    }

    //    임시저장 조회 (이어쓰기용)
    @GetMapping("/draft/{id}")
    public ResponseEntity<ApiResponseDTO> getDraft(@PathVariable("id") Long id) {
        PostDraftVO draft = postService.getDraft(id);

        if (draft == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.of("임시저장된 글을 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok(ApiResponseDTO.of("임시저장 글 불러오기 성공", draft));
    }

    //    임시저장 삭제 (마이페이지 / 작성완료 시)
    @DeleteMapping("/draft/delete")
    public ResponseEntity<ApiResponseDTO> deleteDraft(@RequestParam("id") Long id) {
        postService.deleteDraft(id);
        return ResponseEntity.ok(ApiResponseDTO.of("임시저장 글이 삭제되었습니다."));
    }

    // 게시글 수정 조회 (수정페이지 진입 시)
    @GetMapping("/modify/{id}")
    public ResponseEntity<ApiResponseDTO> getPostUpdate(@PathVariable Long id) {
        Long memberId = 1L; // ✅ 임시 로그인 회원
        PostModifyDTO dto = postService.getPostForUpdate(id);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.of("존재하지 않는 게시글입니다.", null));
        }

        return ResponseEntity.ok(ApiResponseDTO.of("게시글 조회 성공", dto));
    }

    // 게시글 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<ApiResponseDTO> modifyPost(
            @PathVariable Long id,
            @RequestBody PostVO postVO) {

        postVO.setId(id);
        postService.modifyPost(postVO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDTO.of("게시글이 성공적으로 수정되었습니다."));
    }

    // 게시글 상세 조회
    @GetMapping("read/{id}")
    public ResponseEntity<ApiResponseDTO<PostDetailDTO>> getPostDetail(@PathVariable("id") Long id,
                                                                       @RequestParam Long memberId) {
        PostDetailDTO postDetail = postService.getPostDetail(id, memberId);

        if (postDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.of("게시글을 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok(ApiResponseDTO.of("게시글 상세 조회 성공", postDetail));
    }

    // 댓글 등록
    @PostMapping("/comment")
    public ResponseEntity<ApiResponseDTO> insertComment(@RequestBody PostCommentVO postCommentVO) {
        postService.insertComment(postCommentVO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("댓글이 등록되었습니다."));
    }

    // 답글 등록
    @PostMapping("/reply")
    public ResponseEntity<ApiResponseDTO> insertReply(@RequestBody PostReplyVO postReplyVO) {
        postService.insertReply(postReplyVO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("답글이 등록되었습니다."));
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDTO> deleteComment(@PathVariable("commentId") Long commentId) {
        postService.deleteComment(commentId);
        return ResponseEntity
                .ok(ApiResponseDTO.of("댓글 및 관련 답글이 모두 삭제되었습니다."));
    }

    // 답글 삭제
    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<ApiResponseDTO> deleteReply(@PathVariable("replyId") Long replyId) {
        postService.deleteReply(replyId);
        return ResponseEntity
                .ok(ApiResponseDTO.of("답글이 삭제되었습니다."));
    }

    // 게시글 좋아요 토글
    @PostMapping("/like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleLike(@RequestBody Map<String, Long> payload) {
        Long postId = payload.get("postId");
        Long memberId = payload.get("memberId");

        postService.toggleLike(postId, memberId);
        return ResponseEntity.ok(ApiResponseDTO.of("좋아요 토글 완료"));
    }

    // 댓글 좋아요 토글
    @PostMapping("/comment/like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleCommentLike(@RequestBody Map<String, Long> payload) {
        Long commentId = payload.get("commentId");
        Long memberId = payload.get("memberId");
        postService.toggleCommentLike(commentId, memberId);
        return ResponseEntity.ok(ApiResponseDTO.of("댓글 좋아요 토글 완료"));
    }

    // 답글 좋아요 토굴
    @PostMapping("/reply/like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleReplyLike(@RequestBody Map<String, Long> payload) {
        Long replyId = payload.get("replyId");
        Long memberId = payload.get("memberId");
        postService.toggleReplyLike(replyId, memberId);
        return ResponseEntity.ok(ApiResponseDTO.of("대댓글 좋아요 토글 완료"));
    }
}

