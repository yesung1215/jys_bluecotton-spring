package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.*;

import java.util.List;

public interface PostService {
    // 게시물 목록 조회 (좋아요 여부 포함)
    List<PostMainDTO> getPosts(
            String somCategory,
            String orderType,
            Long memberId,
            String q,
            int page,
            int size
    );

    // 토탈 게시글 수 처리
    int countPosts(String somCategory, String q);

    //  임시저장 불러온거 글쓴거
    public Long write(PostVO postVO, List<Long> postImageIds, Long draftId);

    //  새 글 작성
    public default void write(PostVO postVO, List<Long> postImageIds) {
        write(postVO, postImageIds, null);
    }

    //  카테고리 목록
    public List<SomCategoryDTO> getJoinedCategories(Long memberId);

    //  게시물 삭제
    public void withdraw(Long postId);

    //  임시저장 등록
    public void registerDraft(PostDraftVO postDraftVO);

    //  임시저장 조회 (이어쓰기)
    public PostDraftVO getDraft(Long id);

    //  임시저장 삭제 (마이페이지 or 작성완료 후 삭제용)
    public void deleteDraft(Long id);

    //  게시글 수정 조회
    public PostModifyDTO getPostForUpdate(Long id);

    //  게시글 수정
    public void modifyPost(PostModifyDTO postModifyDTO);

    //  댓글 등록
    public void insertComment(PostCommentVO postCommentVO);

    //  답글 등록
    public void insertReply(PostReplyVO postReplyVO);

    //  댓글 삭제
    public void deleteComment(Long commentId);

    //  답글 삭제
    public void deleteReplyById(Long replyId);

    //  게시글 좋아요
    public void toggleLike(Long postId, Long memberId);

    //  댓글 좋아요
    public void toggleCommentLike(Long commentId, Long memberId);

    //  답글 좋아요
    public void toggleReplyLike(Long ReplyId, Long memberId);

    //  게시글 상세 조회
    PostDetailDTO getPost(Long postId, Long memberId);
    public PostNeighborDTO getPrevPost(Long id);
    public PostNeighborDTO getNextPost(Long id);

    //  최근본글 추가
    public void registerRecent(Long memberId, Long postId);

    //  게시물 신고
    public void reportPost(PostReportVO postReportVO);

    //  댓글 신고
    public void reportComment(PostCommentReportVO postCommentReportVO);

    //  답글 신고
    public void reportReply(PostReplyReportVO postReplyReportVO);
}
