package com.app.bluecotton.api.privateapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.*;
import com.app.bluecotton.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/private/post")
@RequiredArgsConstructor
public class PostPrivateApi {

    private final PostService postService;

    // 게시글 등록
    @PostMapping("/write")
    public ResponseEntity<ApiResponseDTO> writePost(
            @RequestBody PostWriteDTO dto,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        dto.setMemberId(currentUser.getId());
        Long postId = postService.write(dto.postVO(), dto.getPostImageIds(), dto.getDraftId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("게시글 등록 완료", Map.of("postId", postId)));
    }

    // 참여 중 솜 카테고리 조회
    @GetMapping("/categories")
    public ResponseEntity<List<SomCategoryDTO>> getJoinedCategories(
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        return ResponseEntity.ok(postService.getJoinedCategories(currentUser.getId()));
    }

    // 게시글 삭제
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponseDTO> withdrawPost(@RequestParam Long id) {
        postService.withdraw(id);
        return ResponseEntity.ok(ApiResponseDTO.of("게시글 삭제 완료"));
    }

    // 임시저장 생성
    @PostMapping("/draft")
    public ResponseEntity<ApiResponseDTO> draftPost(
            @RequestBody PostDraftVO postDraftVO,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        postDraftVO.setMemberId(currentUser.getId());
        postService.registerDraft(postDraftVO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("임시 저장 완료"));
    }

    // 임시저장 조회
    @GetMapping("/draft/{id}")
    public ResponseEntity<ApiResponseDTO> getDraft(@PathVariable Long id) {
        PostDraftVO draft = postService.getDraft(id);
        if (draft == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.of("임시 저장된 글 없음", null));
        }
        return ResponseEntity.ok(ApiResponseDTO.of("불러오기 성공", draft));
    }

    // 임시저장 삭제
    @DeleteMapping("/draft/delete")
    public ResponseEntity<ApiResponseDTO> deleteDraft(@RequestParam Long id) {
        postService.deleteDraft(id);
        return ResponseEntity.ok(ApiResponseDTO.of("임시 저장 삭제 완료"));
    }

    // 게시글 수정 조회
    @GetMapping("/modify/{id}")
    public ResponseEntity<ApiResponseDTO> getPostUpdate(@PathVariable Long id) {
        PostModifyDTO dto = postService.getPostForUpdate(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.of("게시글 없음", null));
        }
        return ResponseEntity.ok(ApiResponseDTO.of("조회 성공", dto));
    }

    // 게시글 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<ApiResponseDTO> modifyPost(
            @PathVariable Long id,
            @RequestBody PostModifyDTO postModifyDTO
    ) {
        postModifyDTO.setId(id);
        postService.modifyPost(postModifyDTO);
        return ResponseEntity.ok(ApiResponseDTO.of("수정 완료"));
    }

    // 댓글 등록
    @PostMapping("/comment")
    public ResponseEntity<ApiResponseDTO> insertComment(
            @RequestBody PostCommentVO vo,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        vo.setMemberId(currentUser.getId());
        postService.insertComment(vo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("댓글 등록 완료"));
    }

    // 답글 등록
    @PostMapping("/reply")
    public ResponseEntity<ApiResponseDTO> insertReply(
            @RequestBody PostReplyVO vo,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        vo.setMemberId(currentUser.getId());
        postService.insertReply(vo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("답글 등록 완료"));
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDTO> deleteComment(@PathVariable Long commentId) {
        postService.deleteComment(commentId);
        return ResponseEntity.ok(ApiResponseDTO.of("댓글 삭제 완료"));
    }

    // 답글 삭제
    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<ApiResponseDTO> deleteReply(@PathVariable Long replyId) {
        postService.deleteReplyById(replyId);
        return ResponseEntity.ok(ApiResponseDTO.of("답글 삭제 완료"));
    }

    // 게시글 좋아요 토글
    @PostMapping("/like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleLike(
            @RequestBody Map<String, Long> payload,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        postService.toggleLike(payload.get("postId"), currentUser.getId());
        return ResponseEntity.ok(ApiResponseDTO.of("좋아요 토글 완료"));
    }

    // 댓글 좋아요 토글
    @PostMapping("/comment/like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleCommentLike(
            @RequestBody Map<String, Long> payload,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        postService.toggleCommentLike(payload.get("commentId"), currentUser.getId());
        return ResponseEntity.ok(ApiResponseDTO.of("댓글 좋아요 토글 완료"));
    }

    // 답글 좋아요 토글
    @PostMapping("/reply/like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleReplyLike(
            @RequestBody Map<String, Long> payload,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        postService.toggleReplyLike(payload.get("replyId"), currentUser.getId());
        return ResponseEntity.ok(ApiResponseDTO.of("답글 좋아요 토글 완료"));
    }

    // 최근 본 글 추가
    @PostMapping("/recent/{postId}")
    public ResponseEntity<ApiResponseDTO> recentPost(
            Authentication authentication,
            @PathVariable Long postId
    ) {
        Long memberId = ((MemberResponseDTO) authentication.getPrincipal()).getId();
        postService.registerRecent(memberId, postId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("최근 본 글 추가 완료"));
    }

    // 게시글 신고
    @PostMapping("/report/post")
    public ResponseEntity<ApiResponseDTO> reportPost(
            @RequestBody PostReportVO vo,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        vo.setMemberId(currentUser.getId());
        postService.reportPost(vo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("게시글 신고 완료"));
    }

    // 댓글 신고
    @PostMapping("/report/comment")
    public ResponseEntity<ApiResponseDTO> reportComment(
            @RequestBody PostCommentReportVO vo,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        vo.setMemberId(currentUser.getId());
        postService.reportComment(vo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("댓글 신고 완료"));
    }

    // 답글 신고
    @PostMapping("/report/reply")
    public ResponseEntity<ApiResponseDTO> reportReply(
            @RequestBody PostReplyReportVO vo,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        vo.setMemberId(currentUser.getId());
        postService.reportReply(vo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("답글 신고 완료"));
    }

}
