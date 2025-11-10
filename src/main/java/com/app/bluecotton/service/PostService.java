package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.post.PostDetailDTO;
import com.app.bluecotton.domain.dto.post.PostMainDTO;
import com.app.bluecotton.domain.dto.post.PostModifyDTO;
import com.app.bluecotton.domain.dto.post.SomCategoryDTO;
import com.app.bluecotton.domain.vo.post.PostCommentVO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.post.PostReplyVO;
import com.app.bluecotton.domain.vo.post.PostVO;

import java.util.List;

public interface PostService {
    //    게시물 목록
    List<PostMainDTO> getPosts(String somCategory, String orderType
            , Long memberId, String q);

    //    게시물 등록
    void write(PostVO postVO, List<String> imageUrls);

    List<SomCategoryDTO> getJoinedCategories(Long memberId);

    //    게시물 삭제
    void withdraw(Long postId);

    //    임시저장 등록
    void registerDraft(PostDraftVO postDraftVO);

    //    임시저장 조회 (이어쓰기용)
    PostDraftVO getDraft(Long id);

    //    임시저장 삭제 (마이페이지 or 작성완료 후 삭제용)
    void deleteDraft(Long id);

    // 게시글 수정 조회
    PostModifyDTO getPostForUpdate(Long id);

    // 게시글 수정
    void modifyPost(PostVO postVO);

    // 게시물 상세 조회
    PostDetailDTO getPostDetail(Long postId, Long memberId);

    // 댓글 등록
    void insertComment(PostCommentVO postCommentVO);

    // 답글 등록
    void insertReply(PostReplyVO postReplyVO);

    // 댓글 삭제
    void deleteComment(Long commentId);

    // 답글 삭제
    void deleteReply(Long replyId);

    // 게시글 좋아요
    void toggleLike(Long postId, Long memberId);

    // 댓글 좋아요
    void toggleCommentLike(Long commentId, Long memberId);

    // 답글 좋아요
    void toggleReplyLike(Long ReplyId, Long memberId);

}
